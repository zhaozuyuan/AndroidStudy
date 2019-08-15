package com.example.hp.xiaogongtest.fourhttp;



import android.util.TimeUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * @类名:MyHttpUrlConnection
 * @创建人:赵祖元
 * @创建时间：2018/8/3 21:18
 * @简述:
 */
public class MyHttpUrlConnection {

    public void sendRequest(String url) throws IOException{
        InputStream inputStream = null;

        URL newUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection)newUrl.openConnection();
        //设置读取超时为10s
        connection.setReadTimeout(10000);
        //设置连接超时为15s
        connection.setConnectTimeout(15000);
        //模式
        connection.setRequestMethod("POST");
        //接收输入流
        connection.setDoInput(true);
        //启动输出流，默认为false
        connection.setDoOutput(true);
        //添加header
        connection.setRequestProperty("Connection","Keep-Alive");


        //添加请求参数，若是GET,则写在Url中
        //List<NameValuePair> paramsList = new ArrayList<>();
        //paramsList.add(new BasicNameValuePair("username","mr.zzz"));
        //writeParams(connection.getOutputStream(),paramsList);

        //发送请求
        connection.connect();

        //获取输入流，自行处理
        inputStream = connection.getInputStream();

    }

    private void writeParams(OutputStream outputStream,List paramsList)throws IOException{
        StringBuilder parsmStr = new StringBuilder();

//        for(NameValuePair pair:paramsList){
//            if(!TimeUtils.isEmpty(paramsList)){
//                parsmStr.append("&");
//            }
//
//            parsmStr.append(URLEncoder.encode(pair.getName()),"UTF-8");
//        }

        BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
        writer.write(parsmStr.toString());
        writer.flush();
        writer.close();
    }
}
