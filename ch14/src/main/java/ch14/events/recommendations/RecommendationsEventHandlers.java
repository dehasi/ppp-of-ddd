package ch14.events.recommendations;

import ch14.events.shopping.ProductAddedToBasket;

class RecommendationsEventHandlers {

    private RecommendationService recommendationService;

    void handle(ProductAddedToBasket productAdded) {
        recommendationService.update_suggestions_for(
                productAdded.basketId(), productAdded.itemsInBasket());
    }
}
