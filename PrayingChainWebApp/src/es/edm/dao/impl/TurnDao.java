package es.edm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.edm.dao.ITurnDao;
import es.edm.domain.entity.TurnEntity;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class TurnDao implements ITurnDao {

	@PersistenceContext
	protected EntityManager entityManager;
	
	private final static Logger logger = LoggerFactory.getLogger(TurnDao.class);


	@Override
	public TurnEntity getTurnById(Integer uid) {
		Session session = entityManager.unwrap(Session.class);

		Criteria objCriteria = session.createCriteria(TurnEntity.class);

		objCriteria.add(Restrictions.eq("uid", uid));

		return (TurnEntity) objCriteria.uniqueResult();
	}

	@Override
	public void updateTurn(TurnEntity turn) {
		entityManager.persist(turn);
	}

	@Override
	public List<TurnEntity> getOrphanTurns() {
		Session session = entityManager.unwrap(Session.class);
		
		Query query = session.createQuery("select turn from TurnEntity as turn left join turn.prayer as prayer where prayer.turns is empty");

		return query.list();

	}

	@Override
	public float getTotalRedundancy() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCommittedRedundancy() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getEmptyTurnsPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFreeTurnsPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getTurnsUsedPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEmptyTurns() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalTurns() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getUsedTurns() {
		// TODO Auto-generated method stub
		return 0;
	}


}
