package ch17.onlineGaming.withDomainServices.model;

// repository
public interface ScoreFinder {

    Score findTopScore(Game game, int resultNumber);
}
