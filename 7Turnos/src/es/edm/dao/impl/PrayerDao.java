package es.edm.dao.impl;

import es.edm.dao.IPrayerDao;
import es.edm.domain.entity.OrphanPrayerEntity;
import es.edm.domain.entity.PrayerEntity;
import es.edm.domain.entity.TurnEntity;
import es.edm.services.IOtherServices;
import es.edm.util.TurnStatus;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class PrayerDao implements IPrayerDao {

    private final static Logger logger = LoggerFactory.getLogger(PrayerDao.class);

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    IOtherServices otherServices;

    @Override
    public List<PrayerEntity> getPrayers() {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(PrayerEntity.class);
        objCriteria.add(Restrictions.ne("erased", true));
        objCriteria.add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()));
        objCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        return objCriteria.list();
    }

    @Override
    public List<PrayerEntity> getCommittedPrayers() {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(PrayerEntity.class);
        // "SELECT distinct edm_prayers.* FROM `edm_prayers` join edm_turns on
        // edm_prayers.uid=edm_turns.prayer_id
        // where edm_turns.status!='cancelled' and
        // edm_turns.status!='NotCommitted';"
        objCriteria.add(Restrictions.ne("erased", true));
        objCriteria.add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()));
        objCriteria.createCriteria("turns")
                .add(Restrictions.ne("status", TurnStatus.cancelled))
                .add(Restrictions.ne("status", TurnStatus.NotCommitted))
                .add(Restrictions.ne("erased", true));

        return objCriteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PrayerEntity> getNonCommittedPrayers() {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(PrayerEntity.class);
        // "SELECT distinct edm_prayers.* FROM `edm_prayers` join edm_turns on
        // edm_prayers.uid=edm_turns.prayer_id
        // where edm_turns.status!='cancelled' and
        // edm_turns.status!='NotCommitted';"
        objCriteria.add(Restrictions.ne("erased", true));
        objCriteria.add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()));
        objCriteria.createCriteria("turns")
                .add(Restrictions.ne("status", TurnStatus.cancelled))
                .add(Restrictions.eq("status", TurnStatus.NotCommitted))
                .add(Restrictions.ne("erased", true));

        List<PrayerEntity> list = objCriteria.list();
        return list;
    }

    @Override
    public void addPrayer(PrayerEntity prayer) {

        entityManager.persist(prayer);

    }

    @Override
    public void removePrayer(PrayerEntity prayer) {

        entityManager.remove(prayer);

    }

    @Override
    public List<PrayerEntity> getPrayersByEmail(PrayerEntity prayer, boolean includeErased) {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(PrayerEntity.class);

        if (!includeErased) {
            objCriteria.add(Restrictions.ne("erased", true));
        }

        objCriteria.add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()));
        objCriteria.add(Restrictions.eq("email", prayer.getEmail()));

        return (List<PrayerEntity>) objCriteria.list();
    }

    @Override
    public List<PrayerEntity> getForeignPrayers() {

        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(PrayerEntity.class);
        objCriteria.add(Restrictions.ne("erased", true));
        objCriteria.add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()));
        objCriteria.add(Restrictions.eq("ownCountry", Boolean.FALSE));

        return objCriteria.list();
    }

    @Override
    public List<PrayerEntity> getLocalPrayers() {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(PrayerEntity.class);
        objCriteria.add(Restrictions.ne("erased", true));
        objCriteria.add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()));
        objCriteria.add(Restrictions.eq("ownCountry", Boolean.TRUE));

        return objCriteria.list();
    }

    @Override
    public List<PrayerEntity> getPrayersOnTurn(TurnEntity turn) {
        Session session = entityManager.unwrap(Session.class);

//		"select edm_prayers.* FROM edm_prayers JOIN edm_turns on edm_prayers.uid=edm_turns.prayer_id "
//		+ "where edm_turns.day=? and edm_turns.hour=? and edm_turns.status!='cancelled' and edm_turns.status!='NotCommitted'"
        Criteria objCriteria = session.createCriteria(PrayerEntity.class);
        objCriteria.add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()))
                .add(Restrictions.ne("erased", true));

        objCriteria.createCriteria("turns")
                .add(Restrictions.ne("erased", true))
                .add(Restrictions.ne("status", TurnStatus.cancelled))
                .add(Restrictions.ne("status", TurnStatus.NotCommitted))
                .add(Restrictions.eq("dow", turn.getDow()))
                .add(Restrictions.eq("turn", turn.getTurn())).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return objCriteria.list();
    }

    @Override
    public List<PrayerEntity> find(PrayerEntity prayer) {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(PrayerEntity.class);

        Field[] fields = PrayerEntity.class.getFields();

        for (Field field : fields) {
            try {

                Object value = field.get(prayer);
                //If value is not null then adds a criteria for this value
                if (value != null) {
                    //Si el campo es un date se filtra con equal
                    if (field.getType().isAssignableFrom(Date.class)) {
                        objCriteria
                                .add(Restrictions.ne("erased", true))
                                .add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()))
                                .add(Restrictions.eq(field.getName(), value));

                    }//Si el campo es un character se filtra con equal
                    else if (field.getType().isAssignableFrom(Character.class)) {
                        objCriteria
                                .add(Restrictions.ne("erased", true))
                                .add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()))
                                .add(Restrictions.eq(field.getName(), value));
                    } else //Para el resto de campos se filtra con like %value%
                    {
                        objCriteria
                                .add(Restrictions.ne("erased", true))
                                .add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()))
                                .add(new LikeExpressionPrayers(field.getName(), value.toString(), MatchMode.ANYWHERE));
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
                // No existe el campo en la entidad, se ignora
                logger.error("No existe la columna '" + field.getName() + "' en la entidad '" + PrayerEntity.class.getSimpleName());
            }
        }

        return objCriteria.list();
    }

    @Override
    public PrayerEntity getPrayerByID(Integer uid) {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(PrayerEntity.class);

        objCriteria
                .add(Restrictions.ne("erased", true))
                .add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()))
                .add(Restrictions.eq("uid", uid));
        PrayerEntity prayer = (PrayerEntity) objCriteria.uniqueResult();
        return prayer;
    }

    @Override
    public List<PrayerEntity> getOrphanPrayers() {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(OrphanPrayerEntity.class);
        objCriteria.add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()));
        objCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        return objCriteria.list();
    }

    @Override
    public List<PrayerEntity> getCancelledPrayers() {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(PrayerEntity.class);

        objCriteria
                .add(Restrictions.ne("erased", true))
                .add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()))
                .add(Restrictions.ne("turns.status", TurnStatus.cancelled));

        return objCriteria.list();
    }

    @Override
    public List<PrayerEntity> getPrayersByPhone(PrayerEntity prayer, boolean includeErased) {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(PrayerEntity.class);
        if (!includeErased) {
            objCriteria.add(Restrictions.ne("erased", true));
        }
        objCriteria
                .add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()))
                .add(Restrictions.eq("phone", prayer.getPhone()));

        return (List<PrayerEntity>) objCriteria.list();
    }

    @Override
    public List<PrayerEntity> getPublicPrayers() {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(PrayerEntity.class);

        objCriteria
                .add(Restrictions.ne("erased", true))
                .add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()))
                .add(Restrictions.eq("hidden", false));

        return objCriteria.list();
    }

    @Override
    public List<PrayerEntity> getHiddenPrayers() {
        Session session = entityManager.unwrap(Session.class);

        Criteria objCriteria = session.createCriteria(PrayerEntity.class);

        objCriteria
                .add(Restrictions.ne("erased", true))
                .add(Restrictions.eq("chain", otherServices.getLoggedUser().getChain()))
                .add(Restrictions.eq("hidden", true));

        return objCriteria.list();
    }

    @Override
    public void updatePrayer(PrayerEntity prayer) {
        //TODO: Esto hay que hacerlo de otra manera, porque merge inserta el registro si no existía antes.
        //		Investigar "bloqueos optimistas"
        entityManager.merge(prayer);

    }

    @Override
    public void updateTurn(TurnEntity turn) {
        entityManager.merge(turn);
    }
}
