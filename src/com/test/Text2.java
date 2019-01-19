package com.test;

/**
 * @author Hakase on 2019/1/19.
 * @version 1.0
 */
public class Text2 {
    public static void main(String[] args){
     byte b = -32;
     int c=0xff&b;
       String a=Integer.toHexString(c);
       System.out.println(c);
       System.out.println(a);
    }
}
