package com.themrsung.mc.economy.property;

import java.util.Arrays;

/**
 * {@code long} array property.
 */
public class LongArrayProperty extends ArrayProperty<Long> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    LongArrayProperty(String name, long[] value) {
        super(name, Arrays.stream(value).boxed().toArray(Long[]::new));
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.LONG_ARRAY;
    }
}
