package es.edm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.edm.dao.ITurnDao;
import es.edm.domain.entity.TurnEntity;
import es.edm.domain.middle.UsedTurns;

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
	public List<UsedTurns> getUsedTurns() {
		Session session = entityManager.unwrap(Session.class);

		Criteria objCriteria = session.createCriteria(UsedTurns.class);

		return objCriteria.list();
	}

	@Override
	public void addTurn(TurnEntity turn) {
		entityManager.merge(turn);
	}

	@Override
	public List<TurnEntity> getAllActiveTurns() {
		Session session = entityManager.unwrap(Session.class);

		Criteria objCriteria = session.createCriteria(TurnEntity.class);

		return objCriteria.list();
	}
}
