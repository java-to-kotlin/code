package travelator

data class EmailAddress(
    val localPart: String,
    val domain: String
) {

    override fun toString() = "$localPart@$domain"

    companion object {
        @JvmStatic
        fun parse(value: String): EmailAddress =
            value.splitAroundLast('@').let { (leftPart, rightPart) ->
                EmailAddress(leftPart, rightPart)
            }
    }
}

private fun String.splitAroundLast(divider: Char): Pair<String, String> =
    lastIndexOf(divider).let { index ->
        require(index >= 1 && index != length - 1) {
            "string must be two non-empty parts separated by $divider"
        }
        substring(0, index) to substring(index + 1)
    }

