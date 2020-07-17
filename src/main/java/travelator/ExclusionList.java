package travelator;

import travelator.handlers.RegistrationData;

public interface ExclusionList {
    boolean exclude(RegistrationData registrationData);
}