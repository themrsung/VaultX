package com.themrsung.mc.economy.property;

import com.themrsung.mc.economy.VXAccountHolder;
import com.themrsung.mc.util.Coordinate;
import org.bukkit.OfflinePlayer;

/**
 * An {@link VXAccountHolder account holder}'s property.
 *
 * @see StringProperty
 * @see CoordinateProperty
 * @see OfflinePlayerProperty
 * @see HolderProperty
 * @see DoubleProperty
 * @see LongProperty
 */
public interface AccountHolderProperty {
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
     * Returns a new {@link OfflinePlayerProperty player property}.
     *
     * @param name  The name of the property
     * @param value The value of the property
     * @return The property
     */
    static OfflinePlayerProperty newPlayer(String name, OfflinePlayer value) {
        return new OfflinePlayerProperty(name, value);
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
}
