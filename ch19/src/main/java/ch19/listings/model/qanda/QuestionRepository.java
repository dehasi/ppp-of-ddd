package ch19.listings.model.qanda;

import java.util.UUID;

public interface QuestionRepository {

    Question findBy(UUID id);

    void add(Question question);
}
