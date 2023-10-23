package com.zhuang.ai;

public class CreateFaceGroup {
    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        config.setSecretId("AKIDHQxPq8aFvX5slZPA7413JYYbmmXC39EB");
        config.setSecretKey("0qZPBtSw02qZNJAADFD72lKukikcbkjv");
        config.setServerIp("iai.tencentcloudapi.com");
        config.setArea("ap-nanjing");
        config.setGroupId("03");
        config.setGroupName("03");
        config.setPersonIdPre("03");

        FaceApi2 faceApi2 = new FaceApi2();
        faceApi2.createGroup(config);
        System.out.println("success");
    }
}
