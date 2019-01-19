package com.test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Hakase on 2019/1/18.
 * @version 1.0
 */
public class Test {
    public static void main(String[] args){
        String pathCompress="C:/Users/Hakase/Desktop/temp";
        String pathDecompress="C:/Users/Hakase/Desktop/temp2";
        //zipCompress(pathCompress);
        zipDecompress(pathCompress,pathDecompress);
    }
    private static void zipCompress(String pathCompress){
        File file=new File(pathCompress);
        try( ZipOutputStream zo=new ZipOutputStream(new FileOutputStream(pathCompress+".zip"))) {
            classificationOperation(file,zo,"");
            zo.setLevel(5);
            zo.setComment("Konang");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void zipDecompress(String pathCompress,String pathDecompress){
        String path="";
        int len;
        try(ZipInputStream zi=new ZipInputStream(new FileInputStream(pathCompress+".zip"), Charset.forName("UTF-8"))){
            while (true){
                ZipEntry ze=zi.getNextEntry();
                if (null!=ze){
                    path=pathDecompress+"/"+ze.getName();
                    File file=new File(path);
                if (ze.isDirectory()){
                    file.mkdirs();
                }else {
                    BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(path));
                    byte[] bytes=new byte[1024];
                    while (-1!=(len=zi.read(bytes))){
                        bo.write(bytes,0,len);
                    }
                    bo.close();
                }
                }else {
                    break;
                }
            }

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void classificationOperation(File file,ZipOutputStream zo,String pathUp){
        File[] files=file.listFiles();
        String path=pathUp+file.getName();
        if (file.isFile()){
            try {
                zo.putNextEntry(new ZipEntry(path));
                fileCopy(file,zo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            path+="/";
            try {
                zo.putNextEntry(new ZipEntry(path));
                for (File ff:files){
                    classificationOperation(ff,zo,path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void fileCopy(File file , ZipOutputStream zo){
        int num;
        try(BufferedInputStream bf=new BufferedInputStream(new FileInputStream(file))){
            byte[] bytes=new byte[1024];
            while (-1!=(num=bf.read(bytes))){
                zo.write(bytes,0,num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
