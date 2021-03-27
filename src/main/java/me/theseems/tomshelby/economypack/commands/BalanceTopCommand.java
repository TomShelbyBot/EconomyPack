package me.theseems.tomshelby.economypack.commands;

import me.theseems.tomshelby.ThomasBot;
import me.theseems.tomshelby.command.AdminPermissibleBotCommand;
import me.theseems.tomshelby.command.SimpleBotCommand;
import me.theseems.tomshelby.command.SimpleCommandMeta;
import me.theseems.tomshelby.economypack.EconomyBotPackage;
import me.theseems.tomshelby.economypack.api.EconomyProvider;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BalanceTopCommand extends SimpleBotCommand implements AdminPermissibleBotCommand {
  private static final Map<Integer, String> SPECIAL_PREFIX =
      new HashMap<Integer, String>() {
        {
          put(1, "\uD83E\uDD11 1️⃣");
          put(2, "\uD83D\uDCB0 2️⃣");
          put(3, "\uD83D\uDCB2 3️⃣");
          put(4, "\n4️⃣");
          put(5, "5️⃣");
          put(6, "6️⃣");
          put(7, "7️⃣");
          put(8, "8️⃣");
          put(9, "9️⃣");
          put(10, "\uD83D\uDD1F");
        }
      };

  private static class BalanceEntry {
    private final String username;
    private final BigDecimal money;

    public BalanceEntry(String username, BigDecimal money) {
      this.username = username;
      this.money = money;
    }
  }

  public BalanceTopCommand() {
    super(
        SimpleCommandMeta.onLabel("baltop")
            .aliases("balancetop", "topmoney", "moneytop")
            .description("Get top bounties in the chat"));
  }

  @Override
  public void handle(ThomasBot thomasBot, String[] strings, Update update) {
    Long chatId = update.getMessage().getChatId();
    EconomyProvider provider = EconomyBotPackage.getOrCreate(chatId);

    List<BalanceEntry> entryList = new ArrayList<>();
    thomasBot
        .getChatStorage()
        .getResolvableUsernames(chatId)
        .forEach(
            username ->
                thomasBot
                    .getChatStorage()
                    .lookup(chatId, username)
                    .ifPresent(
                        userId ->
                            entryList.add(new BalanceEntry(username, provider.getMoney(userId)))));

    StringBuilder builder = new StringBuilder();
    builder.append("Топ богачей по состоянию на момент отправки:").append("\n").append("\n");

    AtomicInteger place = new AtomicInteger(1);
    entryList.stream()
        .sorted(
            Comparator.comparing(
                balanceEntry -> balanceEntry.money.multiply(BigDecimal.valueOf(-1L))))
        .limit(10)
        .forEachOrdered(
            balanceEntry ->
                builder
                    .append(
                        SPECIAL_PREFIX.getOrDefault(
                            place.get(), String.valueOf(place.getAndIncrement())))
                    .append(". ")
                    .append(balanceEntry.username)
                    .append(" - ")
                    .append(balanceEntry.money)
                    .append("\n"));

    thomasBot.replyBack(update, new SendMessage().setText(builder.toString()));
  }
}
