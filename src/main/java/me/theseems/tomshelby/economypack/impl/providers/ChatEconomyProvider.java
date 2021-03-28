package me.theseems.tomshelby.economypack.impl.providers;

import me.theseems.tomshelby.ThomasBot;
import me.theseems.tomshelby.economypack.EconomyBotPackage;
import me.theseems.tomshelby.economypack.api.EconomyProvider;
import me.theseems.tomshelby.storage.SimpleTomMeta;
import me.theseems.tomshelby.storage.TomMeta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class ChatEconomyProvider implements EconomyProvider {
  private final Long chatId;

  public ChatEconomyProvider(Long chatId) {
    this.chatId = chatId;
  }

  /**
   * Get name of the economy
   *
   * @return economy's name
   */
  @Override
  public String getName() {
    return "chatEconomy" + chatId;
  }

  /**
   * Set money amount to user
   *
   * @param userId to set to
   * @param amount to set
   */
  @Override
  public void setMoney(Integer userId, BigDecimal amount) {
    amount = amount.setScale(15, RoundingMode.HALF_DOWN);
    ThomasBot bot = EconomyBotPackage.getPackageBot();
    TomMeta chatMeta = bot.getChatStorage().getChatMeta(chatId);

    TomMeta chatEconomy = chatMeta.getContainer("chatEconomy").orElseGet(SimpleTomMeta::new);
    chatEconomy.set(userId.toString(), amount.toString());
    chatMeta.set("chatEconomy", chatEconomy);
  }

  /**
   * Get user's money
   *
   * @param userId to get amount of money of
   * @return money
   */
  @Override
  public BigDecimal getMoney(Integer userId) {
    ThomasBot bot = EconomyBotPackage.getPackageBot();
    TomMeta chatMeta = bot.getChatStorage().getChatMeta(chatId);
    Optional<TomMeta> tomMeta = chatMeta.getContainer("chatEconomy");

    return tomMeta
        .map(meta -> new BigDecimal(meta.getString(userId.toString()).orElse("0")))
        .orElse(BigDecimal.ZERO)
        .setScale(15, RoundingMode.HALF_DOWN);
  }

  @Override
  public String toString() {
    return "ChatEconomyProvider{" + "chatId=" + chatId + '}';
  }

  public Long getChatId() {
    return chatId;
  }
}
