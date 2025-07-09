package com.themrsung.mc.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

/**
 * Abstract parent class for all VaultX events.
 */
public abstract class VXEvent extends Event {
    /**
     * Creates a new VX Event.
     */
    public VXEvent() {
        this.uniqueId = UUID.randomUUID();
        this.date = new Date();
    }

    /**
     * Creates a new VX Event with the given unique identifier.
     *
     * @param uniqueId The unique identifier
     */
    public VXEvent(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.date = new Date();
    }

    /**
     * Creates a new VX Event with the given parameters.
     *
     * @param uniqueId The unique identifier
     * @param date     The date
     */
    public VXEvent(UUID uniqueId, Date date) {
        this.uniqueId = uniqueId;
        this.date = date;
    }

    protected final UUID uniqueId;
    protected final Date date;

    /**
     * Returns the unique identifier of this event.
     *
     * @return The unique identifier of this event
     */
    public UUID getUniqueId() {
        return uniqueId;
    }

    /**
     * Returns the date of this event.
     *
     * @return The date of this event
     */
    public Date getDate() {
        return date;
    }

    // Bukkit Event boilerplate
    private static final HandlerList handlerList = new HandlerList();

    /**
     * Returns the {@link HandlerList}.
     *
     * @return The handler list
     */
    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
