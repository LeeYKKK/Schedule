package com.example.yk.schedule.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yk on 2017/4/18.
 */

public class ScheduleDAO extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public ScheduleDAO(Context context) {
        super(context,"database_db",null,1);
    }

    /**
     * 增加SQL语句
     * @param sqLiteDatabase
     * @return
     */
    public String insertValue(SQLiteDatabase sqLiteDatabase){
        String aaa="";
        try
        {
            String sql = "insert into Schedule values ('1-2',' ',' ',' ',' ',' ',' ',' ','08:00','2017.2.20')";
            sqLiteDatabase.execSQL(sql);
            sql = "insert into Schedule values ('3-4',' ',' ',' ',' ',' ',' ',' ','10:00','0')";
            sqLiteDatabase.execSQL(sql);
            sql = "insert into Schedule values ('5-6',' ',' ',' ',' ',' ',' ',' ','13:20','5')";
            sqLiteDatabase.execSQL(sql);
            sql = "insert into Schedule values ('7-8',' ',' ',' ',' ',' ',' ',' ','15:20',' ')";
            sqLiteDatabase.execSQL(sql);
            sql = "insert into Schedule values ('9-10',' ',' ',' ',' ',' ',' ',' ','18:00',' ')";
            sqLiteDatabase.execSQL(sql);
            sql = "insert into Schedule values ('11-12',' ',' ',' ',' ',' ',' ',' ','20:00',' ')";
            sqLiteDatabase.execSQL(sql);
        }
        catch(Exception e)
        {
            aaa=e.getMessage();
        }
        return aaa;
    }

    /**
     * 修改
     * @param week
     * @param theclass
     * @param value
     * @return
     */
    public String updateValue(String week,String theclass,String value)
    {
        String update="";
        try
        {
            db = this.getWritableDatabase();
            String sql = "UPDATE Schedule SET "+week+" = '"+value+"' WHERE Class = '"+ theclass + "'";
            db.execSQL(sql);
        }
        catch (Exception e)
        {
            update=e.getMessage();
        }
        return update;
    }

    /**
     * 查询
     * @param key
     * @param value
     * @return
     */
    public Cursor getValue(String key, String value)
    {
        db = this.getReadableDatabase();
        Cursor c = db.query("Schedule",new String[]{"Class","Mon","Tue","Wed" ,"Thu", "Fri","Sar","Sun","Time","Data"},key+" = ?",new String[]{value},null,null,
                null);
        //select * from Schedule where key = value
        return c;
    }

    /**
     * 查询
     * @param week
     * @return
     */
    public Cursor getWeekValue(String week){
        db = this.getReadableDatabase();
        Cursor c = db.query("Schedule",new String[]{week},null,null,null,null,null);
        return c;
    }

    /**
     * 创建数据库
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("OnCreate","create");
        String sql = "CREATE TABLE IF NOT EXISTS Schedule(Class text,Mon text,Tue text,Wed text,Thu text, Fri text,Sar text,Sun text,Time text,Data text);";
        sqLiteDatabase.execSQL(sql);
        insertValue(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql ="DROP TABLE IF EXISTS Schedule";
        db.execSQL(sql);
        onCreate(db);
    }

    /**
     * 关闭数据库
     */
    public void closeDB(){
        if(db!=null)
            db.close();
    }

}
