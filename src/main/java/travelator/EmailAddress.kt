package travelator

data class EmailAddress(
    val localPart: String,
    val domain: String
) {

    override fun toString() = "$localPart@$domain"

    companion object {
        @JvmStatic
        fun parse(value: String): EmailAddress {
            require(!(
                value.lastIndexOf('@') < 1 ||
                    value.lastIndexOf('@') == value.length - 1)) {
                "EmailAddress must be two parts separated by @"
            }
            return EmailAddress(
                value.substring(0, value.lastIndexOf('@')),
                value.substring(value.lastIndexOf('@') + 1)
            )
        }
    }
}