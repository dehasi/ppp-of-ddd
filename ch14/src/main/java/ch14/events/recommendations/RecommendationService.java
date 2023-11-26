package ch14.events.recommendations;

import java.util.List;
import java.util.UUID;

interface RecommendationService {
    void update_suggestions_for(UUID basketId, List<UUID> itemsInBasket);
}
