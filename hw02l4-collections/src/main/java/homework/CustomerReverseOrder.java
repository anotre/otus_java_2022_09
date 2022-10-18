package homework;

import java.util.LinkedList;

public class CustomerReverseOrder {
    private LinkedList<Customer> customerOrder = new LinkedList<>();

    public void add(Customer customer) {
        this.customerOrder.addLast(customer);
    }

    public Customer take() {
        return this.customerOrder.pollLast();
    }
}
