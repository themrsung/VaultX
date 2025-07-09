package com.themrsung.mc.economy;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Default implementation of {@link VXInstitution}.
 */
class VXInstitutionImpl extends VXAbstractAccountHolder implements VXInstitution {
    /**
     * Default constructor.
     *
     * @param uniqueId The unique identifier
     * @param name     The name
     * @param owner    The owner
     */
    VXInstitutionImpl(UUID uniqueId, String name, OfflinePlayer owner) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.owner = owner;
        this.members = new HashSet<>();
        this.balance = 0;
        this.premiumBalance = 0;
    }

    private final UUID uniqueId;
    private String name;
    private OfflinePlayer owner;
    final Set<OfflinePlayer> members;
    double balance;
    long premiumBalance;

    @Override
    public @NotNull UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public @Nullable String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public @NotNull AccountHolderType getType() {
        return AccountHolderType.INSTITUTION;
    }

    @Override
    public @NotNull OfflinePlayer getOwner() {
        return owner;
    }

    @Override
    public void setOwner(OfflinePlayer owner) {
        this.owner = owner;
        members.remove(owner); // Owner cannot be member
    }

    @Override
    public @NotNull Stream<OfflinePlayer> getMembers() {
        return members.stream();
    }

    @Override
    public boolean addMember(OfflinePlayer player) {
        if (Objects.equals(owner.getUniqueId(), player.getUniqueId())) return false; // Owner cannot be member
        return members.add(player);
    }

    @Override
    public boolean removeMember(OfflinePlayer player) {
        return members.remove(player);
    }

    @Override
    public boolean isAssociated(OfflinePlayer player) {
        return isOwner(player) || isMember(player);
    }

    @Override
    public boolean isOwner(OfflinePlayer player) {
        return Objects.equals(owner.getUniqueId(), player.getUniqueId());
    }

    @Override
    public boolean isMember(OfflinePlayer player) {
        return members.stream().anyMatch(m -> Objects.equals(m.getUniqueId(), player.getUniqueId()));
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public double getWithdrawableBalance() {
        return balance;
    }

    @Override
    public long getPremiumBalance() {
        return premiumBalance;
    }

    @Override
    public long getWithdrawablePremiumBalance() {
        return premiumBalance;
    }
}
