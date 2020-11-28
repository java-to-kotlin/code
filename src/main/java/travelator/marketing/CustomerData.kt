package travelator.marketing

data class CustomerData(
    val id: String,
    val givenName: String,
    val familyName: String,
    val score: Int,
    val spend: Double
)