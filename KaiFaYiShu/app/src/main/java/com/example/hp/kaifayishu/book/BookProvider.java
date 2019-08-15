package com.example.hp.kaifayishu.book;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @类名:BookProvider
 * @创建人:赵祖元
 * @创建时间：2018/10/4 16:10
 * @简述:
 */
public class BookProvider extends ContentProvider {

    private static final String TAG = "BookProvider";

    //唯一标识符
    public static final String AUTHORITY = "com.example.hp.kaifayishu.book.provider";

    //两个表的uri
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    //两个表的标识码
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //将标识码和表联系起来
    //唯一标识符、path、code
    static {
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private Context mContext ;
    private SQLiteDatabase mDb;

    /**
     * 只有这一个方法在UI线程调用
     * 其它方法都在Binder线程池中调用
     *
     * @return
     */
    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate " + Thread.currentThread().getName());

        mContext = getContext();
        initProviderData();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String
            selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG,"query " + Thread.currentThread().getName());

        String table = getTabName(uri);
        if(table == null) throw new IllegalArgumentException("Unsupported URI: " + uri);

        return mDb.query(table, projection, selection, selectionArgs, null,
                null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG,"insert " + Thread.currentThread().getName());

        String table = getTabName(uri);

        if(table == null) throw new IllegalArgumentException("Unsupported URI: " + uri);

        mDb.insert(table, null, values);
        mContext.getContentResolver().notifyChange(uri, null);

        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[]
            selectionArgs) {
        Log.d(TAG,"delete " + Thread.currentThread().getName());

        String table = getTabName(uri);
        if(table == null) throw new IllegalArgumentException("Unsupported URI: " + uri);

        int count = mDb.delete(table, selection, selectionArgs);
        if(count > 0) mContext.getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        Log.d(TAG,"update " + Thread.currentThread().getName());

        String table = getTabName(uri);
        if(table == null) throw new IllegalArgumentException("Unsupported URI: " + uri);

        int row = mDb.update(table, values, selection, selectionArgs);
        if (row > 0) mContext.getContentResolver().notifyChange(uri, null);

        return row;
    }

    /**
     * 初始化数据库的数据
     * 实际上应该异步操作！
     */
    private void initProviderData() {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();

        //先清空表
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);

        //再插入数据
        mDb.execSQL("insert into book values (3, 'Android');");
        mDb.execSQL("insert into book values (4, 'Html15');");
        mDb.execSQL("insert into book values (5, 'Ios');");
        mDb.execSQL("insert into user values (1, 'jake', 1);");
        mDb.execSQL("insert into user values (2, 'jasmine',0);");
    }

    /**
     * @param uri
     * @return 选中的表名
     */
    private String getTabName (Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)){
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }

        return tableName;
    }
}
