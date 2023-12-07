package ch17.onlineGaming.withoutDomainServices.model;

import java.util.UUID;

// contains hard-coded logicy for applying scores and rewards - does not use a domain service
class OnlineDeathmatch {

    private final UUID id;
    private Competitor player1;
    private Competitor player2;

    public OnlineDeathmatch(UUID id, Competitor player1, Competitor player2) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
    }

    void commenceBattle() {
        // ...

        // battle completes

        // would actually be based on the game result
        var winner = player1;
        var loser = player2;

        updateScoresAndRewards(winner, loser);
    }

    private void updateScoresAndRewards(Competitor winner, Competitor loser) {
        // real system uses rankings, history, bonus points, in-game actions
        // seasonal promotions, marketing campaigns
        winner.score = winner.score.add(new Score(200));
        loser.score = loser.score.subtract(new Score(200));
    }
}
