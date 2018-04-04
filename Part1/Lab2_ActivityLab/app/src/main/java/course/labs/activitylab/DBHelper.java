package course.labs.activitylab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by breck on 11/19/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "passwords_db.db";
    public static final String TABLE_NAME = "passwords";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_COMPANY = "company";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_NAME  +"(" + COLUMN_COMPANY + "text primary key," + COLUMN_PASSWORD + "text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean inezrtPassword(String company, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("id", id);
        contentValues.put("company", company);
        contentValues.put("password", password);
        db.insert("passwords_db", null, contentValues);
        if (contentValues != null) {
            return true;
        } else return false;
    }
    public boolean updatePassword ( String company, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("id", id);
        contentValues.put("password", password);
        contentValues.put("company", company);
        db.update("passwords_db", contentValues, "company = ? ", new String[] { company } );
        if (contentValues!= null){
            return true;
        }
        else return false;
    }
    public Cursor getData(String company){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from passwords_db where company=?" +company +"",new String[] {company});
        return result;
    }

    public Integer deletePassword(String company){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("passwords_db", "company=? ",new String[]{company});

    }
}
