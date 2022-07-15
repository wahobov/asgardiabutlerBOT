package com.example.asgardiabutler.service;

import com.example.asgardiabutler.constraints.BotState;
import com.example.asgardiabutler.constraints.MessageKey;
import com.example.asgardiabutler.entity.Asgardets;
import com.example.asgardiabutler.repository.AsgardetsRepository;
import com.example.asgardiabutler.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.aspectj.bridge.Version.getTime;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final AsgardetsRepository asgardetsRepository;
    private final ChatRepository chatRepository;

    @Override
    public Asgardets returnBotUserIfExistsOrCreate(String id, String userName) {
        if (asgardetsRepository.findByUserId(id)!=null)
            return asgardetsRepository.findByUserId(id);
        Asgardets asgardets = new Asgardets();
        asgardets.setUsername(userName);
        asgardets.setBotState(BotState.GREETING);
        asgardets.setUserId(id);
        asgardetsRepository.save(asgardets);
        return asgardets;
    }

    @Override
    public SendMessage greetings(Update update) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        Asgardets asgardets = returnBotUserIfExistsOrCreate(userId, update.getMessage().getFrom().getFirstName());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.HTML);
        String text = "Hello there <a href=\"tg://user?id="+asgardets.getUserId()+"\">"+asgardets.getUsername()+"</a>" +
                "\n";
        sendMessage.setText(text);
        asgardetsRepository.save(asgardets);
        return sendMessage;
    }

    @Override
    public SendMessage birthDay(Update update) {
        String birthDay = update.getMessage().getText();
        String[] dates = birthDay.split("\\.");
        SendMessage sendMessage = new SendMessage();
        Asgardets asgardets = returnBotUserIfExistsOrCreate(String.valueOf(update.getMessage().getChatId()), update.getMessage().getFrom().getFirstName());
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        if (!(dates.length == 3)) {
            sendMessage.setText("Please send in correct format dd.mm.yyyy");
        }

        int year = Integer.parseInt(dates[0]);
        int day = Integer.parseInt(dates[1]);
        int month = Integer.parseInt(dates[2]);

        if (year < 0 || day < 0 || month < 0) {
            sendMessage.setText("Please send in correct format dd.mm.yyyy");
        }

        asgardets.setBotState(BotState.NOTHING);
        asgardets.setBirthDate(dates[0]+"."+dates[1]);
        asgardets.setBirthDay(dates[0]);
        asgardets.setBirthMonth(dates[1]);
        asgardets.setBirthYear(dates[2]);
        asgardetsRepository.save(asgardets);

        sendMessage.setText("thank you, " + update.getMessage().getFrom().getFirstName() + " your data is saved");
        return sendMessage;
    }

    @Override
    public SendMessage addAsgardets(Update update) {
        Asgardets asgardets = returnBotUserIfExistsOrCreate(String.valueOf(update.getMessage().getChatId()), update.getMessage().getFrom().getFirstName());
        asgardets.setBotState(BotState.GIVE_FULL_NAME);
        asgardetsRepository.save(asgardets);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Please send the full name ad birthDate of Asgardets (Vahobov Abdurauf 17.04.2004)");
        return sendMessage;
    }

    @Override
    public SendMessage settingFullNameAndBirthDay(Update update) {
        Asgardets admin = returnBotUserIfExistsOrCreate(String.valueOf(update.getMessage().getChatId()), update.getMessage().getFrom().getFirstName());
        String[] fullName = update.getMessage().getText().split(" ");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        if (fullName.length>3) {
            sendMessage.setText("Send in proper format");
            return sendMessage;
        }

        String[] birthDate = fullName[2].split("\\.");

        if (birthDate.length>3) {
            sendMessage.setText("Send in proper format");
            return sendMessage;
        }

        Asgardets asgardets = asgardetsRepository.findByFirstNameAndLastNameAndBirthDate(fullName[0], fullName[1], birthDate[0]+"."+birthDate[1]);

        if (asgardets!=null) {
            sendMessage.setText("you have the Asgardets with this fullName and birthDate");
            return sendMessage;
        }

        admin.setBotState(BotState.NOTHING);
        asgardetsRepository.save(admin);

        Asgardets newAsgardets = new Asgardets();

        newAsgardets.setBirthDay(birthDate[0]);
        newAsgardets.setBirthMonth(birthDate[1]);
        newAsgardets.setBirthYear(birthDate[2]);
        newAsgardets.setBirthDate(birthDate[0]+"."+birthDate[1]);
        newAsgardets.setFirstName(fullName[0]);
        newAsgardets.setLastName(fullName[1]);
        asgardetsRepository.save(newAsgardets);

        sendMessage.setText("data is successfully saved");

        return sendMessage;

    }
}
