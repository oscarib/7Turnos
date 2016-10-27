package es.edm.services.Impl;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.edm.dao.ITurnDao;
import es.edm.domain.entity.TurnEntity;
import es.edm.domain.middle.UsedTurns;
import es.edm.services.Configuration;
import es.edm.services.ITurnService;
import es.edm.util.TurnsOfDay;

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
		//TODO: Damián:  Convertir esta mierda en una puta vista
		int noDays = DayOfWeek.values().length;
		int noTurns = TurnsOfDay.values().length;
		int emptyTurns = 0;
		List<UsedTurns> usedTurns = turnDao.getUsedTurns();
		for (int day = 0 ; day < noDays; day++){
			for (int turn = 0; turn<noTurns; turn++){
				String day2Evaluate = DayOfWeek.values()[(day)].toString().toLowerCase();
				String turn2Evaluate = TurnsOfDay.values()[turn].toString().toLowerCase();
				if (!findTurn(day2Evaluate, turn2Evaluate, usedTurns)){
					emptyTurns++;
				}
			}
		}
		return emptyTurns;
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
	
	@Override
	public int getDaysCovered() {
		//Suma agrupada de turnos por día
		List<UsedTurns> usedTurns = turnDao.getUsedTurns();
		int sumOfTurns = 0;
		
		//Si la suma por día es superior al máximo definido en configuración, sólo sumamos hasta esa cantidad
		for (UsedTurns turn : usedTurns){
			sumOfTurns++;
		}
		
		return sumOfTurns;
	}

	private boolean findTurn(String dow, String turn, List<UsedTurns> usedTurns){
		//TODO: Damián: Eliminar esta mierda de código, creando una puta vista en mySQL
		for (UsedTurns usedTurn : usedTurns){
			String day2Evaluate = usedTurn.getDay().toString().toLowerCase();
			String turn2Evaluate = usedTurn.getTurn().toString().toLowerCase();
			if (day2Evaluate.equals(dow) && turn2Evaluate.equals(turn)) {
				return true;
			}
		}
		return false;
	}
}
