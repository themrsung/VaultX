package com.themrsung.mc.economy.property;

/**
 * Base class for object-based properties.
 *
 * @param <T> The object to designate
 */
public abstract class ObjectProperty<T> implements AccountHolderProperty<T> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    ObjectProperty(String name, T value) {
        this.name = name;
        this.value = value;
    }

    protected final String name;
    protected T value;

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
