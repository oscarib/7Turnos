package es.edm.dao;

import java.util.List;

import es.edm.domain.entity.TurnEntity;
import es.edm.domain.middle.UsedTurns;

public interface ITurnDao {
	
	TurnEntity getTurnById(Integer uid);
	
	void updateTurn(TurnEntity turn);

	public List<UsedTurns> getUsedTurns();

	void addTurn(TurnEntity turn);

	List<TurnEntity> getAllActiveTurns();
	
	List<TurnEntity> getAllTurns();

}
