// IOnNewBookArrivedListener.aidl
package com.example.hp.kaifayishu;

// Declare any non-default types here with import statements

import com.example.hp.kaifayishu.Book;
interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
