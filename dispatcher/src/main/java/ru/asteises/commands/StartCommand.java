package ru.asteises.commands;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.asteises.core.service.SimpleService;
import ru.asteises.keyboard.FirstPageKeyboard;

@Slf4j
public class StartCommand extends ServiceCommand {

    private final FirstPageKeyboard firstPageKeyboard;
    private final SimpleService simpleService;

    public StartCommand(String commandIdentifier, String description, FirstPageKeyboard firstPageKeyboard, SimpleService simpleService) {
        super(commandIdentifier, description);
        this.firstPageKeyboard = firstPageKeyboard;
        this.simpleService = simpleService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        // включаем/отключаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
        message.enableMarkdown(true);
        String chatId = String.valueOf(chat.getId());
        message.setChatId(chatId);
        log.info("Chat ID: {}", chatId);
        message.setText("Добро пожаловать, " + user.getUserName() + " ! Я ваш личный помощник LISA. С чего начнем?");
        log.info("Message text: {}", message.getText());

        try {
            absSender.execute(message);
            absSender.execute(FirstPageKeyboard.firstPageFullKeyBoard(Long.parseLong(chatId)));
        } catch (TelegramApiException e) {
            log.error("Cannot send message with such parameters chatId: {}, userName: {}",
                    chat.getId(), chat.getUserName());
            throw new RuntimeException(e);
        }
    }
}
