package homework;


import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;

public class CustomerService {
    private TreeMap<Customer, String> customers = new TreeMap<>(new Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            return Long.compare(o1.getScores(), o2.getScores());
        }

    });

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> smallest = this.customers.firstEntry();
        Customer customer = smallest.getKey();
        return Map.entry(new Customer(customer.getId(), customer.getName(), customer.getScores()), smallest.getValue().toString());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> next = this.customers.higherEntry(customer);
        if (next == null) {
            return null;
        }
        Customer nextCustomer = next.getKey();

        return Map.entry(new Customer(nextCustomer.getId(), nextCustomer.getName(), nextCustomer.getScores()), next.getValue().toString());
    }

    public void add(Customer customer, String data) {
        this.customers.put(new Customer(customer.getId(), customer.getName(), customer.getScores()), data);
    }
}
