package teratroopers.companion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Prashanth on 31-08-2017.
 */
//04/09 modified

public class mydbhelper extends SQLiteOpenHelper {
    public static final String DATABSE_NAME="student.sqLiteDatabase";
    public static final String TABLE_NAME="student_table";
    public static final String COL1="classname";
    public static final String COL2="roll1";
    public static final String COL3="roll2";
    public mydbhelper(Context context) {
        super(context,DATABSE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "("+COL1+" TEXT,"+COL2+ " INTEGER,"+COL3+" INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData( String classname, int roll1, int roll2) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();

        contentvalues.put(COL1, classname);
        contentvalues.put(COL2, roll1);
        contentvalues.put(COL3, roll2);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentvalues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getalldata() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        return res;

    }
    public Cursor getcname(String cname){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor result=sqLiteDatabase.rawQuery("Select * from "+TABLE_NAME +" where classname = "+"'"+cname+"'",null);
        return result;
    }

}
