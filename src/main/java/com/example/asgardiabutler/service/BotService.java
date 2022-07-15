package com.example.asgardiabutler.service;


import com.example.asgardiabutler.entity.Asgardets;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotService {

    Asgardets returnBotUserIfExistsOrCreate(String chatId, String userName);

    SendMessage greetings(Update update);

    SendMessage birthDay(Update update);

    SendMessage addAsgardets(Update update);

    SendMessage settingFullNameAndBirthDay(Update update);
}
