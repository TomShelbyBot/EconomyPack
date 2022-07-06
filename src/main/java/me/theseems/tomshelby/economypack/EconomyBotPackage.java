package me.theseems.tomshelby.economypack;

import me.theseems.tomshelby.ThomasBot;
import me.theseems.tomshelby.economypack.api.EconomyApi;
import me.theseems.tomshelby.economypack.api.EconomyProvider;
import me.theseems.tomshelby.economypack.commands.BalanceTopCommand;
import me.theseems.tomshelby.economypack.commands.GetMoneyCommand;
import me.theseems.tomshelby.economypack.commands.GiveMoneyCommand;
import me.theseems.tomshelby.economypack.commands.TransferMoneyCommand;
import me.theseems.tomshelby.economypack.impl.providers.ChatEconomyProvider;
import me.theseems.tomshelby.economypack.impl.providers.MemoryEconomyProvider;
import me.theseems.tomshelby.economypack.impl.managers.SimpleEconomyManager;
import me.theseems.tomshelby.economypack.impl.executors.SimpleTransactionExecutor;
import me.theseems.tomshelby.economypack.impl.handlers.SimpleDepositTransactionHandler;
import me.theseems.tomshelby.economypack.impl.handlers.SimpleTransferTransactionHandler;
import me.theseems.tomshelby.economypack.impl.handlers.SimpleWithdrawTransactionHandler;
import me.theseems.tomshelby.pack.JavaBotPackage;

import java.util.Optional;

public class EconomyBotPackage extends JavaBotPackage {
  private static ThomasBot thomasBot;

  @Override
  public void onEnable() {
    thomasBot = getBot();
    MemoryEconomyProvider economyProvider = new MemoryEconomyProvider("global");
    SimpleTransactionExecutor executor = new SimpleTransactionExecutor();
    executor.launch();
    economyProvider.setExecutor(executor);

    executor.registerHandler(new SimpleDepositTransactionHandler());
    executor.registerHandler(new SimpleWithdrawTransactionHandler());
    executor.registerHandler(new SimpleTransferTransactionHandler());

    EconomyApi.setManager(new SimpleEconomyManager(economyProvider));
    getBot().getCommandContainer().attach(new GiveMoneyCommand());
    getBot().getCommandContainer().attach(new GetMoneyCommand());
    getBot().getCommandContainer().attach(new TransferMoneyCommand());
    getBot().getCommandContainer().attach(new BalanceTopCommand());
  }

  @Override
  public void onDisable() {
    getBot().getCommandContainer().detach("givemoney");
    getBot().getCommandContainer().detach("getmoney");
    getBot().getCommandContainer().detach("transfermoney");
    getBot().getCommandContainer().detach("baltop");
  }

  public static EconomyProvider getOrCreate(Long chatId) {
    Optional<EconomyProvider> providerOptional =
        EconomyApi.getManager().getProvider("chatEconomy" + chatId);

    if (!providerOptional.isPresent()) {
      ChatEconomyProvider chatEconomyProvider = new ChatEconomyProvider(chatId.toString());
      EconomyApi.getManager().registerProvider(chatEconomyProvider);
      return chatEconomyProvider;
    } else {
      return providerOptional.get();
    }
  }

  public static ThomasBot getPackageBot() {
    return thomasBot;
  }
}
