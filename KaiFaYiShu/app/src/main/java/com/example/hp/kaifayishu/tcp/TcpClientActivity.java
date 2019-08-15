package com.example.hp.kaifayishu.tcp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hp.kaifayishu.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcpClientActivity extends AppCompatActivity {

    private static final int MESSAGE_RECEIVE_NEW_MSG = 1 ;  //新消息
    private static final int MESSAGE_SOCKET_CONNECTED = 2;  //链接

    private Button btnSend;
    private EditText etMessage;
    private TextView tvMessage;

    private PrintWriter mPrintWriter;   //用于发送消息
    private Socket mSocket; //套接字 解决传输层的的数据传输问题

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    tvMessage.setText(tvMessage.getText().toString() + (String) msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    btnSend.setEnabled(true);   //可启用
                    btnSend.setText("已启用");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_client);

        btnSend = (Button) findViewById(R.id.btn_tcp_send);
        etMessage = (EditText) findViewById(R.id.et_tcp_send);
        tvMessage = (TextView) findViewById(R.id.tv_tcp_recive);

        Intent intent = new Intent();
        intent.setAction("com.example.hp.tcp.TcpServerService");
        intent.setPackage("com.example.hp");
        startService(intent);  //启动服务
        //startService(new Intent(this, TCPServerService.class));

        new Thread(new Runnable() {
            @Override
            public void run() {
                 connectTCPServer();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        if (mSocket != null){
            try {
                mSocket.shutdownInput();
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    /**
     * 发送消息
     *
     * @param view
     */
    @SuppressLint("SetTextI18n")
    public void sendMessageOnClick(View view) {
        final String msg = etMessage.getText().toString();

        if(!TextUtils.isEmpty(msg) && mPrintWriter != null){
            mPrintWriter.println(msg);  //实现发送消息

            etMessage.setText("");
            String time = formatDateTime(System.currentTimeMillis());
            final String showMsg = "self" + time + ":" + msg + "\n";
            tvMessage.setText(tvMessage.getText().toString() + showMsg);
        }
    }

    /**
     * 转化时间 格式
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private String formatDateTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }

    /**
     * 建立联系
     */
    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688); //本地端口是8688的一个插口
                mSocket = socket;   //拿到插口对象
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                        .getOutputStream())),true);
                //成功得到Socket和PrintWrite对象，实现 握手成功
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.d("QQ", "success");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                e.printStackTrace();
            }
        }

        try {
            //读取消息
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            while (!TcpClientActivity.this.isFinishing()) {
                String msg = reader.readLine();
                Log.d("QQ", "client receive : " + msg);
                if (msg != null){
                    String time = formatDateTime(System.currentTimeMillis());
                    final String showMsg = "server " + time + ":" + msg + "\n";
                    //新消息到来，得到message
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showMsg).sendToTarget();
                }

                //实现节约cpu资源
                //此处如果是消息积累则会出现？？？
                Thread.sleep(200);
            }
            reader.close();
            mPrintWriter.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
