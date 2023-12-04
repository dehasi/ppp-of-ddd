package ch16.SoccerMatch;

import java.util.UUID;

public record Scores(UUID teamId, int homeLegGoals, int awayLegGoals, int shootoutScore) {

    public int totalScore() {
        return homeLegGoals + awayLegGoals;
    }
}
