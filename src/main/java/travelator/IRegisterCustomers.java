package travelator;

import travelator.handlers.RegistrationData;

public interface IRegisterCustomers {
    Customer register(RegistrationData data)
        throws ExcludedException, DuplicateException;
}