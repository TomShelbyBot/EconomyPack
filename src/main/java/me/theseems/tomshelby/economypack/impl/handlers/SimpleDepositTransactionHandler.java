package me.theseems.tomshelby.economypack.impl.handlers;

import me.theseems.tomshelby.economypack.api.EconomyProvider;
import me.theseems.tomshelby.economypack.api.transaction.TransactionHandler;
import me.theseems.tomshelby.economypack.api.transaction.types.DepositTransaction;

import java.math.BigDecimal;

public class SimpleDepositTransactionHandler extends TransactionHandler<DepositTransaction> {
  @Override
  public void execute(DepositTransaction transaction) {
    EconomyProvider provider = transaction.getProvider();
    BigDecimal amount =
        provider.getMoney(transaction.getTo()).add(transaction.getDepositAmount());
    provider.setMoney(transaction.getTo(), amount);
  }
}
