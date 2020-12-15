package travelator;

import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

public class CampSites {

    public static Set<CampSite> sitesInRegion(
        Set<CampSite> sites,
        String countryISO,
        String region
    ) {
        return sites.stream()
            .filter( campSite ->
                campSite.getCountryCode().equals(countryISO) &&
                    campSite.region().equalsIgnoreCase(region)
            )
            .collect(toUnmodifiableSet());
    }
}