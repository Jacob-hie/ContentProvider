package com.hie2j.listview;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class StuContentProvider extends ContentProvider {
    private StudbOpenHelper studbOpenHelper;
    public static final String AUTHORITY = "com.hie.student";
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(AUTHORITY,"student",1);
        matcher.addURI(AUTHORITY,"score",2);
    }

    @Override
    public boolean onCreate() {
        studbOpenHelper = new StudbOpenHelper(getContext(),"stu.db",null,1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase = studbOpenHelper.getWritableDatabase();
        switch (matcher.match(uri)) {
            case 1:
                Cursor cursor1 = sqLiteDatabase.query("student", projection, selection, selectionArgs, null, null, sortOrder);

                return cursor1;
            case 2:
                Cursor cursor2 = sqLiteDatabase.query("score", projection, selection, selectionArgs, null, null, sortOrder);

                return cursor2;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase sqLiteDatabase = studbOpenHelper.getWritableDatabase();
        switch(matcher.match(uri)){
            case 1:
                sqLiteDatabase.insert("student",null,values);
                sqLiteDatabase.close();
                break;
            case 2:
                sqLiteDatabase.insert("student",null,values);
                sqLiteDatabase.close();
                break;
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = studbOpenHelper.getWritableDatabase();
        switch(matcher.match(uri)){
            case 1:
                int rows = sqLiteDatabase.delete("student",selection,selectionArgs);
                sqLiteDatabase.close();
                return rows;
            case 2:
                int rows1= sqLiteDatabase.delete("student",selection,selectionArgs);
                sqLiteDatabase.close();
                return rows1;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = studbOpenHelper.getWritableDatabase();
        switch(matcher.match(uri)){
            case 1:
                int rows = sqLiteDatabase.update("student",values,selection,selectionArgs);
                sqLiteDatabase.close();
                return rows;
            case 2:
                int rows1= sqLiteDatabase.update("student",values,selection,selectionArgs);
                sqLiteDatabase.close();
                return rows1;
        }
        return 0;
    }
}
