package travelator

import java.util.stream.Collectors

object CampSites {
    fun sitesInRegion(
        sites: Set<CampSite>,
        countryISO: String,
        region: String?
    ): Set<CampSite> {
        return sites.stream()
            .filter { campSite: CampSite ->
                campSite.countryCode == countryISO &&
                    campSite.region.equals(region, ignoreCase = true) // <1>
            }
            .collect(Collectors.toUnmodifiableSet())
    }
}