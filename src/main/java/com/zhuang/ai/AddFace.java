package com.zhuang.ai;


import com.zhuang.ai.util.Base64Util;

public class AddFace {
    public static void main(String[] args) throws Exception {

        AppConfig config = new AppConfig();
        config.setSecretId("xxxxx");
        config.setSecretKey("xxxx");
        config.setServerIp("iai.tencentcloudapi.com");
        config.setArea("ap-nanjing");
        config.setGroupId("01");
        config.setPersonIdPre("01");
        FaceApi2 faceApi2 = new FaceApi2();
        String base = Base64Util.encodeBase64File("D://1.jpg");
        RootResp resp = faceApi2.newperson(config, "person02", "person02", base);
        System.out.println(resp);
    }
}
