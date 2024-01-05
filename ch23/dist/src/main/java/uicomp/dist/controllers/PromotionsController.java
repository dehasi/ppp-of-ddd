package uicomp.dist.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class PromotionsController {

    @GetMapping("/promotions") List<Holiday> promotions() {
        return List.of(
                new Holiday(
                        "Relaxing Med Cruise",
                        999,
                        "/book-cover.png"
                ),
                new Holiday(
                        "Romantic Weekend Break in Paris",
                        120,
                        "/book-cover.png"
                )
        );
    }

    record Holiday(String title, int price, String imageUrl) {}
}
