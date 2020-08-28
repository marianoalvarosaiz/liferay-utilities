import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.Registry;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.registry.ServiceReference;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;

import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

class SchedulerJobsManager {

    SchedulerJobsManager(PrintWriter out) {
        this.out = out;

        schedulerEngineHelper = getSchedulerEngineHelper();

        serviceTracker = schedulerEngineHelper._serviceTracker;

        serviceTrackerCustomizer = serviceTracker.customizer;
    }

    void restartScheduledJobs() {
        serviceTracker.close();

        schedulerEngineHelper._serviceTracker = ServiceTrackerFactory.open(schedulerEngineHelper._bundleContext,
            "(objectClass=" + SchedulerEventMessageListener.class.getName() + ")", serviceTrackerCustomizer);

        printCurrentJobsDebugInformation(schedulerEngineHelper._serviceTracker);
    }

    private void printCurrentJobsDebugInformation(ServiceTracker serviceTracker) {
        out.println("================================================================");
        out.println("==== ServiceTracker has been restarted");
        out.println("==== Current configured listeners: ");

        int i = 1;

        for (def service: serviceTracker.getServices()) {
            if(service instanceof com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListenerWrapper) {
                SchedulerEntry schedulerEntry = service.getSchedulerEntry();

                out.println("EventListenerClass #" + i++ + ": " + schedulerEntry.getEventListenerClass());
            }
        }
        out.println("================================================================");
    }


    private SchedulerEngineHelper getSchedulerEngineHelper() {
        Registry registry = RegistryUtil.getRegistry();

        ServiceReference serviceReference = registry.getServiceReference(SchedulerEngineHelper.class);

        return registry.getService(serviceReference);
    }


    private final SchedulerEngineHelper schedulerEngineHelper;
    private final ServiceTracker serviceTracker;
    private final ServiceTrackerCustomizer serviceTrackerCustomizer;
    private final PrintWriter out;

}

(new SchedulerJobsManager(out)).restartScheduledJobs();
