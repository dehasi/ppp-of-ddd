package ch16.SoccerMatch.DataFocused;

import ch16.SoccerMatch.Scores;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

class SoccerCupMatch {

    private final UUID id;
    private Scores team1Scores;
    private Scores team2Scores;

    SoccerCupMatch(UUID id, Scores team1Scores, Scores team2Scores) {
        this.id = requireNonNull(id, "Soccer cup match ID cannot be null");
        this.team1Scores = requireNonNull(team1Scores, "Team 1 scores cannot be null");
        this.team2Scores = requireNonNull(team2Scores, "Team 2 scores cannot be null");
    }

    // Getters, Setters...
}
