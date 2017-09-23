package teratroopers.companion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Prashanth on 31-08-2017.
 */
//23/09 modified (90% finished)

public class mydbhelper extends SQLiteOpenHelper {
    public static final String DATABSE_NAME="student.sqLiteDatabase";
    public static  String TABLE_NAME;
    public static final String cTABLE_NAME="cTABLE";
    public static final String COL1="rollnos";
    public static final String COL2="studnames";
    public static final String CTCOL1="classname";
    boolean k=false;
    boolean p;


    public mydbhelper(Context context) {
        super(context,DATABSE_NAME, null, 1);
    }
    public SQLiteDatabase sqLiteDatabase;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase=sqLiteDatabase;
        Log.i("info",sqLiteDatabase.toString());
        sqLiteDatabase.execSQL("create table "+cTABLE_NAME+"("+CTCOL1+" TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData( int sr, int er) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        for(int i=sr;i<=er;i++) {
            contentvalues.put(COL1, i);
            long result = sqLiteDatabase.insert(TABLE_NAME, null, contentvalues);
            if (result == -1) {
                k= false;
            } else {
                k= true;
            }
        }
        return k;
    }
    public Cursor getalldata() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from " + cTABLE_NAME, null);
        return res;

    }
    public Cursor getcname(String cname){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor result=sqLiteDatabase.rawQuery("Select "+COL1+" from "+cname,null);
        return result;
    }

    public boolean dbname(String cname, int sr, int er){   //test.java activity (class add)
        ContentValues contentvalues = new ContentValues();
        sqLiteDatabase=this.getWritableDatabase();
        TABLE_NAME=cname;
        Log.i("tname",TABLE_NAME);

        sqLiteDatabase.execSQL("create table if not exists "+TABLE_NAME+"("+COL1+" INTEGER,"+COL2+ " TEXT);");
        boolean c=checkclassname();
        if(c==true) {
            contentvalues.put(CTCOL1,TABLE_NAME);
            sqLiteDatabase.insert(cTABLE_NAME, null, contentvalues);

            Log.i("class table insertion:", "success");
            k = insertData(sr, er);
            Log.i("our table data:", "success");
            return k;
        }
        else return false;
    }

    public boolean checkclassname(){ //checks class name for existence

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Log.i("came to checkclassname","success");
        Cursor result = sqLiteDatabase.rawQuery("Select count(*) from " + cTABLE_NAME + " where " + CTCOL1 + "=" + "'"+TABLE_NAME+"'", null);
        result.moveToNext();
        int k=Integer.parseInt(result.getString(0));
        Log.i("value of k:",String.valueOf(k));
        if(k==0)
            return true;
        else
            return false;
    }

    public void deleteclass(String classname){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(classname,null,null);
        sqLiteDatabase.delete(cTABLE_NAME,CTCOL1+"="+"'"+classname+"'",null);
    }

    public void alterTable(String date,String cname){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        boolean k=isFieldExist(cname,"DATE");
        if(k) {
            try{
                sqLiteDatabase.execSQL("alter table " + cname + " add "+date+" INTEGER");
                p=true;
            }
            catch (Exception e){
                p=false;
            }
        }
        else {
            p=false;
        }
    }

    public Cursor retrievedatatodisplayattendance(String date,String classname){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("Select "+COL1+"," + date +" from " + classname,null);
        return result;
    }

    public void registerData(String date,String cname,int droll,int i){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        if(p==true) {
            sqLiteDatabase.execSQL("UPDATE " + cname + " SET "+date+" = "+i+ " WHERE " + COL1 + " = " + droll);
            Log.i("update complete:", "success");
        }
        else{
            sqLiteDatabase.execSQL("UPDATE " + cname + " SET "+date+" = "+date+ " + "+i + " WHERE " + COL1 + " = " + droll);
        }

    }

    public boolean isFieldExist(String tableName, String fieldName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("PRAGMA table_info("+tableName+")",null);
        int i= res.getColumnIndex(fieldName);

        if(i == -1)
        {
            return true;
        }
       else return false;
    }


}
