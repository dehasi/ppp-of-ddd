package ch17.onlineGaming.withDomainServices.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class OnlineDeathmatch implements Game {

    private final UUID id;
    private Competitor player1;
    private Competitor player2;

    // Domain Services
    private List<GamingScorePolicy> scorers;
    private List<GamingRewardPolicy> rewarders;

    private List<Competitor> winners = new ArrayList<>();
    private List<Competitor> losers = new ArrayList<>();

    OnlineDeathmatch(UUID id, Competitor player1, Competitor player2,
                     List<GamingScorePolicy> scorers, List<GamingRewardPolicy> rewarders) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;

        this.scorers = scorers;
        this.rewarders = rewarders;
    }

    void commenceBattle() {
        // ...

        // battle completes

        // would actually be based on the game result
        this.winners.add(player1);
        this.losers.add(player2);

        updateScoresAndRewards();
    }

    private void updateScoresAndRewards() {
        for (var scorer : scorers) {
            scorer.apply(this);
        }

        for (var rewarder : rewarders) {
            rewarder.apply(this);
        }
    }

    @Override public List<Competitor> winners() {
        return winners;
    }

    @Override public List<Competitor> losers() {
        return losers;
    }
}
