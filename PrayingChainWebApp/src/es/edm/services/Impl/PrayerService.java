package es.edm.services.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.edm.dao.IPrayerDao;
import es.edm.domain.entity.PrayerEntity;
import es.edm.domain.entity.TurnEntity;
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
	public List<PrayerEntity> getCommittedPrayers() {
		return prayerDao.getCommittedPrayers();
	}

	@Override
	public List<PrayerEntity> getNonCommittedPrayers() {
		return prayerDao.getNonCommittedPrayers();
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
	public PrayerEntity getPrayerByEmail(PrayerEntity prayer) {
		return prayerDao.getPrayerByEmail(prayer);
	}

	@Override
	public List<PrayerEntity> getForeignPrayers() {
		return prayerDao.getForeignPrayers();
	}

	@Override
	public List<PrayerEntity> getLocalPrayers() {
		return prayerDao.getLocalPrayers();
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
	public List<PrayerEntity> getPrayersByPhone(PrayerEntity prayer) {
		return prayerDao.getPrayersByPhone(prayer);
	}

	@Override
	public List<PrayerEntity> getPublicPrayers() {
		return prayerDao.getPublicPrayers();
	}

	@Override
	public List<PrayerEntity> getHiddenPrayers() {
		return prayerDao.getHiddenPrayers();
	}

	@Override
	public List<PrayerEntity> getOrphanPrayers() {
		return prayerDao.getOrphanPrayers();
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

}
