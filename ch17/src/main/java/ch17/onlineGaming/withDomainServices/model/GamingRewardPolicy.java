package ch17.onlineGaming.withDomainServices.model;

// Domain Service interface - part of ubiquitous language
public interface GamingRewardPolicy {

    void apply(Game game);
}
