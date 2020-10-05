package travelator.mobile

import java.util.*

class PreferencesView(
    private val preferences: UserPreferences
) : View() {
    private val greetingPicker = GreetingPicker()
    private val localePicker = LocalePicker()
    private val currencyPicker = CurrencyPicker()

    override fun show() {
        greetingPicker.greeting = preferences.greeting
        localePicker.locale = preferences.locale
        currencyPicker.currency = preferences.currency
        super.show()
    }

    protected fun onGreetingChange() {
        preferences.greeting = greetingPicker.greeting
    }


    protected fun onLocaleChange() {
        preferences.locale = localePicker.locale
    }

    protected fun onCurrencyChange() {
        preferences.currency = currencyPicker.currency
    }
}

internal class GreetingPicker {
    var greeting: String = TODO()
}

internal class LocalePicker {
    var locale: Locale = TODO()
}

internal class CurrencyPicker {
    var currency: Currency = TODO()
}