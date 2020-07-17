package travelator.handlers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class RegistrationData {
    public final String name;
    public final String email;

    public RegistrationData(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email
    ) {
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationData that = (RegistrationData) o;
        return name.equals(that.name) &&
            email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}