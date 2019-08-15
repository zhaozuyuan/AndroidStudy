package com.example.hp.kaifayishu.book;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.hp.kaifayishu.Book;
import com.example.hp.kaifayishu.IBookManager;
import com.example.hp.kaifayishu.IOnNewBookArrivedListener;
import com.example.hp.kaifayishu.R;

import java.util.List;

/**
 * 要注意的是一般访问服务端都是耗时操作，UI线程需要等待服务端返回信息，所以一般都是异步操作 —— handler
 * 同理，在服务端回调接口的时候，服务端同样需要等待接口方法执行完毕，也就是“新书到了”执行的方法同样不能耗时
 * 或者在服务端回调接口放在了子线程中去做。
 *
 */
public class BookManagerActivity extends AppCompatActivity {

    private static final String TAG = "BookManagerActivity";

    private IBookManager bookManager;

    /**
     * 处理服务端送过来的消息
     * 此处应该进行同步操作
     * 使用handler内操作
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    //实现“新书到了”的监听
    //在客户端做好监听器，传回服务端,服务端触发监听事件
    private IOnNewBookArrivedListener listener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            //注册了一个监听器
            bookManager.registerListener(new IOnNewBookArrivedListener() {
                @Override
                public void onNewBookArrived(Book book) throws RemoteException {
                    //新书到达 服务端主动发过来消息
                    handler.obtainMessage(1, book).sendToTarget();
                }

                @Override
                public IBinder asBinder() {
                    return null;
                }
            });

        }
    };

    //创建联系
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //service 即是代理binder对象

            //拿到manager对象
            bookManager = IBookManager.Stub.asInterface(service);
            try {
                //请求拿到书单(客户端提出请求)
                List<Book> list = bookManager.getBookList();

                //请求添加书籍(客户端提出请求)
                Book newBook = new Book(3, "Web");
                bookManager.addBook(newBook);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            //注册监听器
            try {
                bookManager.registerListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);

        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        //先解除监听器绑定
        //isBinderAlive() 检查binder所在进程是否存活(true)
        if (bookManager != null && bookManager.asBinder().isBinderAlive()){
            try {
                bookManager.unregisterListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        unbindService(mConnection);
        super.onDestroy();
    }
}
