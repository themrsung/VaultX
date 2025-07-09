package com.themrsung.mc.economy.property;

import java.util.Date;

/**
 * {@link Date} property.
 */
public class DateProperty extends ObjectProperty<Date> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    DateProperty(String name, Date value) {
        super(name, value);
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.DATE;
    }
}
