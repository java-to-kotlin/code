package travelator

import java.util.stream.Collectors

fun Set<CampSite>.sitesInRegion(
    countryISO: String,
    region: String
): Set<CampSite> {
    return stream()
        .filter { campSite: CampSite ->
            campSite.countryCode == countryISO &&
                campSite.region.equals(region, ignoreCase = true)
        }
        .collect(Collectors.toUnmodifiableSet())
}