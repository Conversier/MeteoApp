package ch.supsi.dti.isin.meteoapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "MeteoAppDB.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + DbSchema.DbTable.NAME + "("
                + " _id integer primary key autoincrement, "
                + DbSchema.DbTable.Cols.UUID
                + ", "
                + DbSchema.DbTable.Cols.NAME
                + ", "
                + DbSchema.DbTable.Cols.WNAME
                + ", "
                + DbSchema.DbTable.Cols.TEMP
                + ", "
                + DbSchema.DbTable.Cols.MINTEMP
                + ", "
                + DbSchema.DbTable.Cols.MAXTEMP
                + ", "
                + DbSchema.DbTable.Cols.DESCRIPTION
                + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
