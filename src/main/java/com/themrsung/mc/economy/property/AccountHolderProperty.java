package com.themrsung.mc.economy.property;

import com.themrsung.mc.economy.VXAccountHolder;
import com.themrsung.mc.util.Coordinate;
import org.bukkit.OfflinePlayer;

import java.util.Date;
import java.util.UUID;

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
 * @see UUIDProperty
 * @see DoubleProperty
 * @see LongProperty
 * @see BooleanProperty
 * @see StringArrayProperty
 * @see CoordinateArrayProperty
 * @see PlayerArrayProperty
 * @see HolderArrayProperty
 * @see DateArrayProperty
 * @see UUIDArrayProperty
 * @see DoubleArrayProperty
 * @see LongArrayProperty
 * @see BooleanArrayProperty
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
     * Returns a new {@link StringArrayProperty string array property}.
     *
     * @param name   The name of the property
     * @param values The values of the property
     * @return The property
     */
    static StringArrayProperty newStringArray(String name, String... values) {
        return new StringArrayProperty(name, values);
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
     * Returns a new {@link CoordinateArrayProperty coordinate array property}.
     *
     * @param name   The name of the property
     * @param values The values of the property
     * @return The property
     */
    static CoordinateArrayProperty newCoordinateArray(String name, Coordinate... values) {
        return new CoordinateArrayProperty(name, values);
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
     * Returns a new {@link PlayerArrayProperty player array property}.
     *
     * @param name   The name of the property
     * @param values The values of the property
     * @return The property
     */
    static PlayerArrayProperty newPlayerArray(String name, OfflinePlayer... values) {
        return new PlayerArrayProperty(name, values);
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
     * Returns a new {@link HolderArrayProperty holder array property}.
     *
     * @param name   The name of the property
     * @param values The values of the property
     * @return The property
     */
    static HolderArrayProperty newHolderArray(String name, VXAccountHolder... values) {
        return new HolderArrayProperty(name, values);
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
     * Returns a new {@link DateArrayProperty date array property}.
     *
     * @param name   The name of the property
     * @param values The values of the property
     * @return The property
     */
    static DateArrayProperty newDateArray(String name, Date... values) {
        return new DateArrayProperty(name, values);
    }

    /**
     * Returns a new {@link UUIDProperty UUID property}.
     *
     * @param name  The name of the property
     * @param value The value of the property
     * @return The property
     */
    static UUIDProperty newUniqueId(String name, UUID value) {
        return new UUIDProperty(name, value);
    }

    /**
     * Returns a new {@link UUIDArrayProperty UUID array property}.
     *
     * @param name   The name of the property
     * @param values The values of the property
     * @return The property
     */
    static UUIDArrayProperty newUniqueIdArray(String name, UUID... values) {
        return new UUIDArrayProperty(name, values);
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
     * Returns a new {@link DoubleArrayProperty double array property}.
     *
     * @param name   The name of the property
     * @param values The values of the property
     * @return The property
     */
    static DoubleArrayProperty newDoubleArray(String name, double... values) {
        return new DoubleArrayProperty(name, values);
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
     * Returns a new {@link LongArrayProperty long array property}.
     *
     * @param name   The name of the property
     * @param values The values of the property
     * @return The property
     */
    static LongArrayProperty newLongArray(String name, long... values) {
        return new LongArrayProperty(name, values);
    }

    /**
     * Returns a new {@link BooleanProperty boolean property}.
     *
     * @param name  The name of the property
     * @param value The value of the property
     * @return The property
     */
    static BooleanProperty newBoolean(String name, boolean value) {
        return new BooleanProperty(name, value);
    }

    /**
     * Returns a new {@link BooleanArrayProperty boolean array property}.
     *
     * @param name   The name of the property
     * @param values The values of the property
     * @return The property
     */
    static BooleanArrayProperty newBooleanArray(String name, boolean... values) {
        return new BooleanArrayProperty(name, values);
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
