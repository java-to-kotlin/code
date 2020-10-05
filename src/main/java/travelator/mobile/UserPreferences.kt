package travelator.mobile

import java.util.*

class UserPreferences(
    var greeting: String,
    var locale: Locale,
    var currency: Currency
) {
    constructor() : this(
        greeting = "Hello",
        locale = Locale.UK,
        currency = Currency.getInstance(Locale.UK)
    )
}