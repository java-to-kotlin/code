package travelator

data class EmailAddress(
    val localPart: String,
    val domain: String
) {

    override fun toString() = "$localPart@$domain"

    companion object {
        @JvmStatic
        fun parse(value: String): EmailAddress =
            value.lastIndexOf('@').let { atIndex ->
                require(!(atIndex < 1 || atIndex == value.length - 1)) {
                    "EmailAddress must be two parts separated by @"
                }
                EmailAddress(
                    value.substring(0, atIndex),
                    value.substring(atIndex + 1)
                )
            }
    }
}