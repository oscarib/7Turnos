package es.edm.services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.edm.dao.ITurnDao;
import es.edm.domain.entity.TurnEntity;
import es.edm.domain.middle.UsedTurns;
import es.edm.services.Configuration;
import es.edm.services.ITurnService;

@Service
@Transactional
public class TurnService implements ITurnService {
	
	@Autowired
	ITurnDao turnDao;
	
	@Autowired
	Configuration conf;

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
		return 48 * 7 * conf.getPrayersPerTurn();
	}

	@Override
	public int getUsedTurns() {
		//Suma agrupada de turnos por día
		List<UsedTurns> usedTurns = turnDao.getUsedTurns();
		int sumOfTurns = 0;
		
		//Si la suma por día es superior al máximo definido en configuración, sólo sumamos hasta esa cantidad
		for (UsedTurns turn : usedTurns){

			if (turn.getCountOfTurns()>conf.getPrayersPerTurn()){
				sumOfTurns += conf.getPrayersPerTurn();
			} else {
				sumOfTurns += turn.getCountOfTurns();
			}
		}
		
		return sumOfTurns;
	}
}
