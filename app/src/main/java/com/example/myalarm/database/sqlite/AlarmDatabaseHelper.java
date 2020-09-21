package com.example.myalarm.database.sqlite;




import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDatabaseHelper extends SQLiteOpenHelper {


    /** Database file name */

    private static final String DATABASE_NAME = "Sqlitealarmdatabase.db";

    /** Database version */

    private static final int VERSION = 1;

    public AlarmDatabaseHelper (Context context)
    {
        super(context,DATABASE_NAME,null,VERSION);

    }





    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + DatabaseContract.AlarmEntry.TABLE_NAME + " ("
                + DatabaseContract.AlarmEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + DatabaseContract.AlarmEntry.COLUMNS_ALARM_OBJECT + " TEXT NOT NULL );";

        db.execSQL(SQL_CREATE_PETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

