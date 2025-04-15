package Assignment2;

public class Main {
    public static void main(String[] args) {
        float payment = 2.00f;
        Customer myCustomer = new Customer("Ram", "lakhan", 20f);
        if (myCustomer.pay(payment)) {
            System.out.println("Payment received.");
        } else {
            System.out.println("Come back later and get my money.");
        }
    }
}
