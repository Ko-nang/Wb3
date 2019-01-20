package com.work;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**根据给出的两个资源得到最后的图片。并观察原始图片的内容，还原正确的图片内容（具体详见images/Compress.png）
 * @author Hakase on 2019/1/20.
 * @version 1.0
 */
public class ImgCompressAndDecompress {
    public static void main(String[] args){
        String path="C:/Users/Hakase/Desktop/temp/xx.jpg";
        String str="%E5%A4%9A%E5%A4%9A";
        ImgCompressAndDecompress icad=new ImgCompressAndDecompress();
        byte[] key=icad.generatingKey(icad.md5Compress(icad.urlDecompress(str)));
        byte[] zipByte=icad.base64Op(icad.zip(new File("src/zip/data.zip")));
        icad.fileShow(icad.xor(zipByte,key),path);
        icad.imgCup(new File(path));
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
            md.update(str.getBytes());
            byte[] bytes=md.digest();
            for (byte b:bytes){
                temp=0xff&b;
                if (temp<16){
                    md5String.append("0");
                }
                    md5String.append(Integer.toHexString(temp));
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
        return str.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 通过异或运算加密解密
     * @param str 明文或密文
     * @param key 密钥
     * @return 返回密文或明文
     */
    private  byte[] xor(byte[] str,byte[] key){
        byte[] ciphertext=new byte[str.length];
        for(int i=0;i<str.length;i++){
            ciphertext[i]= (byte) (str[i]^key[i%key.length]);
        }
        return ciphertext;
    }

    /**
     * Base64编码解密
     * @param str 需要解密的
     * @return 返回解密后的相关字节数组
     */
    private  byte[] base64Op(byte[] str){
        return Base64.getDecoder().decode(str);
    }

    /**
     * 读取压缩包中的文件，并将文件转化为字节数组
     * @param file 目标文件
     * @return 返回字符数组数据
     */
    private  byte[] zip(File file){
        byte[] aa=null;
        try(ZipInputStream zi=new ZipInputStream(new FileInputStream(file))){
            while (true){
                ZipEntry ze=zi.getNextEntry();
                if (null!=ze){
                    if (!ze.isDirectory()) {
                        System.out.println(ze.getName());
                       aa=fileByte(zi);
                    }
                }else {
                    break;
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return aa;
    }

    /**
     * 具体文件操作
     * @param zi 文件输入流：读取文件到流中
     * @return  返回字节数组
     */
    private  byte[] fileByte(InputStream zi)  {
        int len;
            ByteArrayOutputStream byteo=new ByteArrayOutputStream();
            byte[] bytes=new byte[1024];
            try {
                while (-1!=(len=zi.read(bytes))){
                    byteo.write(bytes,0,len);
                }
            }catch (IOException e){
                e.printStackTrace();
            }


        return byteo.toByteArray();
    }

    /**
     * 将得到的字节数组输出编译为具体文件形式
     * @param str 目标字节数组
     * @param path 文件输出具体地址（目录）
     */
    private  void fileShow(byte[] str,String path){
        int len;
        ByteArrayInputStream bi=new ByteArrayInputStream(str);
        byte[] bytes=new byte[1024];
        try (BufferedOutputStream bo=new BufferedOutputStream(new FileOutputStream(path))){
            while (-1!=(len=bi.read(bytes))){
                bo.write(bytes,0,len);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对已解密的文件进行还原
     * @param file 目标文件
     */
    private  void imgCup(File file){
        try {
            int newW;
            int newH;
            BufferedImage bi= ImageIO.read(file);
            int w=bi.getWidth();
            int h=bi.getHeight();
             newW=w/2;
             newH=h/2;
            BufferedImage newBi=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
            Graphics graphics = newBi.getGraphics();
            graphics.drawImage(bi,0,0,newW,newH,newW,newH,w,h,null);
            graphics.drawImage(bi,newW,0,w,newH,0,newH,newW,h,null);
            graphics.drawImage(bi,0,newH,newW,h,newW,0,w,newH,null);
            graphics.drawImage(bi,newW,newH,w,h,0,0,newW,newH,null);
            ImageIO.write(newBi,"jpg",new File("C:/Users/Hakase/Desktop/temp/xx.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
