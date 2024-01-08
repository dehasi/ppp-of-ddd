package gambling.applicationServices;

import gambling.applicationServices.Domain.IReferralPolicy;
import gambling.controllers.ControllerDomain.Customer;
import gambling.controllers.ControllerDomain.ICustomerDirectory;
import gambling.controllers.ControllerDomain.NewAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

class Recommend_a_friend_Test {

    private TestApplication.ReferAFriendService service;

    private TestApplication.IEmailer emailer;
    private ICustomerDirectory directory;

    // test data
    private int referrerId = 454456;
    private NewAccount friendsDetails = new NewAccount("Deedeedee", "pppddd@wrox.com", 24);

    private final Map<Integer, Customer> inMemoryCustomerDB = new HashMap<>() {{
        put(1, new Customer(1));
        put(referrerId, new Customer(referrerId));
    }};

    private final List<Customer> sendReferralAcknowledgement = new ArrayList<>();
    private final List<Customer> sendPostReferralSignUpWelcome = new ArrayList<>();

    @BeforeEach void when_a_user_signs_up_with_a_referral_from_a_friend() {
        // test as much of the implementation as possible
        directory = new TestApplication.CustomerDirectory(inMemoryCustomerDB);
        var policy = new TestDomain.ReferralPolicy();

        // cannot test emailing implementation - easier to stub
        emailer = new TestApplication.IEmailer() {

            @Override public void SendReferralAcknowledgement(Customer customer) {
                sendReferralAcknowledgement.add(customer);
            }

            @Override public void SendPostReferralSignUpWelcome(Customer customer) {
                sendPostReferralSignUpWelcome.add(customer);
            }
        };

        service = new TestApplication.ReferAFriendService(directory, policy, emailer);

        service.ReferAFriend(referrerId, friendsDetails);
    }


    @Test void the_referrer_has_50_dollars_credited_to_their_account() {}

    @Test void the_friend_has_an_account_created_with_an_initial_50_dollars() {}

    @Test void the_referrers_loyalty_is_upgraded_to_gold_status() {
        var referrer = directory.Find(referrerId);
        assertThat(referrer.loyaltyStatus).isEqualTo(Customer.LoyaltyStatus.Gold);
    }

    @Test void the_refferer_gets_an_email_notifying_of_the_referral() {
        var referrer = directory.Find(referrerId);
        assertThat(sendPostReferralSignUpWelcome).isEmpty();
        assertThat(sendReferralAcknowledgement).hasSize(1);

        assertThat(sendReferralAcknowledgement.get(0)).isEqualTo(referrer);
    }

    @Test void the_friend_gets_an_email_notifying_of_account_creation() {}

    public static class TestApplication {
        static class ReferAFriendService {
            private ICustomerDirectory directory;
            private IReferralPolicy policy;
            private IEmailer emailer;

            public ReferAFriendService(ICustomerDirectory directory, IReferralPolicy policy, IEmailer emailer) {
                this.directory = directory;
                this.policy = policy;
                this.emailer = emailer;
            }

            public void ReferAFriend(int referrerId, NewAccount friend) {
                // ***** Activity 1 - implement this if you want to make the tests pass
                //policy.apply();
                var referrer = directory.Find(referrerId);
                // referAFriend policy is a domain policy
                policy.referralAccepted((sender, r) -> {
                    referrer.loyaltyStatus = Customer.LoyaltyStatus.Gold;
                    emailer.SendReferralAcknowledgement(referrer);
                });
                policy.apply(new Domain.RecommendAFriend(referrerId, friend));
            }
        }

        public interface IEmailer {
            void SendReferralAcknowledgement(Customer customer);

            void SendPostReferralSignUpWelcome(Customer customer);
        }


        static class CustomerDirectory implements ICustomerDirectory {

            private final Map<Integer, Customer> inMemoryCustomerDB;

            public CustomerDirectory(Map<Integer, Customer> inMemoryCustomerDB) {
                this.inMemoryCustomerDB = inMemoryCustomerDB;
            }

            @Override public Customer Create(NewAccount details) {
                return null;
            }

            @Override public Customer add(NewAccount customerId) {
                return null;
            }

            public Customer Find(int customerId) {
                // ***** Activity 2 - implement this if you want to make the tests pass
                // You will need to choose an appropriate in-memory database
                return inMemoryCustomerDB.get(customerId);
            }
        }
    }

    static class TestDomain {
        static class ReferralPolicy implements IReferralPolicy {
            private BiConsumer<Object, Domain.Referral> referralAccepted;
            private BiConsumer<Object, Domain.Referral> referralRejected;

            @Override public void referralAccepted(BiConsumer<Object, Domain.Referral> eventHandler) {
                referralAccepted = eventHandler;
            }

            @Override public void referralRejected(BiConsumer<Object, Domain.Referral> eventHandler) {
                this.referralRejected = eventHandler;
            }

            // ***** Activity 3 - implement this if you want to make the tests pass
            @Override public void apply(Domain.RecommendAFriend command) {
                referralAccepted.accept(this, new Domain.Referral(command.referrerId(), command.friend().age()));
            }
        }
    }
}
