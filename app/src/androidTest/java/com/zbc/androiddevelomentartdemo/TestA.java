package com.zbc.androiddevelomentartdemo;

import android.test.AndroidTestCase;
import android.util.Log;

import com.alibaba.sdk.android.OSSTestConfig;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.utils.HttpdnsMini;

public class TestA extends AndroidTestCase {

    private OSSClient oss;

    public void initOSSClient() {
        HttpdnsMini.getInstance().isHttp2Test = false;
        ClientConfiguration conf = new ClientConfiguration();
        //ClientConfiguration 链接和socket 已经改为60s了
//        conf.setConnectionTimeout(60 * 1000); // 连接超时，默认15秒
//        conf.setSocketTimeout(60 * 1000); // socket超时，默认15秒
//        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
//        conf.setHttpDnsEnable(false);
        oss = new OSSClient(getContext(), OSSTestConfig.ENDPOINT, OSSTestConfig.credentialProvider,conf);

        Log.e("-------",oss!=null?"oss!=null":"oss==null");
    }


    public void printLog(){
        Log.e("-------",oss!=null?"oss!=null":"oss==null");
    }

}
