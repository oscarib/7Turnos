package es.edm.services;

import es.edm.domain.entity.TurnEntity;
import java.util.List;

public interface ITurnService {
	
	TurnEntity getTurnById(Integer uid);
	
	void updateTurn(TurnEntity turn);
	
	List<TurnEntity> getOrphanTurns();
	
	float getTotalRedundancy();
	
	float getCommittedRedundancy();
	
	float getEmptyTurnsPercentage();
	
	float getFreeTurnsPercentage();
	
	float getTurnsUsedPercentage();
	
	int getEmptyTurns();
	
	int getTotalTurns();
	
	int getUsedTurns();
}
