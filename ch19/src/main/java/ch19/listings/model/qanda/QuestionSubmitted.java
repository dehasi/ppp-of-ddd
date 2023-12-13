package ch19.listings.model.qanda;

import java.util.UUID;

public record QuestionSubmitted(UUID questionId, UUID listingId) {}
