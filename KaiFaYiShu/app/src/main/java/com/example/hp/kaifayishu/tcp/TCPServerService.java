package com.example.hp.kaifayishu.tcp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServerService extends Service {

    private boolean mIsServerDestory = false;
    private String[] mDefinedMessage = new String[]{
            "你好啊，欢欢！",
            "我是陆超，真好！",
            "你喜欢刷抖音吗？",
            "吃鸭儿嘛你！"};

    public TCPServerService() {
    }

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        mIsServerDestory = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);  //对接8688端口
                //握手成功
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (!mIsServerDestory) {
                try {
                    //接收客户端消息
                    //不断地接收消息
                    //这里假装处理消息也是一个耗时地过程，再开一个线程
                    final Socket client = serverSocket.accept();
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                //回复客户端
                                responesClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 实现消息读取和回复
     *
     * @param client
     * @throws IOException
     */
    private void responesClient(Socket client) throws IOException{
        //用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client
                .getOutputStream())),true);

        out.println("欢迎来到聊天室！");
        while (!mIsServerDestory) {
            String str = in.readLine();
            Log.d("QQ", "message from client : " + str);
            if (str == null){
                //客户端断开链接
                break;
            }

            //回应消息
            int i = new Random().nextInt(mDefinedMessage.length);
            String msg = mDefinedMessage[i];
            out.println(msg);
        }

        out.close();
        in.close();
        client.close();
    }
}
