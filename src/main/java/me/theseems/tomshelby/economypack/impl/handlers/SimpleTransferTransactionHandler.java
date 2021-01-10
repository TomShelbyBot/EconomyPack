package me.theseems.tomshelby.economypack.impl.handlers;

import me.theseems.tomshelby.economypack.api.EconomyProvider;
import me.theseems.tomshelby.economypack.api.transaction.TransactionHandler;
import me.theseems.tomshelby.economypack.api.transaction.types.TransferTransaction;

import java.math.BigDecimal;

public class SimpleTransferTransactionHandler extends TransactionHandler<TransferTransaction> {
  @Override
  public void execute(TransferTransaction transaction) {
    EconomyProvider provider = transaction.getProvider();
    BigDecimal amount = transaction.getAmount();
    Integer from = transaction.getFrom();
    Integer to = transaction.getTo();

    if (provider.getMoney(transaction.getFrom()).compareTo(amount) < 0)
      throw new IllegalStateException(
          "User has insufficient amount of money to transfer '" + transaction.getAmount() + "'");

    provider.setMoney(from, provider.getMoney(from).subtract(amount));
    provider.setMoney(to, provider.getMoney(to).add(amount));
  }
}
