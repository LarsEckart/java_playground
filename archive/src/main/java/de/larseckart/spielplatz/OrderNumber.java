package de.larseckart.spielplatz;

import java.util.Formattable;
import java.util.Formatter;

public class OrderNumber implements Formattable {

    protected OrderNumber() {

    }

    public static OrderNumber of() {
        return new OrderNumber();
    }

    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision) {

    }
}

