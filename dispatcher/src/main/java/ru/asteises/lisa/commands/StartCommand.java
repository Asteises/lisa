package ru.asteises.lisa.commands;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.asteises.lisa.keyboard.FirstPageKeyboard;
import ru.asteises.lisa.storage.HeroPageRepository;

@Slf4j
public class StartCommand extends ServiceCommand {

    private final HeroPageRepository heroPageRepository;

    public StartCommand(String commandIdentifier,
                        String description,
                        HeroPageRepository heroPageRepository) {
        super(commandIdentifier, description);
        this.heroPageRepository = heroPageRepository;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        // включаем/отключаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
        message.enableMarkdown(true);
        String chatId = String.valueOf(chat.getId());
        message.setChatId(chatId);
        log.info("Chat ID: {}", chatId);
        message.setText("Welcome, " + user.getUserName() +
                " ! Here is an encyclopedia of characters. In our database: " + heroPageRepository.count() + " characters");
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
