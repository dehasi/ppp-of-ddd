package ch17.onlineGaming.withDomainServices.model;

import java.util.List;

public interface Game {

    List<Competitor> winners();

    List<Competitor> losers();
}
