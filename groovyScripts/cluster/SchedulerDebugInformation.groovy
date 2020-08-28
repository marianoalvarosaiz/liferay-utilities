import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiServiceUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


class SchedulerDebugInformation {

    SchedulerDebugInformation() {
        isMaster = ClusterMasterExecutorUtil.isMaster();
    }

    void printDebugInformation(PrintWriter out) {
        out.println("================================================================");
        out.println("=== Master: "+ isMaster);
        out.println("=== " + new Date());

        if (isMaster) {
            printMasterDebugInformation(out);
        }
        else {
            printSlaveDebugInformation(out);
        }
    }

    private void printMasterDebugInformation(PrintWriter out) {      
        List<SchedulerResponse> schedulerResponses = SchedulerEngineHelperUtil.getScheduledJobs();

	out.println("================================================================");
        out.println("=== Total scheduled jobs: " + schedulerResponses.size());

        for (SchedulerResponse schedulerResponse: schedulerResponses) {
            try {

                out.println("================================================================");
        
                Trigger trigger = schedulerResponse.getTrigger();

                String jobName = schedulerResponse.getJobName();
                String groupName = schedulerResponse.getGroupName();
                StorageType storageType = schedulerResponse.getStorageType();

                out.println("Job Name: " + jobName);        
                out.println("Group Name: " + groupName);
                out.println("Storage Type: " + storageType);
                out.println ("TriggerState: " + SchedulerEngineHelperUtil.getJobState(schedulerResponse));
        
                if (trigger == null) {
                    out.println("Trigger is NULL");
                } 
                else {
                    out.println("TriggerStartDate: " + trigger.getStartDate());
                    out.println("TriggerEndDate: " + trigger.getEndDate());
                    out.println("PreviousFireTime: " + SchedulerEngineHelperUtil.getPreviousFireTime(schedulerResponse));
                    out.println("NextFireTime: " + SchedulerEngineHelperUtil.getNextFireTime(schedulerResponse));
                }

            } catch (Exception exception) {
                exception.printStackTrace(out);
            }
        }
    }

    private void printSlaveDebugInformation(PrintWriter out) {
        SchedulerEngine schedulerEngine = getSchedulerEngine();

        Map<String, ObjectValuePair<SchedulerResponse, TriggerState>> memoryClusteredJobs = schedulerEngine._memoryClusteredJobs;

        out.println("================================================================");
        out.println("=== Total scheduled jobs: " + memoryClusteredJobs.size());

        for (ObjectValuePair<SchedulerResponse, TriggerState> memoryClusteredJob : memoryClusteredJobs.values()) {
            SchedulerResponse schedulerResponse = memoryClusteredJob.getKey();

            out.println("================================================================");
        
            Trigger trigger = schedulerResponse.getTrigger();

            String jobName = schedulerResponse.getJobName();
            String groupName = schedulerResponse.getGroupName();
            StorageType storageType = schedulerResponse.getStorageType();

            out.println("Job Name: " + jobName);        
            out.println("Group Name: " + groupName);
            out.println("Storage Type: " + storageType);
        }
    }
        
    
    private SchedulerEngine getSchedulerEngine() {
        SchedulerEngine schedulerEngine = (SchedulerEngine) IdentifiableOSGiServiceUtil.getIdentifiableOSGiService(OSGI_SERVICE_IDENTIFIER);
    }
    
    private final String OSGI_SERVICE_IDENTIFIER = "com.liferay.portal.scheduler.multiple.internal.ClusterSchedulerEngine";
    private final boolean isMaster;

}


(new SchedulerDebugInformation()).printDebugInformation(out);

