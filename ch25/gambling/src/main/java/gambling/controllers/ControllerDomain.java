package gambling.controllers;

import java.util.concurrent.CompletableFuture;

public class ControllerDomain {

    public interface ICustomerDirectory {
        Customer Find(int customerId);

        Customer Create(NewAccount details);

        Customer add(NewAccount customerId);
    }

    public interface ICustomerDirectoryAsync {
        CompletableFuture<Customer> find(int customerId);

        CompletableFuture<Customer> add(NewAccount customerId);
    }

    public record NewAccount(
            String nickname,
            String email,
            int age
    ) {}

    public static class Customer {
        public int id;
        public String email;
        public String nickname;
        public int age;
        public LoyaltyStatus loyaltyStatus = LoyaltyStatus.Bronze;

        public Customer(int id) {
            this.id = id;
        }

        public enum LoyaltyStatus {
            Bronze,
            Silver,
            Gold
        }
    }


    public interface IReferAFriendPolicy {
        void Apply(Customer referrer, Customer friend);
    }
}
