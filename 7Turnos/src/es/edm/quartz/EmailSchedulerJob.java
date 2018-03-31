package es.edm.quartz;

import es.edm.services.Impl.PrayerService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.inject.Inject;

public class EmailSchedulerJob extends QuartzJobBean {

    private final static Logger logger = LoggerFactory.getLogger(EmailSchedulerJob.class);

    @Inject
    PrayerService prayerService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        logger.info("Se ha ejecutado correctamente el EmailScheduler");
    }
}
