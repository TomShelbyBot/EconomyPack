package me.theseems.tomshelby.economypack.commands;

import me.theseems.tomshelby.ThomasBot;
import me.theseems.tomshelby.command.AdminPermissibleBotCommand;
import me.theseems.tomshelby.command.SimpleBotCommand;
import me.theseems.tomshelby.command.SimpleCommandMeta;
import me.theseems.tomshelby.economypack.EconomyBotPackage;
import me.theseems.tomshelby.economypack.api.EconomyProvider;
import me.theseems.tomshelby.economypack.utils.DragUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.DecimalFormat;
import java.util.Optional;

public class GetMoneyCommand extends SimpleBotCommand implements AdminPermissibleBotCommand {
  public GetMoneyCommand() {
    super(SimpleCommandMeta.onLabel("getmoney").alias("money").description("Узнать сколько денег"));
  }

  @Override
  public void handle(ThomasBot thomasBot, String[] strings, Update update) {
    Optional<Integer> userId;

    if (strings.length != 0) {
      userId = DragUtils.dragUserId(update.getMessage().getChatId(), strings[0]);
    } else {
      userId = Optional.ofNullable(update.getMessage().getFrom().getId());
    }

    if (!userId.isPresent()) {
      thomasBot.replyBackText(update, "Не могу найти данного юзера");
      return;
    }

    EconomyProvider provider = EconomyBotPackage.getOrCreate(update.getMessage().getChatId());
    thomasBot.replyBackText(
        update,
        "Денег на счету: "
            + DecimalFormat.getNumberInstance().format(provider.getMoney(userId.get())));
  }
}
