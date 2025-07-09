package com.themrsung.mc.economy;

import com.themrsung.mc.VaultX;
import com.themrsung.mc.event.balance.BalanceDepositedByVaultEvent;
import com.themrsung.mc.event.balance.BalanceWithdrawnByVaultEvent;
import com.themrsung.mc.exception.UnknownAccountHolderImplementationException;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.text.NumberFormat;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of {@link EconomyVX}
 */
class EconomyVXImpl extends EconomyXImpl implements EconomyVX {
    public EconomyVXImpl(VaultX plugin) {
        super(plugin);
    }

    @Override
    public boolean isEnabled() {
        return plugin.isEnabled();
    }

    @Override
    public String getName() {
        return "VaultX with Legacy Support";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return NumberFormat.getNumberInstance().format(v) + currencyNamePlural(); // TODO
    }

    @Override
    public String currencyNamePlural() {
        return ""; // TODO
    }

    @Override
    public String currencyNameSingular() {
        return ""; // TODO
    }

    @Override
    public boolean hasAccount(String s) {
        return hasAccount(Bukkit.getOfflinePlayer(s));
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return accounts.stream()
                .anyMatch(a -> Objects.equals(a.getUniqueId(), offlinePlayer.getUniqueId()));
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return hasAccount(Bukkit.getOfflinePlayer(s));
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return hasAccount(offlinePlayer);
    }

    @Override
    public double getBalance(String s) {
        return getBalance(Bukkit.getOfflinePlayer(s));
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        var account = getAccount(offlinePlayer.getUniqueId());
        if (account == null) return 0;

        return account.getWithdrawableBalance();
    }

    @Override
    public double getBalance(String s, String s1) {
        return getBalance(Bukkit.getOfflinePlayer(s));
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return getBalance(offlinePlayer);
    }

    @Override
    public boolean has(String s, double v) {
        return has(Bukkit.getOfflinePlayer(s), v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        var account = getAccount(offlinePlayer.getUniqueId());
        if (account == null) return false;

        return account.getWithdrawableBalance() >= v;
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return has(Bukkit.getOfflinePlayer(s), v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return has(offlinePlayer, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        return withdrawPlayer(Bukkit.getOfflinePlayer(s), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        var account = getAccount(offlinePlayer.getUniqueId());
        if (account == null || !containsAccount(account)) {
            return new EconomyResponse(v, 0, EconomyResponse.ResponseType.FAILURE, "User not found.");
        }

        if (account.getWithdrawableBalance() <= v) {
            return new EconomyResponse(v, account.getWithdrawableBalance(), EconomyResponse.ResponseType.FAILURE, "Insufficient funds.");
        }

        try {
            modifyBalance(account, -v);
        } catch (UnknownAccountHolderImplementationException e) {
            plugin.getLogger().warning(e.getMessage());
            return new EconomyResponse(v, account.getWithdrawableBalance(), EconomyResponse.ResponseType.FAILURE, "VaultX internal error.");
        }

        Bukkit.getPluginManager().callEvent(new BalanceWithdrawnByVaultEvent(account, v));

        return new EconomyResponse(v, account.getWithdrawableBalance(), EconomyResponse.ResponseType.SUCCESS, "Success.");
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return withdrawPlayer(Bukkit.getOfflinePlayer(s), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        return depositPlayer(Bukkit.getOfflinePlayer(s), v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        var account = getAccount(offlinePlayer.getUniqueId());
        if (account == null || !containsAccount(account)) {
            return new EconomyResponse(v, 0, EconomyResponse.ResponseType.FAILURE, "User not found.");
        }

        try {
            modifyBalance(account, v);
        } catch (UnknownAccountHolderImplementationException e) {
            plugin.getLogger().warning(e.getMessage());
            return new EconomyResponse(v, 0, EconomyResponse.ResponseType.FAILURE, "VaultX internal error.");
        }

        Bukkit.getPluginManager().callEvent(new BalanceDepositedByVaultEvent(account, v));

        return new EconomyResponse(v, account.getWithdrawableBalance(), EconomyResponse.ResponseType.SUCCESS, "Success.");
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return depositPlayer(Bukkit.getOfflinePlayer(s), v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return depositPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public List<String> getBanks() {
        return List.of();
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return createPlayerAccount(Bukkit.getOfflinePlayer(s));
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        if (accounts.stream().anyMatch(a -> Objects.equals(a.getUniqueId(), offlinePlayer.getUniqueId()))) return false;

        var created = VXPlayer.from(offlinePlayer);
        return addAccount(created);
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return createPlayerAccount(s);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return createPlayerAccount(offlinePlayer);
    }
}
