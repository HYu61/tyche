package pers.hyu.tyche.admin.service.zkcurator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * ZooKeeper client
 *
 * @author Heng Yu
 * @version 1.0
 */
public class ZKCurator {

    // zk client
    private CuratorFramework client = null;

//     logg    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    final static Logger log = LoggerFactory.getLogger(pers.hyu.tyche.admin.service.zkcurator.ZKCurator.class);

    public ZKCurator(CuratorFramework client) {
        this.client = client;
    }

    /**
     * Init the client, create the znode for the bgm
     */
    public void init() {
        client = client.usingNamespace("admin");

        // check the node of /admin/bgm exist or not
        try {
            if (client.checkExists().forPath("/bgm") == null) {

                // There are 2 types of the nodes,
//                 persistent: if it created, it will not auto delete. must delete manually
//                temp: after the node created, when the session is done, the node will be deleted automatllu
                client.create().creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)     // type of the node, persistent
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE) // acl: 匿名权限
                        .forPath("/bgm");

                log.info("zookeeper init suceesee!!!");

                log.info("zookeeper server status: {}", client.isStarted());
            }
        } catch (Exception e) {
            log.error("zookeeper client connect and init error!!!");
            e.printStackTrace();
        }
    }


    /**
     * Send the operation with the bgm.
     *
     * @param bgmId The bgm need to be operated.
     * @param operationMap The map of the operation.
     */
    public void sendBgmOperation(String bgmId, Map<String, String> operationMap){
        try {
            ObjectMapper mapper = new ObjectMapper();
            client.create().creatingParentContainersIfNeeded()
                    .withMode(CreateMode.PERSISTENT)     // type of the node, persistent
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE) // acl: 匿名权限
                    .forPath("/bgm/" + bgmId, mapper.writeValueAsString(operationMap).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
