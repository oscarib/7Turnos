package es.edm.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.edm.dao.IOthersDao;
import es.edm.domain.entity.ConfigurationEntity;
import es.edm.domain.entity.StatisticsEntity;
import es.edm.domain.entity.UserEntity;
import es.edm.domain.middle.LoginStatus;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class OthersDao implements IOthersDao {
	
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public ConfigurationEntity getConfiguration(LoginStatus loggedUser) {
		Session session = entityManager.unwrap(Session.class);

		Criteria objCriteria = session.createCriteria(ConfigurationEntity.class);

		objCriteria.add(Restrictions.eq("chain", loggedUser.getChain()));

		return (ConfigurationEntity) objCriteria.uniqueResult();
	}

	@Override
	public int getUserChain(String userName) {
		Session session = entityManager.unwrap(Session.class);

		Criteria objCriteria = session.createCriteria(UserEntity.class);

		objCriteria.add(Restrictions.eq("username", userName));
		
		UserEntity user = (UserEntity)objCriteria.uniqueResult(); 

		return user.getChain();
	}

	@Override
	public StatisticsEntity getStatistics(LoginStatus loggedUser) {
		Session session = entityManager.unwrap(Session.class);

		Criteria objCriteria = session.createCriteria(StatisticsEntity.class);

		objCriteria.add(Restrictions.eq("chain", loggedUser.getChain()));

		return (StatisticsEntity) objCriteria.uniqueResult();
	}
}