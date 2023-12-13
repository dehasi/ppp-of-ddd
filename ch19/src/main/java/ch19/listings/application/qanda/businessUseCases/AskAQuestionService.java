package ch19.listings.application.qanda.businessUseCases;

import ch19.listings.infrastructure.Clock;
import ch19.listings.infrastructure.DomainEvents;
import ch19.listings.model.listings.ListingRepository;
import ch19.listings.model.qanda.QuestionRepository;
import ch19.listings.model.qanda.QuestionSubmitted;

import java.util.UUID;
import java.util.function.Consumer;

class AskAQuestionService {

    private ListingRepository listings;
    private QuestionRepository questions;
    //private DocumentSession unitOfWork;
    private Clock clock;

    public AskAQuestionService(ListingRepository listings, QuestionRepository questions, Clock clock) {
        this.listings = listings;
        this.questions = questions;
        this.clock = clock;
    }


    void ask(UUID listingId, UUID memberId, String question) {
        var listing = listings.findBy(listingId);

        DomainEvents.register(questionSubmitted());
        {
            var aQuestion = listing.askQuestion(memberId, question, clock.time());

            questions.add(aQuestion);
        }
    }

    private Consumer<QuestionSubmitted> questionSubmitted() {
        return (QuestionSubmitted e) -> {
            // Email seller about the question being asked
        };
    }
}
