package ch19.listings.model.listings;

import ch19.listings.model.qanda.Question;
import ch19.listings.model.watchLists.WatchedItem;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class Listing {

    private UUID id;
    private ListingFormat format;
    private String title;
    private String description;
    private String condition;
    private UUID sellerId;

    private double postageCosts;

    private String paymentMethodsAccepted;
    private String dispatchTime;

    public Listing(UUID id, UUID sellerId, ListingFormat format) {
        this.id = id;
        this.format = format;
        this.sellerId = requireNonNull(sellerId);
    }

    // public Email contactSeller(string question) {
    // You'll receive the message in your Messages inbox and your personal email account. 
    // When you respond, you can choose to post the question and answer to your listing 
    // so all buyers can see it. Once you post the answer, you can't change or remove it.
    //}

    public WatchedItem watch(UUID watchedItemId, UUID memberId) {
        return new WatchedItem(watchedItemId, this.id, memberId);
    }

    //public void Add(PaymentMethod paymentMethod) {
    // replace Item and add PaymentMethod       
    //}

    //public void Add(PostLocation()) {}

    public Question askQuestion(UUID MemberId, String quesiton, LocalDateTime timeOfQuestion) {
        return new Question(UUID.randomUUID(), this.id, MemberId, quesiton, timeOfQuestion);
    }

    // public void Amend(Item item) {
    // http://pages.ebay.co.uk/help/sell/revising_restrictions.html
    // if (currentTime.
    // trow new ItemRevisionEvent(Description), DateTime currentTime
    //  }

}
