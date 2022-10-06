import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;

import java.util.List;

class ClusterNodesDebugInformation {

    ClusterNodesDebugInformation() {
        isMaster = ClusterMasterExecutorUtil.isMaster();
    }

    void printDebugInformation(PrintWriter out) {
        out.println("================================================================");
        out.println("=== Master: "+ isMaster);
        out.println("=== " + new Date());

        out.println("================================================================");
        try{
            out.println("=== Current Cluster Node: ");
            printClusterNodeInformation(ClusterExecutorUtil.getLocalClusterNode(), out);

            List<ClusterNode> clusterNodes = ClusterExecutorUtil.getClusterNodes();

            out.println("================================================================");
            out.println("=== Total cluster nodes: " + clusterNodes.size());

            int i = 0;

            for(ClusterNode clusterNode: clusterNodes) {
                out.println("================================================================");
                out.println("=== Cluster Node # " + i);
                
                printClusterNodeInformation(clusterNode, out);

                i++;
            }
        } catch (Exception exception) {
            out.println("Failed to get nodes information.");
            out.println("Exception:" + exception);
            // exception.printStackTrace(out);
        }
        
    }

    private void printClusterNodeInformation(ClusterNode clusterNode, PrintWriter out) {
        out.println("BindInetAddress: " + clusterNode.getBindInetAddress());
        out.println("ClusterNodeId: " + clusterNode.getClusterNodeId());
        out.println("PortalInetSocketAddress: " + clusterNode.getPortalInetSocketAddress());
        out.println("PortalProtocol: " + clusterNode.getPortalProtocol());
    }

    private final boolean isMaster;
}

(new ClusterNodesDebugInformation()).printDebugInformation(out);

