package travelator;

import dev.forkhandles.result4k.Failure;
import dev.forkhandles.result4k.Result;
import dev.forkhandles.result4k.Success;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryCustomers implements Customers {

    private final List<Customer> list = new ArrayList<>();
    private int id = 0;

    @SuppressWarnings("unchecked")
    @Override
    public Result<Customer, DuplicateException> add(
        String name, String email
    ) {
        if (list.stream().anyMatch( item -> item.getEmail().equals(email)))
            return new Failure<>(
                new DuplicateException(
                    "customer with email " + email + " already exists"
                )
            );
        int newId = id++;
        Customer result = new Customer(Integer.toString(newId), name, email);
        list.add(result);
        return new Success<Customer>(result);
    }

    @Override
    public Optional<Customer> find(String id) {
        return list.stream()
            .filter(customer -> customer.getId().equals(id))
            .findFirst();
    }

    // for test
    public void add(Customer customer) {
        list.add(customer);
    }

    public int size() {
        return list.size();
    }
}