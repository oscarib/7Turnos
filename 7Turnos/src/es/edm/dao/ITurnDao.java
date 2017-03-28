package es.edm.dao;

import es.edm.domain.entity.TurnEntity;
import es.edm.domain.middle.UsedTurns;

import java.util.List;

public interface ITurnDao {

    TurnEntity getTurnById(Integer uid);

    void updateTurn(TurnEntity turn);

    public List<UsedTurns> getUsedTurns();

    void addTurn(TurnEntity turn);

    List<TurnEntity> getAllActiveTurns();

    List<TurnEntity> getAllTurns();

}
