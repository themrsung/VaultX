package com.themrsung.mc.economy.property;

import java.util.Date;

/**
 * Date array property.
 */
public class DateArrayProperty extends ArrayProperty<Date> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    DateArrayProperty(String name, Date[] value) {
        super(name, value);
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.DATE_ARRAY;
    }
}
