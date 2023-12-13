package ch19.listings.model.qanda;

import ch19.listings.infrastructure.DomainEvents;

import java.time.LocalDateTime;
import java.util.UUID;

public class Question {

    private UUID id;
    private UUID sellerId;
    private UUID listingId;
    private UUID memberId;
    private boolean publishOnListing;
    private LocalDateTime timeOfQuestion;

    public String desc;
    private Answer answer;

    public Question(UUID id, UUID listingId, UUID memberId, String question, LocalDateTime timeOfQuestion) {
        this.id = id;
        this.listingId = listingId;
        this.memberId = memberId;
        this.desc = question;
        this.timeOfQuestion = timeOfQuestion;

        DomainEvents.raise(new QuestionSubmitted(id, listingId));
    }

    public void submitAnAnswer(String answer, UUID sellerId, boolean publishOnListing, LocalDateTime timeOfAnswer) {
        this.publishOnListing = publishOnListing;
        this.answer = new Answer(timeOfAnswer, answer);

        DomainEvents.raise(new QuestionAnswered(id, listingId));
    }

    public Answer answer() {return answer;}
}
