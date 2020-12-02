package com.example.warehouseinventoryapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class W8ContentProvider extends ContentProvider {

    ItemDatabase db;
    private final String table = "items";
    public final static String CONTENT_AUTHORITY = "fit2081.app.NGUYEN_DANH_CHAU";
    public final static Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public W8ContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleteCount;
        deleteCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .delete(table, selection, selectionArgs);
        return deleteCount;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = db
                .getOpenHelper()
                .getWritableDatabase()
                .insert(table, 0, values);
        return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }

    @Override
    public boolean onCreate() {
        db = ItemDatabase.getDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(table);
        String query = builder.buildQuery(projection, selection, null,
                null, sortOrder, null);
        final Cursor cursor =
                db
                .getOpenHelper()
                .getReadableDatabase()
                .query(query, selectionArgs);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count;
        count = db
                .getOpenHelper()
                .getWritableDatabase()
                .update(table, 0, values, selection, selectionArgs);
        return count;
    }
}
