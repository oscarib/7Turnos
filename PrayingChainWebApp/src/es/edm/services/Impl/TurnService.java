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
	public List<TurnEntity> getOrphanTurns() {
		return turnDao.getOrphanTurns(); 
	}

	@Override
	public float getTotalRedundancy() {
		return turnDao.getTotalRedundancy();
	}

	@Override
	public float getCommittedRedundancy() {
		return turnDao.getCommittedRedundancy();
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
		return turnDao.getTotalTurns();
	}

	@Override
	public int getUsedTurns() {
		return turnDao.getUsedTurns();
	}

}
