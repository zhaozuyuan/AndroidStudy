package com.example.hp.kaifayishu.binder;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.example.hp.kaifayishu.binder.Book;

import java.util.List;

/**
 * @类名:IBookManager
 * @创建人:赵祖元
 * @创建时间：2018/10/3 22:46
 * @简述:
 */
public interface IBookManager extends IInterface {

    static final String DESCRIPTOR = "com.example.hp.kaifayishu.binder.IBookManager";

    //getBookList() 的Id
    static final int TRANSATION_getBookList = IBinder.FIRST_CALL_TRANSACTION;

    static final int TRANSATION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    public List<Book> getBookList() throws RemoteException;

    public void addBook(Book book) throws RemoteException;
}
