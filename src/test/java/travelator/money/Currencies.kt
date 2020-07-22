package travelator.money

import java.util.*

// These all have 100 minor units per major unit
val GBP = Currency.getInstance("GBP")
val USD = Currency.getInstance("USD")
val EUR = Currency.getInstance("EUR")

// JOD has 1000 minor units per major unit
val JOD = Currency.getInstance("JOD")

// JPY has no minor units
val JPY = Currency.getInstance("JPY")