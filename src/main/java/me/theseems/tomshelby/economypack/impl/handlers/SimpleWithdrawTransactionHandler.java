package me.theseems.tomshelby.economypack.impl.handlers;

import me.theseems.tomshelby.economypack.api.EconomyProvider;
import me.theseems.tomshelby.economypack.api.transaction.TransactionHandler;
import me.theseems.tomshelby.economypack.api.transaction.types.WithdrawTransaction;

import java.math.BigDecimal;

public class SimpleWithdrawTransactionHandler extends TransactionHandler<WithdrawTransaction> {
  @Override
  public void execute(WithdrawTransaction transaction) {
    EconomyProvider provider = transaction.getProvider();
    BigDecimal amount =
        provider.getMoney(transaction.getTo()).subtract(transaction.getWithdrawAmount());
    provider.setMoney(transaction.getTo(), amount);
  }
}
