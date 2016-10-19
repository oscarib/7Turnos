package es.edm.dao;

import java.util.List;

import es.edm.domain.entity.TurnEntity;

public interface ITurnDao {
	
	TurnEntity getTurnById(Integer uid);
	
	void updateTurn(TurnEntity turn);
	
}
