package com.themrsung.mc.economy.property;

import com.themrsung.mc.economy.VXAccountHolder;
import com.themrsung.mc.util.Coordinate;
import org.bukkit.OfflinePlayer;

import java.util.Date;

/**
 * <h1>An {@link VXAccountHolder account holder}'s property.</h1>
 * Properties are used to store variables such as an institution's founder, a player's first
 * join date, etc. Only the seven types listed below are supported as properties, which are
 * automatically saved and loaded by VaultX's IO system.
 *
 * @param <T> The type of value to store
 * @see StringProperty
 * @see CoordinateProperty
 * @see PlayerProperty
 * @see HolderProperty
 * @see DateProperty
 * @see DoubleProperty
 * @see LongProperty
 */
public interface AccountHolderProperty<T> {
    /**
     * Returns a new {@link StringProperty string property}.
     *
     * @param name  The name of the property
     * @param value The value of the property
     * @return The property
     */
    static StringProperty newString(String name, String value) {
        return new StringProperty(name, value);
    }

    /**
     * Returns a new {@link CoordinateProperty coordinate property}.
     *
     * @param name  The name of the property
     * @param value The value of the property
     * @return The property
     */
    static CoordinateProperty newCoordinate(String name, Coordinate value) {
        return new CoordinateProperty(name, value);
    }

    /**
     * Returns a new {@link PlayerProperty player property}.
     *
     * @param name  The name of the property
     * @param value The value of the property
     * @return The property
     */
    static PlayerProperty newPlayer(String name, OfflinePlayer value) {
        return new PlayerProperty(name, value);
    }

    /**
     * Returns a new {@link HolderProperty holder property}.
     *
     * @param name  The name of the property
     * @param value The value of the property
     * @return The property
     */
    static HolderProperty newHolder(String name, VXAccountHolder value) {
        return new HolderProperty(name, value);
    }

    /**
     * Returns a new {@link DateProperty date property}.
     *
     * @param name  The name of the property
     * @param value The value of the property
     * @return The property
     */
    static DateProperty newDate(String name, Date value) {
        return new DateProperty(name, value);
    }

    /**
     * Returns a new {@link DoubleProperty double property}.
     *
     * @param name  The name of the property
     * @param value The value of the property
     * @return The property
     */
    static DoubleProperty newDouble(String name, double value) {
        return new DoubleProperty(name, value);
    }

    /**
     * Returns a new {@link LongProperty long property}.
     *
     * @param name  The name of the property
     * @param value The value of the property
     * @return The property
     */
    static LongProperty newLong(String name, long value) {
        return new LongProperty(name, value);
    }

    /**
     * Returns the name of this property.
     *
     * @return The name of this property
     */
    String getName();

    /**
     * Returns the value of this property.
     *
     * @return The value of this property
     */
    T getValue();

    /**
     * Sets the value of this property.
     *
     * @param value The value to set to
     */
    void setValue(T value);

    /**
     * Returns the type of this property.
     *
     * @return The type of this property
     */
    AccountHolderPropertyType getType();
}
