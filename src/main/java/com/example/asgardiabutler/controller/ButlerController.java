package com.example.asgardiabutler.controller;

import com.example.asgardiabutler.constraints.BotState;
import com.example.asgardiabutler.entity.Asgardets;
import com.example.asgardiabutler.repository.AsgardetsRepository;
import com.example.asgardiabutler.service.BotService;
import com.example.asgardiabutler.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.zaxxer.hikari.util.ClockSource.currentTime;
import static org.aspectj.bridge.Version.getTime;

@Controller
@RequiredArgsConstructor
public class ButlerController extends TelegramLongPollingBot implements ChatService {

    private final BotService botService;
    private final AsgardetsRepository asgardetsRepository;

    @Override
    public String getBotUsername() {
        return "asgardiabutler_bot";
    }

    @Override
    public String getBotToken() {
        return "5541688461:AAHQ69UhDYJxfLl1eO28UmlTpx9w1J8GsD8";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.getMessage().getChatId() > 0) {
            Asgardets asgardets = botService.returnBotUserIfExistsOrCreate(String.valueOf(update.getMessage().getChatId()), update.getMessage().getFrom().getFirstName());
            if (update.getMessage().hasText()) {
                String text = update.getMessage().getText();

                if (text.equals("/start"))
                    execute(botService.greetings(update));
                else if (asgardets.getBotState().equals(BotState.BIRTHDAY)) {
                    execute(botService.birthDay(update));
                    } else if (text.equals("/add")) {
                    execute(botService.addAsgardets(update));
                } else if (asgardets.getBotState().equals(BotState.GIVE_FULL_NAME)) {
                    execute(botService.settingFullNameAndBirthDay(update));
                }
            }
        } else {

        }
    }

    @Override
    public void sentText(String text) {
        CompletableFuture.runAsync(() -> {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId("1374218276");
            sendMessage.setParseMode(ParseMode.HTML);
            sendMessage.setText(text);
            try {
                if (text.length() > 4096) {
                    sendMessage.setText(text.substring(0, 4096));
                }
                sendApiMethod(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }

    @SneakyThrows
    @Scheduled(cron = "0 0 9 ? * * ")
    public void notifyExpiredLicenceDate() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM");
        String birthDate = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        System.out.println(birthDate);
        List<Asgardets> asgardetsList = asgardetsRepository.findAllByBirthDate(birthDate);
        System.out.println(asgardetsList.size());
        for (Asgardets asgardets : asgardetsList) {
            sentText("Congratulations!!\nToday is the birthday of someone from Asgardets\nHappy Birthday " + asgardets.getFirstName() + " " + asgardets.getLastName());
        }

    }
}
