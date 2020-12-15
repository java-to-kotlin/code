package travelator

fun Iterable<CampSite>.sitesInRegion(
    countryISO: String,
    region: String
): Set<CampSite> =
    filter { site ->
        site.countryCode == countryISO &&
            site.region.equals(region, ignoreCase = true)
    }.toSet()