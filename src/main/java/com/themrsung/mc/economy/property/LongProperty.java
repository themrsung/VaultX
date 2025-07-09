package com.themrsung.mc.economy.property;

/**
 * {@code long} property.
 */
public class LongProperty implements AccountHolderProperty {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    LongProperty(String name, long value) {
        this.name = name;
        this.value = value;
    }

    protected final String name;
    protected long value;

    public String getName() {
        return name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
