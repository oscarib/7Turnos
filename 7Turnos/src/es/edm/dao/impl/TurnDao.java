package es.edm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.edm.dao.ITurnDao;
import es.edm.domain.entity.TurnEntity;
import es.edm.domain.middle.UsedTurns;
import es.edm.services.Impl.OtherServices;
import es.edm.util.TurnStatus;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class TurnDao implements ITurnDao {

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Autowired
	OtherServices otherServices;
	
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
		objCriteria.add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()));

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
		objCriteria.add(Restrictions.ne("erased", true))
		.add(Restrictions.ne("status",TurnStatus.cancelled))
		.add(Restrictions.ne("status",TurnStatus.NotCommitted));

		return objCriteria.list();
	}

	@Override
	public List<TurnEntity> getAllTurns() {
		Session session = entityManager.unwrap(Session.class);

		Criteria objCriteria = session.createCriteria(TurnEntity.class);
		objCriteria.add(Restrictions.ne("erased", true));
		objCriteria.createCriteria("prayer")
			.add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()))
			.add(Restrictions.ne("erased", true));

		return objCriteria.list();
	}
}
