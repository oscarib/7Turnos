package es.edm.dao.impl;

import es.edm.dao.IOthersDao;
import es.edm.domain.entity.ChainEntity;
import es.edm.domain.entity.ConfigurationEntity;
import es.edm.domain.entity.StatisticsEntity;
import es.edm.domain.entity.UserEntity;
import es.edm.domain.middle.LoginStatus;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class OthersDao implements IOthersDao {

    private final static Logger logger = LoggerFactory.getLogger(OthersDao.class);
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

        UserEntity user = (UserEntity) objCriteria.uniqueResult();

        return user.getChain();
    }

    @Override
    public StatisticsEntity getStatistics(LoginStatus loggedUser) {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(StatisticsEntity.class);

        objCriteria.add(Restrictions.eq("chain", loggedUser.getChain()));

        return (StatisticsEntity) objCriteria.uniqueResult();
    }

    @Override
    public String getChainName(int chainNumber) {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(ChainEntity.class);

        objCriteria.add(Restrictions.eq("chain", (long) chainNumber));

        return ((ChainEntity) objCriteria.uniqueResult()).getName();
    }

    @Override
    public boolean setConfiguration(ConfigurationEntity conf) {
        try {
            entityManager.merge(conf);
            return true;
        } catch (ConstraintViolationException e) {
            logger.error("Alerta: Se ha tratado de actualizar una entrada en la tabla de configuración que no existe." + e.toString());
            return false;
        }
    }

    @Override
    public boolean saveChainName(long chainNumber, String chainName) {
        ChainEntity chain = new ChainEntity();
        chain.setChain(chainNumber);
        chain.setName(chainName);
        try {
            entityManager.merge(chain);
            return true;
        } catch (ConstraintViolationException e) {
            logger.error("Alerta: Se ha tratado de actualizar una entrada en la tabla de cadenas que no existe." + e.toString());
            return false;
        }
    }
}