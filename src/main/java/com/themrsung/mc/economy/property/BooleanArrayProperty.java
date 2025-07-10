package com.themrsung.mc.economy.property;

/**
 * {@code boolean} array property.
 */
public class BooleanArrayProperty extends ArrayProperty<Boolean> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    BooleanArrayProperty(String name, boolean[] value) {
        super(name, boxedBooleans(value));
    }

    private static Boolean[] boxedBooleans(boolean[] values) {
        if (values == null) return null;

        Boolean[] result = new Boolean[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i];
        }

        return result;
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.BOOLEAN_ARRAY;
    }
}
