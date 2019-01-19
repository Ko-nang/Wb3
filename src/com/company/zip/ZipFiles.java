package com.company.zip;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Hakase on 2019/1/18.
 * @version 1.0
 */
public class ZipFiles {
    public static void main(String[] args) {
        String pathCompress="C:/Users/Hakase/Desktop/temp";
        String pathDecompress="C:/Users/Hakase/Desktop/temp2";
        zipCompress(pathCompress);
        zipDecompress(pathCompress+".zip",pathDecompress);
    }

    /**
     * 根据文件地址执行压缩操作
     */
    public static void zipCompress(String pathCompress) {
        File file = new File(pathCompress);
        try (ZipOutputStream zo = new ZipOutputStream(new FileOutputStream(pathCompress+".zip"))) {
            zo.setLevel(5);
            zo.setComment("Konang");
            fileZip(file, zo, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压缩操作
     *
     * @param pathCompress 需要解压文件的地址
     * @param dest 解压的目标地址
     */
    public static void zipDecompress(String pathCompress, String dest) {
        int len;
        try (ZipInputStream zi = new ZipInputStream(new FileInputStream(pathCompress), Charset.forName("UTF-8"))
        ) {
            //
            while (true) {
                //获取入口
                ZipEntry ze = zi.getNextEntry();
                if (null != ze) {
                    //定义当前文件的解压路径
                    String pathed = dest + "/" + ze.getName();
                    File file = new File(pathed);
                    //如果是文件则新建文件
                    if (ze.isDirectory()) {
                        file.mkdirs();
                    } else {
                        //否则通过压缩包流读文件并通过FileOutputStream写出文件
                        FileOutputStream fo = new FileOutputStream(file);
                        byte[] bytes = new byte[1024];
                        while (-1 != (len = zi.read(bytes))) {
                            fo.write(bytes, 0, len);
                        }
                        //关流
                        fo.close();
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 具体操作压缩
     *
     * @param file   压缩的文件
     * @param zo     压缩流
     * @param pathUp 上级目录名
     */
    private static void fileZip(File file, ZipOutputStream zo, String pathUp) {
        //获取上级目录＋当前目录名
        String pathed = pathUp + file.getName();
        //如果是非文件夹则直接压缩
        if (file.isFile()) {
            try {
                zo.putNextEntry(new ZipEntry(pathed));
                fileCope(file, zo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //若是文件夹则压缩并递归到下一级
        else {
            pathed += "/";
            try {
                zo.putNextEntry(new ZipEntry(pathed));
                File[] files = file.listFiles();
                if (null != files) {
                    for (File ff : files) {
                        fileZip(ff, zo, pathed);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 压缩具体非文件夹时拷贝文件
     *
     * @param file 目标文件
     * @param zo   压缩输出流
     */
    private static void fileCope(File file, OutputStream zo) {
        int len;
        try (BufferedInputStream bi = new BufferedInputStream(new FileInputStream(file))) {
            byte[] bytes = new byte[1024];
            while (-1 != (len = bi.read(bytes))) {
                zo.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}