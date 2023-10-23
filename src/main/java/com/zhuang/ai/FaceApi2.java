package com.zhuang.ai;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.iai.v20180301.IaiClient;
import com.tencentcloudapi.iai.v20180301.models.*;
import com.zhuang.ai.util.Base64Util;
import org.apache.log4j.Logger;

public class FaceApi2 {

    private final Logger logger = Logger.getLogger(FaceApi2.class);

    /**
     * 人脸分析DetectFace
     *
     * @param config
     * @param url
     * @return
     * @throws Exception
     */
    public RootResp detectFace(AppConfig config, String url) {
        RootResp result = new RootResp();
        try {

            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);
            JSONObject paramObj = new JSONObject();
            paramObj.put("Url", url);
            paramObj.put("MaxFaceNum", 1);
            paramObj.put("MinFaceSize", 34);
            paramObj.put("NeedFaceAttributes", 0);
            paramObj.put("NeedQualityDetection", 1);
            DetectFaceRequest req = DetectFaceRequest.fromJsonString(paramObj.toJSONString(), DetectFaceRequest.class);
            DetectFaceResponse resp = client.DetectFace(req);
            result.setData(DetectFaceResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }
        logger.info(result);
        return result;

    }

    /**
     * 添加个体
     *
     * @param config
     * @param personId   个体ID
     * @param personName 个体姓名
     * @param image      图片base64编码
     * @return
     * @throws Exception
     */
    public RootResp newperson(AppConfig config, String personId, String personName, String image) {
        RootResp result = new RootResp();
        try {

            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);

            // String params = "{\"GroupId\":\"groupid\",\"PersonName\":\"zhangshihuo\",\"PersonId\":\"1\",\"Image\":\"fffffff\"}";
            JSONObject paramObj = new JSONObject();
            paramObj.put("GroupId", config.getGroupId());
            paramObj.put("PersonId", config.getPersonIdPre() + personId);
            paramObj.put("PersonName", personName);
            paramObj.put("Image", image);
            //logger.info(paramObj.toJSONString());
            CreatePersonRequest req = CreatePersonRequest.fromJsonString(paramObj.toJSONString(), CreatePersonRequest.class);
            CreatePersonResponse resp = client.CreatePerson(req);

            result.setData(CreatePersonResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }

        //{"data":"{\"FaceId\":\"3597806115455299295\",\"FaceRect\":{\"X\":661,\"Y\":142,\"Width\":331,\"Height\":437},\"SimilarPersonId\":\"\",\"FaceModelVersion\":\"3.0\",\"RequestId\":\"7e5435ad-383e-4f52-821c-557eeff11dce\"}","ret":0}
        logger.info(result);
        return result;

    }

    /**
     * 删除个体
     *
     * @param personId
     * @return
     */
    public RootResp delperson(AppConfig config, String personId) {

        RootResp result = new RootResp();
        try {

            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);

            // String params = "{\"PersonId\":\"mypersonId\"}";
            JSONObject paramObj = new JSONObject();
            paramObj.put("PersonId", config.getPersonIdPre() + personId);
            DeletePersonRequest req = DeletePersonRequest.fromJsonString(paramObj.toJSONString(), DeletePersonRequest.class);
            DeletePersonResponse resp = client.DeletePerson(req);
            result.setData(DeletePersonResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }
        // {"data":"{\"RequestId\":\"fc54738d-71e7-4b3f-ab86-f001f90f835e\"}","ret":0}
        logger.info(result);
        return result;
    }

    public static void main(String[] args) throws Exception {

        AppConfig config = new AppConfig();
        config.setSecretId("xxxxx");
        config.setSecretKey("xxxx");
        config.setServerIp("iai.tencentcloudapi.com");
        config.setArea("ap-nanjing");
        config.setGroupId("z01");
        config.setPersonIdPre("001");
        FaceApi2 faceApi2 = new FaceApi2();
        faceApi2.delperson(config, "");

        String b64 = Base64Util.encodeBase64File("D://icon.jpg");

        RootResp resp = faceApi2.newperson(config, "01", "01", b64);
        System.out.println(resp);

    }


    /**
     * 增加人脸
     *
     * @param config
     * @param personId 个体ID
     * @param image    图片base64编码
     * @return
     * @throws Exception
     */
    public RootResp addface(AppConfig config, String personId, String image) {

        RootResp result = new RootResp();
        try {

            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);

            //String params = "{\"PersonId\":\"mypersonId\",\"Images\":[\"imafaffafafafafafaf\",\"afewfewfeffffffff\"]}";
            JSONObject paramObj = new JSONObject();
            JSONArray images = new JSONArray();
            images.add(image);
            paramObj.put("PersonId", config.getPersonIdPre() + personId);
            paramObj.put("Images", images);
            CreateFaceRequest req = CreateFaceRequest.fromJsonString(paramObj.toJSONString(), CreateFaceRequest.class);

            CreateFaceResponse resp = client.CreateFace(req);
            result.setData(CreateFaceResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }

        //{"data":"{\"SucFaceNum\":1,\"SucFaceIds\":[\"3597809918601647307\"],\"RetCode\":[0],\"SucIndexes\":[0],\"SucFaceRects\":[{\"X\":729,\"Y\":123,\"Width\":324,\"Height\":427}],\"FaceModelVersion\":\"3.0\",\"RequestId\":\"5c674cd1-9e9d-4bbc-91f9-27cab3ca16f2\"}","ret":0}
        logger.info(result);

        return result;
    }

    /**
     * 删除人脸
     *
     * @param config
     * @param personId 个体ID
     * @param faceId
     * @return
     */
    public RootResp delface(AppConfig config, String personId, String faceId) {
        RootResp result = new RootResp();
        try {

            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);

            //String params = "{\"PersonId\":\"mypersonId\",\"FaceIds\":[\"myfaceId1\",\"myfaceId2\"]}";
            JSONObject paramObj = new JSONObject();
            JSONArray faces = new JSONArray();
            faces.add(faceId);
            paramObj.put("PersonId", config.getPersonIdPre() + personId);
            paramObj.put("FaceIds", faces);
            DeleteFaceRequest req = DeleteFaceRequest.fromJsonString(paramObj.toJSONString(), DeleteFaceRequest.class);

            DeleteFaceResponse resp = client.DeleteFace(req);

            result.setData(DeleteFaceResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }

        logger.info(result);

        return result;
    }

    //人脸验证
    public RootResp faceVerify(AppConfig config, String personId, String image) {
        RootResp result = new RootResp();
        try {

            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);

            //String params = "{\"Image\":\"myImage\",\"PersonId\":\"mypersonId\"}";
            JSONObject paramObj = new JSONObject();
            paramObj.put("PersonId", config.getPersonIdPre() + personId);
            paramObj.put("Image", image);
            VerifyFaceRequest req = VerifyFaceRequest.fromJsonString(paramObj.toJSONString(), VerifyFaceRequest.class);

            VerifyFaceResponse resp = client.VerifyFace(req);
            result.setData(VerifyFaceResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }

        //{"data":"{\"Score\":100.0,\"IsMatch\":true,\"FaceModelVersion\":\"3.0\",\"RequestId\":\"2a21c4d7-f195-417f-a126-b0599ddc9213\"}","ret":0}
        logger.info(result);

        return result;
    }

    //人员搜索按库返回
    public RootResp searchPersonsReturnsByGroup(AppConfig config, String image) {
        RootResp result = new RootResp();
        try {

            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);

            //String params = "{\"Image\":\"myImage\",\"PersonId\":\"mypersonId\"}";
            JSONObject paramObj = new JSONObject();
            paramObj.put("GroupIds", new String[]{config.getGroupId()});
            paramObj.put("Image", image);
            //最多返回的最相似人员数目
            paramObj.put("MaxPersonNumPerGroup", 5);
            //返回人员具体信息
            paramObj.put("NeedPersonInfo", 1);
            //最多识别的人脸数目
            paramObj.put("MaxFaceNum", 1);

            SearchFacesReturnsByGroupRequest req = SearchFacesReturnsByGroupRequest.fromJsonString(paramObj.toJSONString(), SearchFacesReturnsByGroupRequest.class);

            SearchFacesReturnsByGroupResponse resp = client.SearchFacesReturnsByGroup(req);
            result.setData(VerifyFaceResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }
        logger.info(result);

        return result;
    }

    public RootResp createGroup(AppConfig config) {
        RootResp result = new RootResp();
        try {
            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(config.getServerIp());
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            IaiClient client = new IaiClient(cred, config.getArea(), clientProfile);

            JSONObject paramObj = new JSONObject();
            paramObj.put("GroupId", config.getGroupId());
            paramObj.put("GroupName", config.getGroupName());
            CreateGroupRequest req = CreateGroupRequest.fromJsonString(paramObj.toJSONString(), CreateGroupRequest.class);

            CreateGroupResponse resp = client.CreateGroup(req);
            result.setData(CreateGroupResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            result.setRet(-1);
            result.setMsg(e.toString());
            logger.error(e.toString());
        }
        logger.info(result);
        return result;
    }
}
