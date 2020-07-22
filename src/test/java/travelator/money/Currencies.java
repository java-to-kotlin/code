package travelator.money;

import java.util.Currency;

public class Currencies {
    // These all have 100 minor units per major unit
    public static final Currency GBP = Currency.getInstance("GBP");
    public static final Currency USD = Currency.getInstance("USD");
    public static final Currency EUR = Currency.getInstance("EUR");

    // JOD has 1000 minor units per major unit
    public static final Currency JOD = Currency.getInstance("JOD");

    // JPY has no minor units
    public static final Currency JPY = Currency.getInstance("JPY");
}