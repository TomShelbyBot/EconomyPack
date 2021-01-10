package me.theseems.tomshelby.economypack.commands;

import me.theseems.tomshelby.Main;
import me.theseems.tomshelby.ThomasBot;
import me.theseems.tomshelby.command.PermissibleBotCommand;
import me.theseems.tomshelby.command.SimpleBotCommand;
import me.theseems.tomshelby.command.SimpleCommandMeta;
import me.theseems.tomshelby.economypack.EconomyBotPackage;
import me.theseems.tomshelby.economypack.api.EconomyProvider;
import me.theseems.tomshelby.economypack.impl.types.SimpleDepositTransaction;
import me.theseems.tomshelby.economypack.utils.DragUtils;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.ChatMember;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.Optional;

public class GiveMoneyCommand extends SimpleBotCommand implements PermissibleBotCommand {
  public GiveMoneyCommand() {
    super(SimpleCommandMeta.onLabel("givemoney").alias("emmit").description("Выдать денег"));
  }

  @Override
  public void handle(ThomasBot thomasBot, String[] strings, Update update) {
    if (strings.length < 2) {
      thomasBot.replyBackText(update, "Укажите @mention юзера и сумму");
      return;
    }

    Optional<Integer> userId = DragUtils.dragUserId(update.getMessage().getChatId(), strings[0]);
    if (!userId.isPresent()) {
      thomasBot.replyBackText(update, "Не могу найти данного юзера");
      return;
    }

    try {
      BigDecimal amount = new BigDecimal(strings[1]);
      EconomyProvider provider = EconomyBotPackage.getOrCreate(update.getMessage().getChatId());

      SimpleDepositTransaction depositTransaction =
          new SimpleDepositTransaction(provider, amount, userId.get());

      provider
          .getTransactionExecutor()
          .execute(
              depositTransaction,
              transaction ->
                  thomasBot.replyBackText(
                      update,
                      transaction.getStatus().success
                          ? "Исполнено.\n"
                              + "Счет получателя: "
                              + transaction.getProvider().getMoney(transaction.getTo())
                          : "Неудача: " + transaction.getStatus().message));
    } catch (NumberFormatException e) {
      thomasBot.replyBackText(update, "Не удалось определить сумму для выдачи");
    }
  }

  // By default restricted to the group creator
  @Override
  public boolean canUse(Long chatId, Integer userId) {
    try {
      for (ChatMember chatMember :
          Main.getBot().execute(new GetChatAdministrators().setChatId(chatId))) {
        if (chatMember.getStatus().equals("creator")) return true;
      }
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
    return false;
  }
}
