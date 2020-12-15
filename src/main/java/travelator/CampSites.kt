package travelator

fun Iterable<CampSite>.sitesInRegion(
    countryISO: String,
    region: String
): Set<CampSite> =
    filter { site ->
        site.isIn(countryISO, region)
    }.toSet()

fun CampSite.isIn(countryISO: String, region: String) =
    countryCode == countryISO &&
        this.region.equals(region, ignoreCase = true)