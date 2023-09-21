package ru.asteises.lisa.keyboard;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class FirstPageKeyboard {

    public static SendMessage firstPageFullKeyBoard(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("All characters are sorted alphabetically");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        int rowMaxCapacity;
        char currentChar = 'A';
        for (int i = 0; i < 4; i++) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            rowMaxCapacity = 0;
            for (char c = currentChar; c <= 'Z'; c++) {
                currentChar = c;
                InlineKeyboardButton button = new InlineKeyboardButton();
                String letter = String.valueOf(currentChar);
                button.setText(letter);
                button.setCallbackData(letter);
                row.add(button);
                rowMaxCapacity++;
                if (rowMaxCapacity == 7) {
                    currentChar++;
                    break;
                }
            }
            rows.add(row);
        }

        markup.setKeyboard(rows);
        message.setReplyMarkup(markup);

        return message;
    }
}
