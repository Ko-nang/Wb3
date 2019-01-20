package com.company.addpass;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Hakase on 2019/1/19.
 * @version 1.0
 * dfae007079aa53071fefcb101a7f
 * dfae0d047079aa530571fefcb1001a7f
 */
public class Md5 {
    public static void main(String[] args){
        String a="多多";
      System.out.println( md5_32(a.getBytes()));
    }

    /**
     * 通过传输一个处理后的byte数组进行MD5加密
     * @param bytes 将所需加密的文件转换为字节数组
     * @return 返回加密后的32位MD5码
     */
    public static String md5_32(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        try {
            //该MessageDigest类为应用程序提供消息摘要算法的功能
            MessageDigest md=MessageDigest.getInstance("MD5");
            //数据通过它使用update方法进行处理，通过update函数更新摘要
            md.update(bytes);
            //使用指定的字节数组对摘要执行最终更新，然后完成摘要计算。 也就是说，这种方法首先调用update(input) ，将输入数组传递给update方法，然后调用digest()
            byte[] bytes1 = md.digest();
            for (byte b:bytes1){
                //将结果转换成16进制数表示
               int a=0xff&b;
               if (a<16){
                   sb.append("0");
               }
                sb.append(Integer.toHexString(a));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 显示16位MD5编码
     * @param bytes 需要计算的字节数组
     * @return 返回所需编码
     */
    public static String md5_16(byte[] bytes){
        return md5_32(bytes).substring(8,24);
    }
    /**
     * 对文件进行MD5编码
     * @param file 需要需要编码的文件
     * @return 返回所需32位编码
     */
    public static String md5File(File file){
        return md5_32( fileRead(file));
    }

    /**
     * 对文件进行转换，转换为字节数组
     * @param file 需要转换的文件
     * @return 返回转换好的字节数组
     */
    public static byte[] fileRead(File file){
        int len;
        //该类实现了将数据写入字节数组的输出流。 当数据写入缓冲区时，缓冲区会自动增长。 数据可以使用toByteArray()和toString() 。
        ByteArrayOutputStream bo=new ByteArrayOutputStream();
        //文件内容以字节数组的形式读到ByteArrayOutputStream缓冲流中
        try (FileInputStream fi=new FileInputStream(file)){
            byte[] bytes=new byte[1024];
            while (-1!=(len=fi.read(bytes))){
                bo.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建一个新分配的字节数组。 其大小是此输出流的当前大小，缓冲区的有效内容已被复制到其中。
        return bo.toByteArray();
    }
}
