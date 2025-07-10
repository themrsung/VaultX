package com.themrsung.mc.economy.property;

/**
 * The type of {@link AccountHolderProperty}.
 */
public enum AccountHolderPropertyType {
    // Objects
    STRING,
    COORDINATE,
    PLAYER,
    HOLDER,
    DATE,
    UUID,

    // Primitives
    DOUBLE,
    LONG,
    BOOLEAN,

    // Arrays
    STRING_ARRAY,
    COORDINATE_ARRAY,
    PLAYER_ARRAY,
    HOLDER_ARRAY,
    DATE_ARRAY,
    UUID_ARRAY,
    DOUBLE_ARRAY,
    LONG_ARRAY,
    BOOLEAN_ARRAY;
}
