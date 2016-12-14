package es.edm.services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.edm.dao.ITurnDao;
import es.edm.domain.ListOfTurns;
import es.edm.domain.entity.TurnEntity;
import es.edm.domain.middle.UsedTurns;
import es.edm.exceptions.DDBBException;
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

	private boolean findTurn(String dow, String turn, List<UsedTurns> usedTurns){
		//TODO: Eliminar este algoritmo y en su lugar crear una vista en MySQL
		for (UsedTurns usedTurn : usedTurns){
			String day2Evaluate = usedTurn.getDay().toString().toLowerCase();
			String turn2Evaluate = usedTurn.getTurn().toString().toLowerCase();
			if (day2Evaluate.equals(dow) && turn2Evaluate.equals(turn)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean addTurn(TurnEntity turn) {
		try{
			turnDao.addTurn(turn);
			return true;
		} catch (Exception e){
			return false;
		}
	}

	@Override
	public List<TurnEntity> getAllActiveTurns() {
		return turnDao.getAllActiveTurns();
	}
	
	@Override
	public ListOfTurns[][] loadAllTurns(){
		
		//Create the empty array
		ListOfTurns[][] listOfTurns = new ListOfTurns[7][48];
		
		//Load all turns from ddbb
		try {
			List<TurnEntity> ddbbTurns = getAllActiveTurns();

			//Loop through all turns in the ddbb
			for (TurnEntity nextTurn : ddbbTurns){
				
				if (!nextTurn.getPrayer().isErased()){
					//load Day of Week as an integer
					int day = nextTurn.getDow().ordinal();
					
					//Load the turn
					int turn = getTurnPosition(nextTurn.getTurn());
					if (turn<0) throw new RuntimeException("No se pudo encontrar ese turno (String) en el enumerado TurnsOfDay");
					
					//If the grid List is empty, create a new one
					if (listOfTurns[day][turn] == null) listOfTurns[day][turn] = new ListOfTurns();
	
					//Add the turn to its position
					listOfTurns[day][turn].add(nextTurn);
				}
			}
			
			return listOfTurns;
		} catch (DDBBException e) {
			throw new RuntimeException("There was a problem connecting with the ddbb: " + e.toString());
		}
	}
	
	private int getTurnPosition(String hourText){
		for (TurnsOfDay turn2Match : TurnsOfDay.values()){
			if (hourText==null){
				return -1;
			} else {
				if (hourText.equals(turn2Match.toString())){
					return turn2Match.ordinal();
				}
			}
		}
		return -1;
	}

	@Override
	public List<TurnEntity> getAllTurns() {
		return turnDao.getAllTurns();
	}
}
