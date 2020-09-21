package com.example.myalarm.database.sqlite;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseContract {

    private DatabaseContract(){}
    /**
     * Content Authority for the Content provider
     */
    public final static String CONTENT_AUTHORITY = "com.example.myalarm";

    /**
     * Uri for the Content Authority
     */
    public final static Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    /**
     * path for uri
     */

    public final static String PATHS = AlarmEntry.TABLE_NAME;




    public static final class AlarmEntry implements BaseColumns
    {

        /** Name of the Database */
        public  static final String TABLE_NAME = "alarmData";

        /** Names of the columns **/

        /**Unique id for the Alarm Data Entry */

        public static final String _ID = BaseColumns._ID ;



        /** Alarm json object */

        public static final String COLUMNS_ALARM_OBJECT = "alarmObject";
        /**
         * content uri for content resolver
         */

        public  final  static  Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATHS);

        /**
         *The MIME type of the multiple alarms
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATHS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single alarm.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATHS;


    }

}
