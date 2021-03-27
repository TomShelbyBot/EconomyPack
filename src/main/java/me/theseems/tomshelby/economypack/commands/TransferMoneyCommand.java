package me.theseems.tomshelby.economypack.commands;

import me.theseems.tomshelby.ThomasBot;
import me.theseems.tomshelby.command.AdminPermissibleBotCommand;
import me.theseems.tomshelby.command.SimpleBotCommand;
import me.theseems.tomshelby.command.SimpleCommandMeta;
import me.theseems.tomshelby.economypack.EconomyBotPackage;
import me.theseems.tomshelby.economypack.api.EconomyProvider;
import me.theseems.tomshelby.economypack.impl.types.SimpleTransferTransaction;
import me.theseems.tomshelby.economypack.utils.DragUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;
import java.util.Optional;

public class TransferMoneyCommand extends SimpleBotCommand implements AdminPermissibleBotCommand {
  public TransferMoneyCommand() {
    super(
        SimpleCommandMeta.onLabel("transfermoney")
            .description("Передать свои деньги другому юзеру")
            .aliases("sendmoney", "transfer", "transmoney"));
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

      SimpleTransferTransaction transferTransaction =
          new SimpleTransferTransaction(
              provider, update.getMessage().getFrom().getId(), userId.get(), amount);

      provider
          .getTransactionExecutor()
          .execute(
              transferTransaction,
              transaction ->
                  thomasBot.replyBackText(
                      update,
                      transaction.getStatus().success
                          ? "\uD83C\uDD97 Исполнено.\n"
                              + "Счет отправителя: "
                              + transaction.getProvider().getMoney(transaction.getFrom())
                              + "\n"
                              + "Счет получателя: "
                              + transaction.getProvider().getMoney(transaction.getTo())
                          : "\n\uD83D\uDEAB Транзакция отклонена.\n"
                              + transaction.getStatus().message));
    } catch (NumberFormatException e) {
      thomasBot.replyBackText(update, "Не удалось определить сумму для выдачи");
    }
  }
}
