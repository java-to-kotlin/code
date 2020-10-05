package travelator.mobile

import java.util.*

data class UserPreferences(
    val greeting: String,
    val locale: Locale,
    val currency: Currency
)