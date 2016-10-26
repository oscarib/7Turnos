package es.edm.services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.edm.dao.ITurnDao;
import es.edm.domain.entity.TurnEntity;
import es.edm.services.ITurnService;

@Service
@Transactional
public class TurnService implements ITurnService {
	
	@Autowired
	ITurnDao turnDao;

	@Override
	public TurnEntity getTurnById(Integer uid) {
		return turnDao.getTurnById(uid);
	}

	@Override
	public void updateTurn(TurnEntity turn) {
		turnDao.updateTurn(turn);
	}

	@Override
	public float getRedundancyPercentage() {
		return turnDao.getRedundancyPercentage();
	}

	@Override
	public float getEmptyTurnsPercentage() {
		return turnDao.getEmptyTurnsPercentage();
	}

	@Override
	public float getFreeTurnsPercentage() {
		return turnDao.getFreeTurnsPercentage();
	}

	@Override
	public float getTurnsUsedPercentage() {
		return turnDao.getTurnsUsedPercentage();
	}

	@Override
	public int getEmptyTurns() {
		return turnDao.getEmptyTurns();
	}

	@Override
	public int getTotalTurns() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getUsedTurns() {
		List<TurnEntity> turnos = turnDao.getTurns();
		//TODO: Terminar esto
		return 0;
	}

}
