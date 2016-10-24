package es.edm.services;

import es.edm.domain.entity.TurnEntity;

public interface ITurnService {
	
	TurnEntity getTurnById(Integer uid);
	
	void updateTurn(TurnEntity turn);
	
}
