package com.zhuang.ai.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {


    public static void main(String[] args) throws Exception {
        String b64 = Base64Util.encodeBase64File("D://icon.jpg");
        System.out.println(b64);
        Base64Util.decoderBase64File(b64, "D://icon.jpg");
        System.out.println("success");
    }

    /**
     * 将二进制数据编码为BASE64字符串
     *
     * @param binaryData byte[]
     */
    public static String encode(byte[] binaryData) {
        return new String(Base64.encodeBase64(binaryData), StandardCharsets.UTF_8);
    }

    /**
     * 将BASE64字符串恢复为二进制数据
     *
     * @param base64String byte[]
     */
    public static byte[] decode(String base64String) {
        return Base64.decodeBase64(base64String.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将文件转成base64 字符串
     *
     * @param path 文件路径
     */

    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return encode(buffer);
    }

    /**
     * 将base64字符解码保存文件
     *
     * @param base64Code String
     * @param targetPath String
     */

    public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
        byte[] buffer = decode(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * 将base64字符保存文本文件
     *
     * @param base64Code String
     * @param targetPath String
     */

    public static void toFile(String base64Code, String targetPath) throws Exception {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }


}
