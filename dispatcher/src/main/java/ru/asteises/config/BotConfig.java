package ru.asteises.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.asteises.controller.TelegramBotController;
import ru.asteises.core.service.SimpleService;
import ru.asteises.keyboard.FirstPageKeyboard;

@Configuration
public class BotConfig {

    @Bean
    public TelegramBotController botController(@Value("${telegram.bot.name}") String botName,
                                               @Value("${telegram.bot.token}") String botToken,
                                               FirstPageKeyboard firstPageKeyboard,
                                               SimpleService simpleService) {
        return new TelegramBotController(botName, botToken, firstPageKeyboard, simpleService);
    }

    @Bean
    public TelegramBotsApi botsApi(TelegramBotController botController) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(botController);
        return telegramBotsApi;
    }
}