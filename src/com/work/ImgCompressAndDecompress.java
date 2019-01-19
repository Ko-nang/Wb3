package com.work;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**根据给出的两个资源得到最后的图片。并观察原始图片的内容，还原正确的图片内容（具体详见images/Compress.png）
 * @author Hakase on 2019/1/20.
 * @version 1.0
 */
public class ImgCompressAndDecompress {
    public static void main(String[] args){
       //System.out.println( md5Compress("多多"));
    }

    /**
     * URL编码操作:将字符串转化为在URL中显示的内容
     * @param str 原始字符串
     * @return 转化后的字符串
     */
    private  String urlCompress(String str){
        String a=null;
        try {
            a= URLEncoder.encode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * URL解码操作:将URL中的特殊编码转化为原始可知的字符串形式
     * @param str 目标URL特殊字符串
     * @return 解码的可解读字符串
     */
    private  String urlDecompress(String str){
        String a=null;
        try {
            a= URLDecoder.decode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * MD5编码实现：将给定字符串转化为16进制32位MD5编码
     * @param str 原字符串
     * @return 结果字符串（MD5编码）
     */
    private  String md5Compress(String str){
        int temp;
        StringBuilder md5String=new StringBuilder();
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            md.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] bytes=md.digest();
            for (byte b:bytes){
                temp=0xff&b;
                if (temp<16){
                    md5String.append("0");
                }else {
                    md5String.append(Integer.toHexString(temp));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5String.toString();
    }

    /**
     * 将字符串转换成密钥字节数组
     * @param str 原字符串
     * @return 密钥
     */
    private  byte[] generatingKey(String str){
        return str.getBytes();
    }
    private  byte[] compress(byte[] str){
        return null;
    }
}
