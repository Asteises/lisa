package ru.asteises.lisa.keyboard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.asteises.lisa.model.HeroPage;
import ru.asteises.lisa.service.HeroPageService;
import ru.asteises.lisa.util.Navigate;
import ru.asteises.lisa.util.PagesCounter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class HeroPageKeyBoard {

    private final HeroPageService heroPageService;

    public SendMessage getHeroPagesOnLetter(long chatId, String letter, PagesCounter pagesCounter, Navigate navigate) {
        log.info("input nextCharacters: {}", pagesCounter.getPageFrom());
        List<HeroPage> heroPages = getHeroPages(letter);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Characters on " + letter + ": " + heroPages.size());
        log.info("Total characters on " + letter + ": " + heroPages.size());

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (int i = pagesCounter.getPageFrom(); i < pagesCounter.getPageFrom() + 10; i++) {
            List<InlineKeyboardButton> charactersRow = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            HeroPage heroPage = heroPages.get(i);
            String name = heroPage.getTitle();
            String link = heroPage.getUrl();
            button.setText(name);
            button.setUrl(link);
            button.setCallbackData("GO_TO_HERO_PAGE");
            charactersRow.add(button);

            rows.add(charactersRow);
        }



        List<InlineKeyboardButton> nextRow = new ArrayList<>();
        InlineKeyboardButton nextButton = new InlineKeyboardButton();
        nextButton.setText("NEXT >>>");
        nextButton.setCallbackData("NEXT");
        nextRow.add(nextButton);
        rows.add(nextRow);

        markup.setKeyboard(rows);
        message.setReplyMarkup(markup);

        pagesCounter.getCount(Navigate.NEXT);
        log.info(String.valueOf(pagesCounter.getPageFrom()));
        return message;
    }

    public List<HeroPage> getHeroPages(String letter) {
        return heroPageService.getHeroPageByUrlFirstChar(letter);
    }
}
