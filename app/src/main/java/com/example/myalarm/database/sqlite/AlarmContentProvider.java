package com.example.myalarm.database.sqlite;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AlarmContentProvider extends ContentProvider {

    private final static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ALARMS = 100;
    private static final int AlARM_ID = 101;
    private static final String LOG_TAG = AlarmContentProvider.class.getSimpleName();

    AlarmDatabaseHelper alarmDatabaseHelper;

    static {
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.PATHS, ALARMS);
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.PATHS + "/#", AlARM_ID);
    }

    @Override
    public boolean onCreate() {
        alarmDatabaseHelper = new AlarmDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        SQLiteDatabase db = alarmDatabaseHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        switch (match) {
            case ALARMS:
                cursor = db.query(DatabaseContract.AlarmEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case AlARM_ID:
                selection = DatabaseContract.AlarmEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(DatabaseContract.AlarmEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("cannot Query unkonwn Uri " + uri);

        }
        //cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ALARMS:
                return DatabaseContract.AlarmEntry.CONTENT_LIST_TYPE;
            case AlARM_ID:
                return DatabaseContract.AlarmEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = alarmDatabaseHelper.getWritableDatabase();
        long id = db.insert(DatabaseContract.AlarmEntry.TABLE_NAME, null, values);
        //getContext().getContentResolver().notifyChange(uri, null);
        if (id == -1) {
            Log.i(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, id);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = alarmDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
       // getContext().getContentResolver().notifyChange(uri, null);

        switch (match) {
            case ALARMS:
                // Delete all rows that match the selection and selection args
                return db.delete(DatabaseContract.AlarmEntry.TABLE_NAME, selection, selectionArgs);
            case AlARM_ID:
                // Delete a single row given by the ID in the URI
                selection = DatabaseContract.AlarmEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.delete(DatabaseContract.AlarmEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);

        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase db = alarmDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
      //  getContext().getContentResolver().notifyChange(uri, null);

        switch (match) {
            case ALARMS:
                return db.update(DatabaseContract.AlarmEntry.TABLE_NAME, values, selection, selectionArgs);
            case AlARM_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = DatabaseContract.AlarmEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.update(DatabaseContract.AlarmEntry.TABLE_NAME, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }
}
