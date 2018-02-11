package com.xingoo.teddy.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class YarnService {

    private CloseableHttpClient httpclient = HttpClients.createDefault();
    public static final String URL = "http://hnode2:8088/ws/v1/cluster/apps/";
    public static final String URL_R = "http://hnode1:8088/ws/v1/cluster/apps/";
    /**
     * https://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/ResourceManagerRest.html#Cluster_Application_State_API
     * @return
     */
    public String state(String app_id){
        HttpGet httpGet = new HttpGet(URL+ app_id + "/state");
        httpGet.setHeader("Content-Type", "application/json");
        CloseableHttpResponse resp = null;
        try {
            resp = httpclient.execute(httpGet);
            if (resp.getStatusLine().getStatusCode() == 200) {
                String content = IOUtils.toString(resp.getEntity().getContent());
                return JSON.parseObject(content).getString("state");
            }
        } catch (IOException e) {
            httpGet = new HttpGet(URL_R+ app_id + "/state");
            try {
                resp = httpclient.execute(httpGet);
                if (resp.getStatusLine().getStatusCode() == 200) {
                    String content = IOUtils.toString(resp.getEntity().getContent());
                    return JSON.parseObject(content).getString("state");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
            if(resp!=null){
                try {
                    resp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "NONE";
    }
}
