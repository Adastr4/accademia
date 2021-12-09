package net.accademia.demone;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import java.io.IOException;
import java.util.List;

public class AccademiaTelegramBot {

    String bot_token = "5061679080:AAEkVopyxlibmqchEsjItpLAjpc5P5kqleo";
    TelegramBot bot = new TelegramBot(bot_token);

    public AccademiaTelegramBot() {
        super();
        bot.setUpdatesListener(
            updates -> {
                // ... process updates
                for (Update update : updates) {
                    Message message = update.message();
                    long chatId = update.message().chat().id();
                    SendResponse response2 = bot.execute(new SendMessage(chatId, "Hello!"));
                }
                // return id of last processed update or confirm them all
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            },
            e -> {
                if (e.response() != null) {
                    // god bad response from telegram
                    e.response().errorCode();
                    e.response().description();
                } else {
                    // probably network error
                    e.printStackTrace();
                }
            }
        );
        //		GetUpdates getUpdates = new GetUpdates().limit(100).offset(0).timeout(0);
        //		// async
        //		bot.execute(getUpdates, new Callback<GetUpdates, GetUpdatesResponse>() {
        //			@Override
        //			public void onResponse(GetUpdates request, GetUpdatesResponse response) {
        //				List<Update> updates = response.updates();
        //				if (updates==null ) return;
        //				for (Update update : updates) {
        //					Message message = update.message();
        //					long chatId = update.message().chat().id();
        //					SendResponse response2 = bot.execute(new SendMessage(chatId, "Hello!"));
        //				}
        //			}
        //
        //			@Override
        //			public void onFailure(GetUpdates request, IOException e) {
        //
        //			}
        //		});
    }

    void sendMessage() {
        //		SendResponse sendResponse = bot.execute(new SendMessage(chatId, "reply this message")
        //				.replyMarkup(new ForceReply().inputFieldPlaceholder("input-placeholder").selective(true)));
        //
        //		sendResponse = bot.execute(new SendMessage(chatId, "remove keyboard").replyMarkup(new ReplyKeyboardRemove())
        //				.disableNotification(true).replyToMessageId(8087));
        //
        //		sendResponse = bot.execute(new SendMessage(chatId, "message with keyboard").parseMode(ParseMode.HTML)
        //				.disableWebPagePreview(false)
        //				.replyMarkup(new ReplyKeyboardMarkup(new KeyboardButton("contact").requestContact(true),
        //						new KeyboardButton("location").requestLocation(true)).oneTimeKeyboard(true).resizeKeyboard(true)
        //								.inputFieldPlaceholder("input-placeholder").selective(true)));
        //
        //		sendResponse = bot.execute(
        //				new SendMessage(chatId, "simple buttons").replyMarkup(new ReplyKeyboardMarkup("ok", "cancel")));

    }
}
