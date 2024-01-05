package uicomp.dist.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class HolidaysController {

    @GetMapping("/holidays") List<Holiday> holidays() {
        return List.of(
                new Holiday(
                        "2 Weeks in Rhodes",
                        688,
                        "/book-cover.png"
                ),
                new Holiday(
                        "1 Week in Barbados",
                        320,
                        "/book-cover.png"
                )
        );
    }

    record Holiday(String title, int price, String imageUrl) {}
}
