package ch17.onlineGaming.withDomainServices.model;

// Domain Service interface - part of ubiquitous language
public interface GamingScorePolicy {
    void apply(Game game);
}
