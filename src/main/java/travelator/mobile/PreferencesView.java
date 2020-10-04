package travelator.mobile;

import java.util.Currency;
import java.util.Locale;

public class PreferencesView extends View {

    private final UserPreferences preferences;
    private final GreetingPicker greetingPicker = new GreetingPicker();
    private final LocalePicker localePicker = new LocalePicker();
    private final CurrencyPicker currencyPicker = new CurrencyPicker();

    public PreferencesView(UserPreferences preferences) {
        this.preferences = preferences;
    }

    public void show() {
        greetingPicker.setGreeting(preferences.getGreeting());
        localePicker.setLocale(preferences.getLocale());
        currencyPicker.setCurrency(preferences.getCurrency());
        super.show();
    }

    protected void onGreetingChange() {
        preferences.setGreeting(greetingPicker.getGreeting());
    }

    protected void onLocaleChange() {
        preferences.setLocale(localePicker.getLocale());
    }

    protected void onCurrencyChange() {
        preferences.setCurrency(currencyPicker.getCurrency());
    }
}

class GreetingPicker {
    private String greeting;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}

class LocalePicker {
    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}

class CurrencyPicker {
    private Currency currency;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}