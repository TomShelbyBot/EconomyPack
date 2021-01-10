package me.theseems.tomshelby.economypack;

import me.theseems.tomshelby.economypack.api.transaction.TransactionHandler;
import me.theseems.tomshelby.economypack.api.transaction.types.DepositTransaction;
import me.theseems.tomshelby.economypack.api.transaction.types.WithdrawTransaction;
import me.theseems.tomshelby.economypack.impl.providers.MemoryEconomyProvider;
import me.theseems.tomshelby.economypack.impl.executors.SimpleTransactionExecutor;
import me.theseems.tomshelby.economypack.impl.types.SimpleDepositTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleExecutorTest {
  @Test
  public void simpleExecutor_stressTest1() {
    MemoryEconomyProvider provider = new MemoryEconomyProvider("test");
    SimpleTransactionExecutor executor = new SimpleTransactionExecutor();
    provider.setExecutor(executor);

    executor.registerHandler(
        new TransactionHandler<DepositTransaction>() {
          @Override
          public void execute(DepositTransaction transaction) {
            provider.setMoney(
                transaction.getTo(),
                provider.getMoney(transaction.getTo()).add(transaction.getDepositAmount()));
          }
        });

    executor.registerHandler(
        new TransactionHandler<WithdrawTransaction>() {
          @Override
          public void execute(WithdrawTransaction transaction) {
            provider.setMoney(
                transaction.getTo(),
                provider.getMoney(transaction.getTo()).subtract(transaction.getWithdrawAmount()));
          }
        });

    executor.launch();

    AtomicInteger check = new AtomicInteger(0);
    final int threshold = 500000;

    for (int i = 0; i < threshold; i++) {
      executor.execute(
          new SimpleDepositTransaction(provider, BigDecimal.TEN, 0),
          (transaction) -> check.getAndIncrement());
    }

    while (check.get() != threshold);
    Assertions.assertEquals(BigDecimal.TEN.multiply(BigDecimal.valueOf(threshold)), provider.getMoney(0));
  }
}
