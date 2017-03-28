package es.edm.services;

import es.edm.domain.ListOfTurns;
import es.edm.domain.entity.TurnEntity;

import java.util.List;

public interface ITurnService {

    TurnEntity getTurnById(Integer uid);

    void updateTurn(TurnEntity turn);

    Boolean addTurn(TurnEntity turn);

    List<TurnEntity> getAllActiveTurns();

    List<TurnEntity> getAllTurns();

    public ListOfTurns[][] loadAllTurns();

}
