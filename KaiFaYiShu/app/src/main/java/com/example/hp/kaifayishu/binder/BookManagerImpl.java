package com.example.hp.kaifayishu.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.hp.kaifayishu.binder.Book;

import java.util.List;

/**
 * @类名:BookManagerImpl
 * @创建人:赵祖元
 * @创建时间：2018/10/3 22:55
 * @简述: 仿照AIDL
 */
public class BookManagerImpl extends Binder implements IBookManager {

    /**
     * 判断是否是跨进程
     *
     * @param obj
     * @return 服务端binder对象 or 代理对象(跨进程)
     */
    public static IBookManager asInterface(IBinder obj){
        if (obj == null) return null;

        android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if ((iin != null) && (iin instanceof IBookManager)){
            return (IBookManager) iin;
        }else return new Proxy(obj);
    }

    /**
     * @return 这个binder
     */
    @Override
    public IBinder asBinder() {
        return this;
    }

    /**
     * 处理客户端的请求，判断调用的方法
     * 可以在该方法中设置权限
     *
     * @param code 方法识别码
     * @param data 包含客户端传过来的数据
     * @param reply 回复给客户端的序列化Parcel对象
     * @param flags
     * @return 是否进行处理
     * @throws RemoteException
     */
    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags)
            throws RemoteException {
        switch (code){
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSATION_getBookList:
                data.enforceInterface(DESCRIPTOR);
                List<Book>result = this.getBookList();
                reply.writeNoException();
                reply.writeTypedList(result);
                return true;
            case TRANSATION_addBook:
                data.enforceInterface(DESCRIPTOR);
                Book arg0 ;
                if (0 != data.readInt()){
                    arg0 = Book.CREATOR.createFromParcel(data);
                }else {
                    arg0 = null;
                }
                this.addBook(arg0);
                reply.writeNoException();
                return true;
        }

        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public List<Book> getBookList() throws RemoteException {
        //TODO
        return null;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        //TODO
    }

    /**
     * 在客户端实现的代理类
     */
    public static class Proxy implements IBookManager {

        private IBinder mRemote;

        Proxy(IBinder remote){
            this.mRemote = remote;
        }

        public java.lang.String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            List<Book> result;
            try {
                data.writeInterfaceToken(DESCRIPTOR);   //“芝麻开门”
                //此处需要一个返回值，所以先请求
                mRemote.transact(TRANSATION_getBookList, data, reply, 0);
                reply.readException();
                result = reply.createTypedArrayList(Book.CREATOR);  //得到序列化的Parcel
            }finally {
                reply.recycle();    //释放
                data.recycle();
            }

            return result;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                if (book != null){
                    data.writeInt(1);
                    book.writeToParcel(data, 0);    //写入数据
                }else data.writeInt(0);
                mRemote.transact(TRANSATION_addBook, data, reply, 0);
                reply.readException();
            }finally {
                data.recycle();
                reply.recycle();
            }
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }
}
