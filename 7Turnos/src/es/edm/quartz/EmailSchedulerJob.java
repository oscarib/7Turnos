package es.edm.quartz;

import es.edm.dao.impl.OthersDao;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

public class EmailSchedulerJob extends QuartzJobBean {

    private final static Logger logger = LoggerFactory.getLogger(EmailSchedulerJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("El EmailScheduler se ha ejecutado a las " + new Date());
    }
}
