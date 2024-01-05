package uicomp.dist.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class RecommendationsController {

    @GetMapping("/recommendations") List<Holiday> recommendations() {
        return List.of(
                new Holiday(
                        "2 Weeks in Mykonos",
                        450,
                        "/book-cover.png"
                ),
                new Holiday(
                        "2 Weeks in Kos",
                        365,
                        "/book-cover.png"
                )
        );
    }

    record Holiday(String title, int price, String imageUrl) {}
}
