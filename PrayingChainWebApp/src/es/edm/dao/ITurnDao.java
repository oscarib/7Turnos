package es.edm.dao;

import java.util.List;

import es.edm.domain.entity.TurnEntity;

public interface ITurnDao {
	
	TurnEntity getTurnById(Integer uid);
	
	void updateTurn(TurnEntity turn);

	public List<TurnEntity> getOrphanTurns();

	public float getTotalRedundancy();

	public float getCommittedRedundancy();

	public float getEmptyTurnsPercentage();

	public float getFreeTurnsPercentage();

	public float getTurnsUsedPercentage();

	public int getEmptyTurns();

	public int getTotalTurns();

	public int getUsedTurns();
}
