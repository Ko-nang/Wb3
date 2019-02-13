package com.company.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Hakase
 * @version 1.0
 */
public class Img {
    public static void main(String[] args) throws IOException {
        //imgChange();
        imgCut();
    }

    /**
     * 新建图像，写上文字
     */
    private static void imgChange() throws IOException {
        BufferedImage dest = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics d = dest.getGraphics();
        d.drawString("戏说不是胡说", 10, 10);
        ImageIO.write(dest, "jpeg", new File("a.jpeg"));
    }

    /**
     * 图像分割为10块
     */
    private static void imgCut() throws IOException {
        int a = 2, b = 5;
        int i, j;
        BufferedImage str = ImageIO.read(new File("pk.jpg"));
        //分割后图像高度
        int he = str.getHeight() / b;
        //分割后图像宽度
        int wh = str.getWidth() / a;
        for (i = 0; i < a; i++) {
            for (j = 0; j < b; j++) {
                BufferedImage dest = new BufferedImage(wh, he, BufferedImage.TYPE_INT_RGB);
                Graphics d = dest.getGraphics();
                d.drawImage(str, 0, 0, wh, he, wh * i, he * j, wh * i + wh, he * j + he, null);
                ImageIO.write(dest, "jpeg", new File("src/images/" + i + j + ".jpeg"));
            }
        }
    }
}
