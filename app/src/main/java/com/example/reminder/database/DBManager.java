package com.example.reminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

public class DBManager {
    //建立SQLite表头
    private SQLiteDatabase sqlDB;
    static final String DBname = "ReMinder";
    static final String TableName = "Notes";
    public static final String ColDateTime = "DateTime";
    public static final String ColTitle = "Title";
    public static final String ColDescription = "Description";
    public static final String ColRemTime = "Time";
    public static final String ColRemDate = "Date";
    public static final String ColPri = "Priority";
    public static final String ColID = "ID";
    static final int DBVersion = 1;
    static final String CreateTable = "Create table IF NOT EXISTS " + TableName + "(ID integer primary key autoincrement,"+ ColDateTime +
            " text," + ColTitle + " text," + ColDescription + " text," + ColPri + " text," + ColRemTime + " text," + ColRemDate + " text);";

    static class DatabaseHelperUser extends SQLiteOpenHelper{

        Context context;

        DatabaseHelperUser(Context context){
            super(context, DBname,null, DBVersion);
            this.context = context;
        }
        // 增加
        @Override
        public void onCreate(SQLiteDatabase db) {

            Toast.makeText(context, "ReMinder", Toast.LENGTH_LONG).show();
            db.execSQL(CreateTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop table IF EXISTS " + TableName);
            onCreate(db);
        }
    }

    public DBManager(Context context){
        DatabaseHelperUser db = new DatabaseHelperUser(context);
        sqlDB = db.getWritableDatabase();
    }

    public long Insert(ContentValues values){
        long ID = sqlDB.insert(TableName,"",values);
        return ID;
    }

    // 使用Cursor来处理表形式的数据
    // Projection no. means no. of columns, Selection is selecting a specific column
    public Cursor query(String[] Projection, String Selection, String[] SelectionArgs, String SortOrder){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TableName);

        Cursor cursor = qb.query(sqlDB, Projection, Selection, SelectionArgs,null, null, SortOrder);
        return cursor;
    }
    //删除
    public int Delete(String Selection, String[] SelectionArgs){
        int count = sqlDB.delete(TableName, Selection, SelectionArgs);
        return count;
    }
    //更新
    public int Update(ContentValues values, String Selection, String[] SelectionArgs){
        int count = sqlDB.update(TableName, values, Selection, SelectionArgs);
        return count;
    }
    //统计
    public long RowCount(){
        long count = DatabaseUtils.queryNumEntries(sqlDB, TableName);
        return count;
    }
}
