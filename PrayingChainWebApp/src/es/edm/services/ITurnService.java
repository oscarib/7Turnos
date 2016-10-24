package es.edm.services;

import java.util.List;

import es.edm.domain.entity.TurnEntity;

public interface ITurnService {
	
	TurnEntity getTurnById(Integer uid);
	
	void updateTurn(TurnEntity turn);
	
}
