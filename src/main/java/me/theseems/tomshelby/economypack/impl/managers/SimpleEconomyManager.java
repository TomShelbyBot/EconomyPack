package me.theseems.tomshelby.economypack.impl.managers;

import me.theseems.tomshelby.economypack.api.EconomyManager;
import me.theseems.tomshelby.economypack.api.EconomyProvider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimpleEconomyManager implements EconomyManager {
  private final EconomyProvider global;
  private final Map<String, EconomyProvider> providerMap;

  public SimpleEconomyManager(EconomyProvider global) {
    this.global = global;
    this.providerMap = new HashMap<>();
  }

  /**
   * Get global economy provider
   *
   * @return global economy
   */
  @Override
  public EconomyProvider getGlobalProvider() {
    return global;
  }

  /**
   * Get 'local' economy provider
   *
   * @param name to get by
   * @return optional of local economy provider
   */
  @Override
  public Optional<EconomyProvider> getProvider(String name) {
    return Optional.ofNullable(providerMap.get(name));
  }

  /**
   * Register economy provider
   *
   * @param provider to register
   */
  @Override
  public void registerProvider(EconomyProvider provider) {
    if (providerMap.containsKey(provider.getName()))
      throw new IllegalStateException(
          "Economy provider with name '" + provider.getName() + "' already exists");
    providerMap.put(provider.getName(), provider);
  }

  /**
   * Unregister provider from the manager
   *
   * @param name to unregister
   */
  @Override
  public void unregisterProvider(String name) {
    providerMap.remove(name);
  }

  /**
   * Get all provider's names there are in manager (possible except for global)
   *
   * @return providers
   */
  @Override
  public Collection<String> getProviders() {
    return providerMap.keySet();
  }
}
