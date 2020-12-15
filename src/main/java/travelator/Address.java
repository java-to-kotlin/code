package travelator;

public class Address {
    private final String line1;
    private final String line2;
    private final String locality;
    private final String region;
    private final String postalCode;
    private final String countryCode;

    public Address(String line1, String line2, String locality, String region, String postalCode, String countryCode) {
        this.line1 = line1;
        this.line2 = line2;
        this.locality = locality;
        this.region = region;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getRegion() {
        return region;
    }
}