package com.themrsung.mc.economy.property;

import com.themrsung.mc.util.Coordinate;

/**
 * Coordinate array property.
 */
public class CoordinateArrayProperty extends ArrayProperty<Coordinate> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    CoordinateArrayProperty(String name, Coordinate[] value) {
        super(name, value);
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.COORDINATE_ARRAY;
    }
}
