package main.pb;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class DBPerson {

    public static final String TABLE_ID = "_id";
    private static final String DATABASE_NAME = "persons";
    private static final int DATABASE_VERSION = 4;
    public static final String TABLE_NAME = "person_table";
    public static final String PID = "pid";
    public static final String NAME = "name";
    public static final String SNAME = "sname";
    public static final String PNAME = "pname";
    public static final String GROUP = "ggroup";
    public static final String PHONENUMBER = "phonenumber";
    public static final String EMAIL = "email";
    public static final String COMMENT = "comment";
    public static final String UPDATE = "ddate";

    private static final String SQL_CREATE_ENTRIES = "create table "
            + TABLE_NAME + " ("
            + TABLE_ID + " integer primary key autoincrement, "
            + PID + " text not null, "
            + NAME + " text not null, "
            + SNAME + " text not null, "
            + PNAME + " text not null, "
            + GROUP + " text not null, "
            + PHONENUMBER + " text not null, "
            + EMAIL + " text not null, "
            + COMMENT + " text not null, "
            + UPDATE + " text not null ); ";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

    private final Context mcontext;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    public DBPerson(Context context) {
        this.mcontext = context;
        DBHelper = new DatabaseHelper(mcontext);
        db = DBHelper.getWritableDatabase();
    }

    public Person getPersonByPid(int pid) {
        Cursor cursor = db.query(TABLE_NAME, null, PID + " = ?", new String[]{Integer.toString(pid)}, null, null, null);
        Person curPerson = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            curPerson = new Person(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PID))), cursor.getString(cursor.getColumnIndex(NAME)), cursor.getString(cursor.getColumnIndex(SNAME)),
                    cursor.getString(cursor.getColumnIndex(PNAME)), cursor.getString(cursor.getColumnIndex(GROUP)),
                    cursor.getString(cursor.getColumnIndex(PHONENUMBER)), cursor.getString(cursor.getColumnIndex(EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COMMENT)), cursor.getString(cursor.getColumnIndex(UPDATE)));
        }
        return curPerson;
    }

    public void insert(Person person) {
        ContentValues cv = new ContentValues();
        cv.put(PID, person.getPid());
        cv.put(NAME, person.getName());
        cv.put(SNAME, person.getSName());
        cv.put(PNAME, person.getPName());
        cv.put(PHONENUMBER, person.getPhoneNumber());
        cv.put(GROUP, person.getGroup());
        cv.put(EMAIL, person.getEmail());
        cv.put(COMMENT, person.getComment());
        cv.put(UPDATE, person.getDate());
        db.insert(TABLE_NAME, null, cv);
    }

    public void delete(Person person) {
        db.delete(TABLE_NAME, PID + " = ?", new String[]{Integer.toString(person.getPid())});
    }

    public void update(Person person) {
        ContentValues cv = new ContentValues();
        cv.put(PID, person.getPid());
        cv.put(NAME, person.getName());
        cv.put(SNAME, person.getSName());
        cv.put(PNAME, person.getPName());
        cv.put(PHONENUMBER, person.getPhoneNumber());
        cv.put(GROUP, person.getGroup());
        cv.put(EMAIL, person.getEmail());
        cv.put(COMMENT, person.getComment());
        cv.put(UPDATE, person.getDate());
        db.update(TABLE_NAME, cv, "pid = ?", new String[]{Integer.toString(person.getPid())});
    }

    public ArrayList<Person> getAllPersons() {
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Person> persons = new ArrayList<Person>();
        while (cursor.moveToNext()) {
            Person curPerson = new Person(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PID))), cursor.getString(cursor.getColumnIndex(NAME)), cursor.getString(cursor.getColumnIndex(SNAME)),
                    cursor.getString(cursor.getColumnIndex(PNAME)), cursor.getString(cursor.getColumnIndex(GROUP)),
                    cursor.getString(cursor.getColumnIndex(PHONENUMBER)), cursor.getString(cursor.getColumnIndex(EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COMMENT)), cursor.getString(cursor.getColumnIndex(UPDATE)));
            persons.add(curPerson);
        }
        return persons;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }
}
