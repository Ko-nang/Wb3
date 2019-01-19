package com.company.zip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**文件壓縮單例
 * @author Hakase on 2019/1/18.
 * @version 1.0
 */
public class Zip {
    public static void main(String[] args){
        zipCompress();
    }
    private static void zipCompress(){
        //读取目标文件a.txt;
        try (FileInputStream fi=new FileInputStream("a.txt");
             //写入到文件pk.zip中
             FileOutputStream fo = new FileOutputStream("pk.zip");
             //压缩
             ZipOutputStream zo=new ZipOutputStream(fo);){
            //压缩入口
            ZipEntry ze = new ZipEntry("aa/a.txt");
            //添加压缩入口到压缩流
            zo.putNextEntry(ze);
            //压缩过程实质为文件复制过程，执行文件复制操作
            byte[] bytes = new byte[1024];
            while (true){
            int len=fi.read(bytes);
            if (-1!=len){
                zo.write(bytes,0,len);
            }else {
                break;
            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
