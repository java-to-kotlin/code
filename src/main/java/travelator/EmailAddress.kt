package travelator

data class EmailAddress(
    val localPart: String,
    val domain: String
) {

    override fun toString() = "$localPart@$domain"

    companion object {
        @JvmStatic
        fun parse(value: String): EmailAddress =
            value.split('@').let { (leftPart, rightPart) ->
                EmailAddress(leftPart, rightPart)
            }

        private fun String.split(divider: Char): Pair<String, String> {
            val atIndex = lastIndexOf(divider)
            require(!(atIndex < 1 || atIndex == length - 1)) {
                "EmailAddress must be two parts separated by @"
            }
            val leftPart = substring(0, atIndex)
            val rightPart = substring(atIndex + 1)
            return Pair(leftPart, rightPart)
        }
    }
}