package com.themrsung.mc.economy.property;

import java.util.Arrays;

/**
 * String array property.
 */
public class StringArrayProperty extends ArrayProperty<String> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    StringArrayProperty(String name, String[] value) {
        super(name, Arrays.stream(value).toArray(String[]::new));
    }

    @Override
    public void setValue(String[] value) {
        super.setValue(Arrays.stream(value).toArray(String[]::new));
    }

    @Override
    public void setElement(int i, String element) throws NullPointerException, IndexOutOfBoundsException {
        super.setElement(i, element);
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.STRING_ARRAY;
    }
}
