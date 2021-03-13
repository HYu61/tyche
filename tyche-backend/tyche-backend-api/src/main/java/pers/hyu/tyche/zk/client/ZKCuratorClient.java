package pers.hyu.tyche.zk.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pers.hyu.tyche.enums.BGMOperatorTypeEnum;


import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

@Component()
public class ZKCuratorClient {
    private CuratorFramework client = null;

    @Value("${zookeeper.retry-policy.waiting}")
    private Integer wait;
    @Value("${zookeeper.retry-policy.connect}")
    private Integer connect;
    @Value("${zookeeper.server}")
    private String zkServer;
    @Value("${zookeeper.client.session-timeout}")
    private Integer sessionTimeout;
    @Value("${zookeeper.client.connection-timeout}")
    private Integer connectTimeout;
    @Value("${zookeeper.name-space}")
    private String nameSpace;
    @Value("${zookeeper.node-path}")
    private String nodePath;
    @Value("${bgm-server}")
    private String bgmServer;
    @Value("${file-space}")
    private String fileSpace;


    Logger log = LoggerFactory.getLogger(ZKCuratorClient.class);

    public void init() {
        if (this.client != null) {
            return;
        }

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(wait, connect);

        client = CuratorFrameworkFactory.builder().connectString(zkServer).sessionTimeoutMs(sessionTimeout)
                .connectionTimeoutMs(connectTimeout).retryPolicy(retryPolicy).namespace(nameSpace).build();
        client.start();

        try {
            addBGMWatch(nodePath);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void addBGMWatch(String nodePath) throws Exception {
        final PathChildrenCache CACHE = new PathChildrenCache(client, nodePath, true);

        CACHE.start();
        CACHE.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                if (pathChildrenCacheEvent.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {

                    // /admin/bgm/sdfdsfdsfs
                    String path = pathChildrenCacheEvent.getData().getPath();
                    String zkOperationStr = new String(pathChildrenCacheEvent.getData().getData());

                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, String> zkOperationMap = mapper.readValue(zkOperationStr, Map.class);
//{"path":"\\bgms\\20210310072385d0a6c94102b567709bca06f29a.mp3","code":"1"}
                    String bgmRelatedPath = zkOperationMap.get("path");
                    String bgmOperationCode = zkOperationMap.get("code");

                    // set stored path
                    // c:\tyche\bgms\far.mp3
                    String storedFilePath = fileSpace + bgmRelatedPath;
                    String arrPath[] = bgmRelatedPath.split("\\\\");
                    String tempUrlPaht = "";
                    for (int i = 0; i < arrPath.length; i++) {
                        if (StringUtils.isNotBlank(arrPath[i])) {
                            tempUrlPaht += "/";
                            tempUrlPaht += URLEncoder.encode(arrPath[i], "UTF-8");
                        }
                    }

                    String bgmUrl = bgmServer + tempUrlPaht;

                    // code 1 add
                    if (bgmOperationCode.equals(BGMOperatorTypeEnum.ADD.getCode())) {
                        //download to springboot server
                        URL url = new URL(bgmUrl);
                        File file = new File(storedFilePath);
                        FileUtils.copyURLToFile(url, file);

                        client.delete().forPath(path);
                    }

                    // code -1 delete
                    else if (bgmOperationCode.equals((BGMOperatorTypeEnum.DELETE.getCode()))) {
                        FileUtils.forceDelete(new File(storedFilePath));

                        client.delete().forPath(path);
                    }


                }
            }
        });


    }


}
