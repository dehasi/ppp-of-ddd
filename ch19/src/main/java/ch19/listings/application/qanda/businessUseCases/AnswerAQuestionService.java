package ch19.listings.application.qanda.businessUseCases;

import ch19.listings.infrastructure.Clock;
import ch19.listings.infrastructure.DomainEvents;
import ch19.listings.model.listingFormat.auctions.AuctionRepository;
import ch19.listings.model.qanda.QuestionAnswered;
import ch19.listings.model.qanda.QuestionRepository;

import java.util.UUID;
import java.util.function.Consumer;

class AnswerAQuestionService {

    private AuctionRepository auctions;
    private QuestionRepository questions;
    //private DocumentSession unitOfWork;
    private Clock clock;

    public AnswerAQuestionService(AuctionRepository auctions, QuestionRepository questions, Clock clock) {
        this.auctions = auctions;
        this.questions = questions;
        this.clock = clock;
    }

    void Answer(UUID questionId, UUID sellerId, String answer, boolean publishOnListing) {
        var question = questions.findBy(questionId);

        DomainEvents.register(questionAnswered());
        {
            question.submitAnAnswer(answer, sellerId, publishOnListing, clock.time());
        }
    }


    private Consumer<QuestionAnswered> questionAnswered() {
        return (QuestionAnswered e) -> {
            // Email member about the question being answered
        };
    }

}
