package ru.asteises.lisa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.asteises.lisa.controller.TelegramBotController;
import ru.asteises.lisa.keyboard.HeroPageKeyBoard;
import ru.asteises.lisa.service.SimpleService;
import ru.asteises.lisa.keyboard.FirstPageKeyboard;
import ru.asteises.lisa.storage.HeroPageRepository;
import ru.asteises.lisa.util.PagesCounter;

@Configuration
public class BotConfig {

    @Bean
    public TelegramBotController botController(@Value("${telegram.bot.name}") String botName,
                                               @Value("${telegram.bot.token}") String botToken,
                                               FirstPageKeyboard firstPageKeyboard,
                                               SimpleService simpleService,
                                               HeroPageKeyBoard heroPageKeyBoard,
                                               HeroPageRepository heroPageRepository,
                                               PagesCounter pagesCounter) {
        return new TelegramBotController(
                botName,
                botToken,
                firstPageKeyboard,
                simpleService,
                heroPageKeyBoard,
                heroPageRepository,
                pagesCounter);
    }

    @Bean
    public TelegramBotsApi botsApi(TelegramBotController botController) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(botController);
        return telegramBotsApi;
    }
}