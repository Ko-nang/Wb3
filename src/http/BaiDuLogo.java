package http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


/**
 * @author Ko-nang
 * @date 2019/2/13 11:41
 */
public class BaiDuLogo {
    public static void main(String[] args) throws IOException {
        //初始化请求报文
        StringBuilder sb = new StringBuilder();
        sb.append("GET /img/bd_logo1.png HTTP/1.1\r\n");
        sb.append("HOST: baidu.com\r\n\r\n");
        Socket socket = new Socket("www.baidu.com", 80);
        //发送请求报文
        socket.getOutputStream().write(sb.toString().getBytes());
        //获取响应报文
        InputStream in = socket.getInputStream();
        sb.delete(0,sb.length());
        //获取响应体字节数
        String length = getLengh(sb, in);
        //精准写出响应体中的内容
        getImg(in, length);
        //关闭socket
        socket.close();
    }

    /**
     * 将响应信息中的数据展现
     * @param in 传输流
     * @param leng 字节长度
     */

    private static void getImg(InputStream in, String leng) throws IOException {
        int length=Integer.parseInt(leng);
        FileOutputStream fo=new FileOutputStream(new File("baidulogo.png"));
        byte[] bytes=new byte[1024*4];
        int count=0;
        while (count<length){
            int read = in.read(bytes);
            fo.write(bytes,0,read);
            count+=read;
        }
        fo.close();
    }

    /**
     * 获取请求报文中所需数据的字节长度
     * @param sb 用于存储报文信息
     * @param in 数据流
     * @return 字节长度
     * @throws IOException
     */

    private static String getLengh(StringBuilder sb, InputStream in) throws IOException {
        String leng = "";
        String[] split;
        String[] metho;
        while (true){
            char temp= (char) in.read();
            sb.append(temp);
            if (sb.length() > 4) {
                if ("\r\n\r\n".equals(sb.substring(sb.length() - 4))){
                    break;
                }
            }
        }
        metho=sb.toString().split("\r\n");
        for (String s : metho) {
            split = s.split(": ");
            if ("Content-Length".equals(split[0])) {
                leng = split[1].trim();
            }
        }
        return leng;
    }
}
