package rentalservice.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private final String name;

    private final Account account = new Account();

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }
}
