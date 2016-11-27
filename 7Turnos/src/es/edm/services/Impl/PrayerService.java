package es.edm.services.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.edm.dao.IPrayerDao;
import es.edm.domain.entity.PrayerEntity;
import es.edm.domain.entity.TurnEntity;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.services.IPrayerService;

@Service
@Transactional
public class PrayerService implements IPrayerService {
	
	@Autowired
	IPrayerDao prayerDao;

	@Override
	public List<PrayerEntity> getPrayers() {
		return prayerDao.getPrayers();
	}
	@Override
	public void addPrayer(PrayerEntity prayer) {
		prayerDao.addPrayer(prayer);
	}

	@Override
	public void removePrayer(PrayerEntity prayer) {
		prayerDao.removePrayer(prayer);
	}

	@Override
	public List<PrayerEntity> getPrayersByEmail(PrayerEntity prayer, boolean includeErased) {
		return prayerDao.getPrayersByEmail(prayer, includeErased);
	}
	@Override
	public List<PrayerEntity> getPrayersOnTurn(TurnEntity turn) {
		return prayerDao.getPrayersOnTurn(turn);
	}

	@Override
	public List<PrayerEntity> find(PrayerEntity prayer) {
		return prayerDao.find(prayer);
	}

	@Override
	public List<PrayerEntity> getPrayersByPhone(PrayerEntity prayer, boolean includeErased) {
		return prayerDao.getPrayersByPhone(prayer, includeErased);
	}

	@Override
	public PrayerEntity getPrayer(int prayerID) {
		PrayerEntity prayer = (PrayerEntity)prayerDao.getPrayerByID(prayerID);
		List<TurnEntity> turns = prayer.getTurns();
		List<TurnEntity> filteredTurns = new ArrayList<TurnEntity>();
		for (TurnEntity turn : turns){
			if (!turn.isErased()) filteredTurns.add(turn);
		}
		prayer.setTurns(filteredTurns);
		return prayer;
	}

	@Override
	public boolean updatePrayer(PrayerEntity prayer) {
		try{
			prayerDao.updatePrayer(prayer);
			return true;
		} catch (Exception e){
			return false;
		}
	}

	@Override
	public Boolean updateTurn(TurnEntity turn) {
		try{
			prayerDao.updateTurn(turn);
			return true;
		} catch (Exception e){
			return false;
		}
	}

	@Override
	public List<TurnEntity> getPrayerTurns(int prayerId) throws PrayerNotFoundException {
		PrayerEntity prayer = getPrayer(prayerId);
		List<TurnEntity> turns = prayer.getTurns();
		return turns;
	}
}
