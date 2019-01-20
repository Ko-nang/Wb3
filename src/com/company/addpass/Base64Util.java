package com.company.addpass;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.*;
import java.nio.BufferOverflowException;
import java.util.Base64;

/**
 * @author Hakase on 2019/1/19.
 * @version 1.0
 */
public class Base64Util {
    public static void main(String[] args) throws FileNotFoundException {
        String a="Idea love code,you know why?";
        System.out.println(base64Compress(a.getBytes()));
        System.out.println(base64Decompress(base64Compress(a.getBytes())));
//        File file=new File("pk.jpg");
//        String a=base64Compress(Md5.fileRead(file));
//        System.setOut(new PrintStream(new FileOutputStream("bb.html")));
//        StringBuilder sb=new StringBuilder();
//        sb.append("<img src =");
//        sb.append("\"");
//        sb.append("data:image/png;base64,");
//        sb.append(a);
//        sb.append("\"/>");
//        System.out.println(sb.toString());
    }

    /**
     * 编码操作
     * @param bytes 将所需编码的文件等转换为字节数组后编码
     * @return 返回Base64编码后的内容
     */
    public static String base64Compress(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 解码操作
     * @param ss 将编码的Base64编码字符串解密为原文
     * @return 返回解密后的内容
     */
    public static String base64Decompress(String ss){
        return new String(Base64.getDecoder().decode(ss));
    }
}
