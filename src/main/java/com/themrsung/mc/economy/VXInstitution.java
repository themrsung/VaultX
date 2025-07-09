package com.themrsung.mc.economy;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * An extension of {@link VXAccountHolder} made for institutions.
 */
public interface VXInstitution extends VXAccountHolder {
    /**
     * Creates a new institution.
     *
     * @param name  The name of the institution
     * @param owner The owner of the institution
     * @return The created institution
     */
    static VXInstitution createInstitution(String name, OfflinePlayer owner) {
        return new VXInstitutionImpl(UUID.randomUUID(), name, owner);
    }

    /**
     * Sets the name of this institution.
     *
     * @param name The name of this institution
     */
    void setName(String name);

    /**
     * Returns the owner of this institution.
     *
     * @return The owner of this institution
     */
    @NotNull OfflinePlayer getOwner();

    /**
     * Sets the owner of this institution.
     *
     * @param player The owner of this institution
     */
    void setOwner(OfflinePlayer player);

    /**
     * Returns a {@link Stream stream} of the members of this institution.
     *
     * @return A stream of the members of this institution
     */
    @NotNull Stream<OfflinePlayer> getMembers();

    /**
     * Adds player as a member.
     *
     * @param player The player to add
     * @return {@code true} if the player was added
     */
    boolean addMember(OfflinePlayer player);

    /**
     * Removes the player from this association.
     *
     * @param player The player to remove
     * @return {@code true} if membership was revoked
     */
    boolean removeMember(OfflinePlayer player);

    /**
     * Returns {@code true} if the player is either the owner or a member of this institution.
     *
     * @param player The player to check for
     * @return {@code true} if the player is associated with this institution
     */
    boolean isAssociated(OfflinePlayer player);

    /**
     * Returns whether the given player owns this institution.
     *
     * @param player The player to check for
     * @return {@code true} if the player owns this institution
     */
    boolean isOwner(OfflinePlayer player);

    /**
     * Checks whether the given player is a member of this institution.
     *
     * @param player The player to check for
     * @return {@code true} if the player is a member
     */
    boolean isMember(OfflinePlayer player);
}

