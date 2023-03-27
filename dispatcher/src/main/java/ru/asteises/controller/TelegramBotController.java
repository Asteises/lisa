package ru.asteises.controller;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.asteises.core.service.SimpleService;

@Slf4j
public class TelegramBotController extends TelegramLongPollingCommandBot {

    private final String botName;
    private final String botToken;
    private final SimpleService simpleService;

    public TelegramBotController(String botName, String botToken, SimpleService simpleService) {
        this.botName = botName;
        this.botToken = botToken;
        this.simpleService = simpleService;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Hello!");
        sendMessage.setChatId(update.getMessage().getChatId());
        System.out.println(update.getMessage().getText());
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}
