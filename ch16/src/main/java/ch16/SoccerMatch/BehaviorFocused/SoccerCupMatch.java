package ch16.SoccerMatch.BehaviorFocused;

import ch16.SoccerMatch.Scores;
import ch16.SoccerMatch.ThereWasNoPenaltyShootout;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

class SoccerCupMatch {

    public UUID id;
    private Scores team1Scores;
    private Scores team2Scores;

    public SoccerCupMatch(UUID id, Scores team1Scores, Scores team2Scores) {
        this.id = requireNonNull(id, "Soccer cup match ID cannot be null");
        this.team1Scores = requireNonNull(team1Scores, "Team 1 scores cannot be null");
        this.team2Scores = requireNonNull(team2Scores, "Team 2 scores cannot be null");
    }

    public Scores winningTeamScores() {
        if (team1Scores.totalScore() > team2Scores.totalScore())
            return team1Scores;

        if (team2Scores.totalScore() > team1Scores.totalScore())
            return team2Scores;

        var awayGoalsWinner = findWinnerUsingAwayGoalsRule();

        if (awayGoalsWinner == null)
            return findWinnerOfPenaltyShootout();
        else
            return awayGoalsWinner;

    }

    private Scores findWinnerUsingAwayGoalsRule() {
        if (team1Scores.awayLegGoals() > team2Scores.awayLegGoals())
            return team1Scores;

        if (team2Scores.awayLegGoals() > team1Scores.awayLegGoals())
            return team2Scores;

        // The scores were exactly the same so no away goals winners
        return null;
    }

    private Scores findWinnerOfPenaltyShootout() {
        if (team1Scores.shootoutScore() > team2Scores.shootoutScore())
            return team1Scores;

        if (team2Scores.shootoutScore() > team1Scores.shootoutScore())
            return team2Scores;

        throw new ThereWasNoPenaltyShootout();
    }
}

