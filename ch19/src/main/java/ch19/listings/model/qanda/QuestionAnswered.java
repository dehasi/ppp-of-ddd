package ch19.listings.model.qanda;

import java.util.UUID;

public record QuestionAnswered(UUID questionId, UUID listingId) {}
