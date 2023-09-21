package ru.asteises.lisa.controller;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.asteises.lisa.commands.StartCommand;
import ru.asteises.lisa.keyboard.HeroPageKeyBoard;
import ru.asteises.lisa.service.SimpleService;
import ru.asteises.lisa.keyboard.FirstPageKeyboard;
import ru.asteises.lisa.storage.HeroPageRepository;
import ru.asteises.lisa.util.Navigate;
import ru.asteises.lisa.util.PagesCounter;

@Slf4j
public class TelegramBotController extends TelegramLongPollingCommandBot {

    public static int nextCharacters = 0;
    public static char letter;

    private final String botName;
    private final String botToken;
    private final HeroPageKeyBoard heroPageKeyBoard;
    private final HeroPageRepository heroPageRepository;
    private final PagesCounter pagesCounter;

    public TelegramBotController(String botName,
                                 String botToken,
                                 FirstPageKeyboard firstPageKeyboard,
                                 SimpleService simpleService,
                                 HeroPageKeyBoard heroPageKeyBoard,
                                 HeroPageRepository heroPageRepository, PagesCounter pagesCounter) {
        super();
        this.botName = botName;
        this.botToken = botToken;
        this.heroPageKeyBoard = heroPageKeyBoard;
        this.heroPageRepository = heroPageRepository;
        this.pagesCounter = pagesCounter;
        register(new StartCommand("start", "Start command", heroPageRepository));
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        SendMessage sendMessage = new SendMessage();
        if (update.hasMessage()) {
            Message message = update.getMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            try {
                if (message.isCommand()) {
                    log.info("The user has entered a non-existent command, we send the initial menu.");
                    sendMessage.setText("There is no such command");
                    execute(sendMessage);
                    execute(FirstPageKeyboard.firstPageFullKeyBoard(update.getMessage().getChatId()));
                } else if (message.hasText()) {
                    log.info("The user has entered a text, we send the initial menu.");
                    sendMessage.setText("Just click on the menu items, no need to write anything :)");
                    execute(sendMessage);
                    execute(FirstPageKeyboard.firstPageFullKeyBoard(update.getMessage().getChatId()));
                }
            } catch (TelegramApiException e) {
                log.error("Something went wrong");
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Long chatIdL = callbackQuery.getMessage().getChatId();
            String chatIdS = String.valueOf(chatIdL);

            if (callbackQuery.getData().matches("[A-Z]")) {
                try {
                    letter = callbackQuery.getData().charAt(0);
                    log.info(String.valueOf(letter));
                    pagesCounter.setPageFrom(0);
                    execute(heroPageKeyBoard.getHeroPagesOnLetter(chatIdL, String.valueOf(letter), pagesCounter, Navigate.NEXT));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (callbackQuery.getData().equals("GO_TO_HERO_PAGE")) {
                sendMessage.setChatId(callbackQuery.getMessage().getChatId());
                sendMessage.setText("");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (callbackQuery.getData().equals("NEXT")) {
                try {
                    log.info(String.valueOf(letter));
                    execute(heroPageKeyBoard.getHeroPagesOnLetter(chatIdL, String.valueOf(letter), pagesCounter, Navigate.BOTH));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (callbackQuery.getData().equals("PREV")) {
                try {
                    log.info(String.valueOf(letter));
                    execute(heroPageKeyBoard.getHeroPagesOnLetter(chatIdL, String.valueOf(letter), pagesCounter, Navigate.BOTH));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

//            try {
//                execute(mediaGroup);
//                execute(FirstPageKeyboard.firstPageFullKeyBoard(Long.parseLong(chatId)));
//            } catch (TelegramApiException e) {
//                throw new RuntimeException(e);
//            }
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
