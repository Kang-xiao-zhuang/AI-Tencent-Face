package com.zhuang.ai;

import com.zhuang.ai.util.Base64Util;

public class SearchPerson {

    public static void main(String[] args) throws Exception {
        AppConfig config = new AppConfig();
        config.setSecretId("xxxxx");
        config.setSecretKey("xxxx");
        config.setServerIp("iai.tencentcloudapi.com");
        config.setArea("ap-nanjing");
        config.setGroupId("02");
        config.setPersonIdPre("01");
        FaceApi2 faceApi2 = new FaceApi2();
        String b64 = Base64Util.encodeBase64File("D://icon.jpg");
        RootResp resp = faceApi2.searchPersonsReturnsByGroup(config, b64);
        System.out.println(resp);
        if (resp.getData() == null) {
            System.out.println("查无此人！");
        } else {
            System.out.println("查询到此人");
            System.out.println(resp.getData());
        }
    }
}
