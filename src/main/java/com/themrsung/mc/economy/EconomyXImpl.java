package com.themrsung.mc.economy;

import com.themrsung.mc.VaultX;
import com.themrsung.mc.event.balance.BalanceIssuedEvent;
import com.themrsung.mc.event.balance.BalanceRetiredEvent;
import com.themrsung.mc.event.balance.BalanceTransferredEvent;
import com.themrsung.mc.event.premium.PremiumBalanceIssuedEvent;
import com.themrsung.mc.event.premium.PremiumBalanceRetiredEvent;
import com.themrsung.mc.event.premium.PremiumBalanceTransferredEvent;
import com.themrsung.mc.exception.UnknownAccountHolderImplementationException;
import com.themrsung.mc.io.VXIO;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Default implementation of {@link EconomyX}.
 */
class EconomyXImpl implements EconomyX {
    public EconomyXImpl(VaultX plugin) {
        this.plugin = Objects.requireNonNull(plugin);
        this.accounts = new HashSet<>();
    }

    protected final VaultX plugin;
    protected final Set<VXAccountHolder> accounts;

    @Override
    public @NotNull Stream<VXAccountHolder> getAccounts() {
        return accounts.stream();
    }

    @Override
    public @NotNull Stream<VXAccountHolder> getAccountsByName(String name) {
        return accounts.stream()
                .filter(a -> Objects.requireNonNullElse(a.getName(), "").equalsIgnoreCase(name));
    }

    @Override
    public @Nullable VXAccountHolder getAccount(UUID uniqueId) {
        return accounts.stream()
                .filter(a -> Objects.equals(a.getUniqueId(), uniqueId))
                .findAny()
                .orElse(null);
    }

    @Override
    public @NotNull VXPlayer getAccountOrNew(OfflinePlayer player) {
        var existing = getAccount(player.getUniqueId());
        if (existing != null) {
            try {
                return (VXPlayer) existing;
            } catch (ClassCastException e) {
                throw new RuntimeException("Existing account found for player, but is not an instance of VXPlayer.", e);
            }
        }

        var created = VXPlayer.from(player);

        if (!addAccount(created)) {
            throw new RuntimeException("Failed to register account for player: " + created.getUniqueId());
        }

        return created;
    }

    @Override
    public boolean addAccount(VXAccountHolder account) {
        return accounts.add(account);
    }

    @Override
    public boolean removeAccount(VXAccountHolder account) {
        return accounts.remove(account);
    }

    @Override
    public boolean containsAccount(VXAccountHolder account) {
        return accounts.contains(account);
    }

    @Override
    public @NotNull BalanceTransferResult transferBalance(VXAccountHolder sender, VXAccountHolder recipient, double amount) {
        if (!containsAccount(sender)) return BalanceTransferResult.FAILURE_INVALID_SENDER;
        if (!containsAccount(recipient)) return BalanceTransferResult.FAILURE_INVALID_RECIPIENT;
        if (sender.getWithdrawableBalance() < amount) return BalanceTransferResult.FAILURE_INSUFFICIENT_FUNDS;

        try {
            modifyBalance(sender, -amount);
            modifyBalance(recipient, +amount);
        } catch (UnknownAccountHolderImplementationException e) {
            plugin.getLogger().warning(e.getMessage());
            return BalanceTransferResult.FAILURE_UNKNOWN_IMPLEMENTATION;
        }

        Bukkit.getPluginManager().callEvent(new BalanceTransferredEvent(sender, recipient, amount));

        return BalanceTransferResult.SUCCESS;
    }

    @Override
    public boolean issueBalance(VXAccountHolder creditor, double amount) {
        if (!containsAccount(creditor)) return false;

        try {
            modifyBalance(creditor, amount);
        } catch (UnknownAccountHolderImplementationException e) {
            plugin.getLogger().warning(e.getMessage());
            return false;
        }

        Bukkit.getPluginManager().callEvent(new BalanceIssuedEvent(creditor, amount));

        return true;
    }

    @Override
    public boolean retireBalance(VXAccountHolder debtor, double amount) {
        if (!containsAccount(debtor)) return false;

        try {
            modifyBalance(debtor, -amount);
        } catch (UnknownAccountHolderImplementationException e) {
            plugin.getLogger().warning(e.getMessage());
            return false;
        }

        Bukkit.getPluginManager().callEvent(new BalanceRetiredEvent(debtor, amount));

        return false;
    }

    @Override
    public @NotNull PremiumBalanceTransferResult transferPremiumBalance(VXAccountHolder sender, VXAccountHolder recipient, long amount) {
        if (!containsAccount(sender)) return PremiumBalanceTransferResult.FAILURE_INVALID_SENDER;
        if (!containsAccount(recipient)) return PremiumBalanceTransferResult.FAILURE_INVALID_RECIPIENT;
        if (sender.getWithdrawablePremiumBalance() < amount)
            return PremiumBalanceTransferResult.FAILURE_INSUFFICIENT_FUNDS;

        try {
            modifyPremiumBalance(sender, -amount);
            modifyPremiumBalance(recipient, +amount);
        } catch (UnknownAccountHolderImplementationException e) {
            plugin.getLogger().warning(e.getMessage());
            return PremiumBalanceTransferResult.FAILURE_UNKNOWN_IMPLEMENTATION;
        }

        Bukkit.getPluginManager().callEvent(new PremiumBalanceTransferredEvent(sender, recipient, amount));

        return PremiumBalanceTransferResult.SUCCESS;
    }

    @Override
    public boolean issuePremiumBalance(VXAccountHolder creditor, long amount) {
        if (!containsAccount(creditor)) return false;

        try {
            modifyPremiumBalance(creditor, amount);
        } catch (UnknownAccountHolderImplementationException e) {
            plugin.getLogger().warning(e.getMessage());
            return false;
        }

        Bukkit.getPluginManager().callEvent(new PremiumBalanceIssuedEvent(creditor, amount));

        return true;
    }

    @Override
    public boolean retirePremiumBalance(VXAccountHolder debtor, long amount) {
        if (!containsAccount(debtor)) return false;

        try {
            modifyPremiumBalance(debtor, -amount);
        } catch (UnknownAccountHolderImplementationException e) {
            plugin.getLogger().warning(e.getMessage());
            return false;
        }

        Bukkit.getPluginManager().callEvent(new PremiumBalanceRetiredEvent(debtor, amount));

        return false;
    }

    // Utilities

    protected void modifyBalance(VXAccountHolder account, double impact) throws UnknownAccountHolderImplementationException {
        if (account instanceof VXPlayerImpl vxpi) {
            vxpi.balance += impact;
            return;
        } else if (account instanceof VXInstitutionImpl vxii) {
            vxii.balance += impact;
            return;
        } else if (account instanceof VXPluginUser vxpu) {
            vxpu.modifyBalance(this, impact);
            return;
        }

        throw new UnknownAccountHolderImplementationException(this, account, "Unknown implementation of VXAccountHolder. Cannot modify balance!");
    }

    protected void modifyPremiumBalance(VXAccountHolder account, long impact) throws UnknownAccountHolderImplementationException {
        if (account instanceof VXPlayerImpl vxpi) {
            vxpi.premiumBalance += impact;
            return;
        } else if (account instanceof VXInstitutionImpl vxii) {
            vxii.premiumBalance += impact;
            return;
        } else if (account instanceof VXPluginUser vxpu) {
            vxpu.modifyPremiumBalance(this, impact);
            return;
        }

        throw new UnknownAccountHolderImplementationException(this, account, "Unknown implementation of VXAccountHolder. Cannot modify premium balance!");
    }

    private static final String savePath = "plugins/VaultX/economy";

    @Override
    public void save() throws IOException {
        var dir = new File(savePath);
        dir.mkdirs();

        for (var account : accounts) {
            if (account.getType() == AccountHolderType.THIRD_PARTY_PLUGIN || account.getType() == AccountHolderType.INERT_REF)
                continue;

            var file = new File(savePath + "/" + account.getUniqueId() + ".yml");
            var lines = VXIO.serializeAccountHolder(account);

            Files.write(file.toPath(), lines);
        }
    }

    @Override
    public void load() throws IOException {
        var dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
            return; // Nothing to load
        }

        var files = dir.listFiles((d, name) -> name.endsWith(".yml"));
        if (files == null) return; // Nothing to load

        // Clear users, but third-party users should not be removed
        accounts.removeIf(a -> !(a instanceof VXPluginUser));

        for (var file : files) {
            var lines = Files.readAllLines(file.toPath());
            var account = VXIO.deserializeAccountHolder(lines);
            if (account == null) continue;

            accounts.add(account);
        }

        VXIO.reviveRefs(accounts);
    }
}
