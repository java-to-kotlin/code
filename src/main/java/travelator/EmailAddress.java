package travelator;

import java.util.Objects;

public class EmailAddress {
    private final String localPart; // <1>
    private final String domain;

    public static EmailAddress parse(String value) { // <2>
        var atIndex = value.lastIndexOf('@');
        if (atIndex < 1 || atIndex == value.length() - 1)
            throw new IllegalArgumentException(
                "EmailAddress must be two parts separated by @"
            );
        return new EmailAddress(
            value.substring(0, atIndex),
            value.substring(atIndex + 1)
        );
    }

    public EmailAddress(String localPart, String domain) { // <3>
        this.localPart = localPart;
        this.domain = domain;
    }

    public String getLocalPart() { // <4>
        return localPart;
    }

    public String getDomain() { // <4>
        return domain;
    }

    @Override
    public boolean equals(Object o) { // <5>
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddress that = (EmailAddress) o;
        return localPart.equals(that.localPart) &&
            domain.equals(that.domain);
    }

    @Override
    public int hashCode() { // <5>
        return Objects.hash(localPart, domain);
    }

    @Override
    public String toString() { // <6>
        return localPart + "@" + domain;
    }
}