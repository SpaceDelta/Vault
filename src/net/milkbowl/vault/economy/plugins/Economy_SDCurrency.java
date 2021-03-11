package net.milkbowl.vault.economy.plugins;

import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.spacedelta.sdcurrency.SDCurrencyPlugin;
import net.spacedelta.sdcurrency.hook.VaultCurrencyImpl;
import net.spacedelta.starship.util.D;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.logging.Logger;

public class Economy_SDCurrency extends AbstractEconomy {

    private final Logger log;
    private SDCurrencyPlugin currencyPlugin;

    private VaultCurrencyImpl economy;

    public Economy_SDCurrency(Plugin plugin) {
        this.log = plugin.getLogger();

        Bukkit.getServer().getPluginManager().registerEvents(new EconomyServerListener(this), plugin);

        // Load Plugin in case it was loaded before
        if (economy == null) {
            Plugin currencyPlugin = plugin.getServer().getPluginManager().getPlugin("SDCurrency");
            if (currencyPlugin != null && currencyPlugin.isEnabled()) {
                this.currencyPlugin = (SDCurrencyPlugin) currencyPlugin;
                log.info(String.format("[Economy] %s hooked.", currencyPlugin.getName()));
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return currencyPlugin != null && currencyPlugin.isEnabled();
    }

    @Override
    public String getName() {
        return "SDCurrency";
    }

    @Override
    public boolean hasBankSupport() {
        return economy.hasBankSupport();
    }

    @Override
    public int fractionalDigits() {
        return economy.fractionalDigits();
    }

    @Override
    public String format(double v) {
        return economy.format(v);
    }

    @Override
    public String currencyNamePlural() {
        return economy.currencyNamePlural();
    }

    @Override
    public String currencyNameSingular() {
        return economy.currencyNameSingular();
    }

    @Override
    public boolean hasAccount(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getBalance(String s) {
        throw new NotImplementedException();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return economy.getBalance(offlinePlayer);
    }

    @Override
    public double getBalance(String s, String s1) {
        throw new NotImplementedException();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return getBalance(offlinePlayer);
    }

    @Override
    public boolean has(String s, double v) {
        throw new NotImplementedException();
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return economy.has(offlinePlayer, v);
    }

    @Override
    public boolean has(String s, String s1, double v) {
        throw new NotImplementedException();
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return has(offlinePlayer, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return economy.withdrawPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return depositPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return depositPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        throw new NotImplementedException();
    }

    @Override
    public List<String> getBanks() {
        throw new NotImplementedException();
    }

    @Override
    public boolean createPlayerAccount(String s) {
        throw new NotImplementedException();
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return true;
    }

    public class EconomyServerListener implements Listener {
        private final Economy_SDCurrency economy;

        public EconomyServerListener(Economy_SDCurrency economy) {
            this.economy = economy;
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPluginEnable(PluginEnableEvent event) {
            if (economy.economy == null) {
                final Plugin plugin = event.getPlugin();

                if (event.getPlugin().getDescription().getName().equals("SDCurrency")) {
                    economy.currencyPlugin = (SDCurrencyPlugin) plugin;
                    economy.economy = economy.currencyPlugin.getCurrencyManager().getVaultCurrency();

                    log.info(String.format("[Economy] %s hooked.", economy.getName()));
                }
            }
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPluginDisable(PluginDisableEvent event) {
            if (economy.economy != null) {
                if (event.getPlugin().getDescription().getName().equals("SDCurrency")) {
                    economy.economy = null;
                    log.info(String.format("[Economy] %s unhooked.", economy.getName()));
                }
            }
        }

    }

}
