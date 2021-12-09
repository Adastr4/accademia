package net.accademia.demone;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.Test;

class AccademiaTelegramBotTest {

    AccademiaTelegramBot tb = new AccademiaTelegramBot();

    @Test
    void test() {}

    @Test
    public void sendMessage() {
        tb.sendMessage();
    }
}
