package com.themrsung.mc.economy.property;

/**
 * {@code double} property.
 */
public class DoubleProperty implements AccountHolderProperty {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    DoubleProperty(String name, double value) {
        this.name = name;
        this.value = value;
    }

    protected final String name;
    protected double value;

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
