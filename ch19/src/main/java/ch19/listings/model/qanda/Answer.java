package ch19.listings.model.qanda;

import java.time.LocalDateTime;

record Answer(LocalDateTime dateAnswered, String text) {}
