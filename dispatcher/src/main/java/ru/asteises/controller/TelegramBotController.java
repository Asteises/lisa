package ru.asteises.controller;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.asteises.commands.StartCommand;
import ru.asteises.core.service.SimpleService;
import ru.asteises.keyboard.FirstPageKeyboard;

import java.util.List;

@Slf4j
public class TelegramBotController extends TelegramLongPollingCommandBot {

    private final String botName;
    private final String botToken;

    private final FirstPageKeyboard firstPageKeyboard;
    private final SimpleService simpleService;

    public TelegramBotController(String botName,
                                 String botToken,
                                 FirstPageKeyboard firstPageKeyboard,
                                 SimpleService simpleService) {
        super();
        this.botName = botName;
        this.botToken = botToken;
        register(new StartCommand("start", "Start command", firstPageKeyboard, simpleService));
        this.firstPageKeyboard = firstPageKeyboard;
        this.simpleService = simpleService;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        SendMessage sendMessage = new SendMessage();
        if (update.hasMessage()) {
            Message message = update.getMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            try {
                if (message.isCommand()) {
                    log.info("Пользователь ввел несуществующую команду, отправляем начальное меню.");
                    sendMessage.setText("Такой команды не существует");
                    execute(sendMessage);
                    execute(FirstPageKeyboard.firstPageFullKeyBoard(update.getMessage().getChatId()));
                } else if (message.hasText()) {
                    sendMessage.setText("Просто нажимайте на пункты меню, не нужно ничего писать :)");
                    execute(sendMessage);
                    execute(FirstPageKeyboard.firstPageFullKeyBoard(update.getMessage().getChatId()));
                }
            } catch (TelegramApiException e) {
                log.error("Что-то пошло не так");
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String chatId = String.valueOf(callbackQuery.getMessage().getChatId());
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            InputFile inputFile = new InputFile();
            SendMediaGroup mediaGroup = new SendMediaGroup();

            if (callbackQuery.getData().equals("10-791")) {
                inputFile.setMedia("https://www.superherodb.com/pictures2/portraits/10/050/791.jpg?v=1610931347");
                sendPhoto.setPhoto(inputFile);
                InputMedia inputMedia1 = new InputMediaPhoto();
                InputMedia inputMedia2 = new InputMediaPhoto();
                inputMedia1.setMedia("https://www.superherodb.com/pictures2/portraits/10/050/791.jpg?v=1610931347");
                inputMedia2.setMedia("https://www.superherodb.com/pictures2/portraits/10/050/791.jpg?v=1610931347");
                mediaGroup.setChatId(chatId);
                mediaGroup.setMedias(List.of(inputMedia1, inputMedia2));

            } else if (callbackQuery.getData().equals("10-639")) {
                inputFile.setMedia("https://www.superherodb.com/pictures2/portraits/10/050/639.jpg?v=1636115377");
                sendPhoto.setPhoto(inputFile);
            }

            try {
                execute(mediaGroup);
                execute(FirstPageKeyboard.firstPageFullKeyBoard(Long.parseLong(chatId)));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Message is null");
        }
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
