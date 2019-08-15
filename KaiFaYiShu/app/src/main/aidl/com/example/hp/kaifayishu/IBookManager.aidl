// IBookManager.aidl
package com.example.hp.kaifayishu;

import com.example.hp.kaifayishu.Book;
import com.example.hp.kaifayishu.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
