package es.edm.services;

import java.util.List;

import es.edm.domain.ListOfTurns;
import es.edm.domain.entity.TurnEntity;

public interface ITurnService {
	
	TurnEntity getTurnById(Integer uid);
	
	void updateTurn(TurnEntity turn);
	
	int getEmptyTurns();
	
	int getTotalTurns();
	
	int getUsedTurns();

	int getDaysCovered();

	Boolean addTurn(TurnEntity turn);

	List<TurnEntity> getAllActiveTurns();
	
	public ListOfTurns[][] loadAllTurns();

}
