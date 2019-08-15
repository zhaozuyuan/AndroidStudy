package com.example.hp.kaifayishu.book;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.hp.kaifayishu.Book;
import com.example.hp.kaifayishu.IBookManager;
import com.example.hp.kaifayishu.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @类名:BookManagerService
 * @创建人:赵祖元
 * @创建时间：2018/10/4 1:01
 * @简述: 这个服务提供增加书籍和提供书籍资源的以及新书提醒功能
 */
public class BookManagerService extends Service {

    private static final String TAG = "BMS";

    //书的容器，该容器支持并发读/写
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    //监听器 可能不止一个地方启动了这个服务
    //remotecallbacklist 自动完成（进程）取消监听注册
    private RemoteCallbackList<IOnNewBookArrivedListener> listeners = new RemoteCallbackList<>();

    //创建binder
    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            //自动判断是否包含
            listeners.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listeners.unregister(listener);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "Ios"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * 新书到了 监听回调
     *
     * @param book
     * @throws RemoteException
     */
    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        final int N = listeners.beginBroadcast(); //返回需要callback数量
        for (int i = 0; i < N; i++){
            IOnNewBookArrivedListener listener = listeners.getBroadcastItem(i);
            listener.onNewBookArrived(book);
        }

        listeners.finishBroadcast();
    }
}
