package me.theseems.tomshelby.economypack.utils;

import me.theseems.tomshelby.Main;

import java.util.Optional;

public class DragUtils {
  public static Optional<Long> dragUserId(Long chatId, String input) {
    if (input == null) return Optional.empty();
    if (input.startsWith("@")) input = input.replaceFirst("@", "");
    return Main.getBot().getChatStorage().lookup(String.valueOf(chatId), input);
  }
}
