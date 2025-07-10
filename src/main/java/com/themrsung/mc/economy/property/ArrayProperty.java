package com.themrsung.mc.economy.property;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.IntFunction;

/**
 * Base class for array properties.
 *
 * @param <T> The object to array-ify
 */
public abstract class ArrayProperty<T> extends ObjectProperty<T[]> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    ArrayProperty(String name, T[] value) {
        super(name, value);
    }

    /**
     * Initializes this array.
     *
     * @param size The size of the new array
     */
    @SuppressWarnings("unchecked")
    public void initializeArray(int size) {
        value = (T[]) new Object[size];
    }

    /**
     * Initializes this array.
     *
     * @param size      The size of the new array
     * @param generator The generator function to fill the array with
     */
    @SuppressWarnings("unchecked")
    public void initializeArray(int size, IntFunction<T> generator) {
        var array = (T[]) new Object[size];
        Arrays.setAll(array, generator);

        value = array;
    }

    /**
     * Returns the size of this array.
     *
     * @return The size of this array
     */
    public int getSize() {
        if (value == null) return 0;

        return value.length;
    }

    /**
     * Returns the {@code i}th element.
     *
     * @param i The index to get
     * @return The element if present, {@code null} otherwise
     */
    public @Nullable T getElement(int i) {
        try {
            return value[i];
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * Sets the {@code i}th element.
     *
     * @param i       The index to set
     * @param element The element to set
     * @throws NullPointerException      When the array is {@code null}
     * @throws IndexOutOfBoundsException When the index is out of bounds
     */
    public void setElement(int i, T element) throws NullPointerException, IndexOutOfBoundsException {
        value[i] = element;
    }
}
