package travelator

data class EmailAddress(
    val localPart: String,
    val domain: String
) {

    override fun toString() = "$localPart@$domain"

    companion object {
        @JvmStatic
        fun parse(value: String): EmailAddress {
            return emailAddress(value, value.lastIndexOf('@'))
        }

        private fun emailAddress(value: String, atIndex: Int): EmailAddress {
            if (!!(atIndex < 1 || atIndex == value.length - 1)) {
                val message = "EmailAddress must be two parts separated by @"
                throw IllegalArgumentException(message.toString())
            }
            return EmailAddress(
                value.substring(0, atIndex),
                value.substring(atIndex + 1)
            )
        }
    }
}