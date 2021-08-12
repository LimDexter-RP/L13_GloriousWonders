package sg.edu.rp.c346.id20039529.gloriouswonders;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sights.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SIGHTS = "sights";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_COUNTRY = "country";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE Song
        // (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,
        // singers TEXT, stars INTEGER, year INTEGER );
        String createSongTableSql = "CREATE TABLE " + TABLE_SIGHTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_COUNTRY + " INTEGER, "
                + COLUMN_STARS + " INTEGER )";
        db.execSQL(createSongTableSql);
        Log.i("info", createSongTableSql + "\ncreated tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGHTS);
        onCreate(db);
    }

    public long insertItem(String a, String b, String c, int stars) {
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, a);
        values.put(COLUMN_DESCRIPTION, b);
        values.put(COLUMN_COUNTRY, c);
        values.put(COLUMN_STARS, stars);
        // Insert the row into the TABLE_SONG
        long result = db.insert(TABLE_SIGHTS, null, values);
        // Close the database connection
        db.close();
        Log.d("SQL Insert", "" + result);
        return result;
    }

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> itemslist = new ArrayList<Item>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + "," + COLUMN_DESCRIPTION + ","
                + COLUMN_COUNTRY + ","
                + COLUMN_STARS + " FROM " + TABLE_SIGHTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String a = cursor.getString(1);
                String b = cursor.getString(2);
                String c = cursor.getString(3);
                int stars = cursor.getInt(4);

                Item newItem = new Item(id, a, b, c, stars);
                itemslist.add(newItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemslist;
    }
    public int updateItem(Item data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_COUNTRY, data.getCountry());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_SIGHTS, values, condition, args);
        db.close();
        return result;
    }
    public int deleteItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SIGHTS, condition, args);
        db.close();
        return result;
    }
    public ArrayList<Item> getAllSongsByStars(int starsFilter) {
        ArrayList<Item> itemlist = new ArrayList<Item>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_COUNTRY, COLUMN_STARS};
        String condition = COLUMN_STARS + ">= ?";
        String[] args = {String.valueOf(starsFilter)};

        //String selectQuery = "SELECT " + COLUMN_ID + ","
        //            + COLUMN_TITLE + ","
        //            + COLUMN_SINGERS + ","
        //            + COLUMN_YEAR + ","
        //            + COLUMN_STARS
        //            + " FROM " + TABLE_SONG;

        Cursor cursor;
        cursor = db.query(TABLE_SIGHTS, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String a = cursor.getString(1);
                String b = cursor.getString(2);
                String c = cursor.getString(3);
                int stars = cursor.getInt(4);

                Item newItem = new Item(id, a, b, c, stars);
                itemlist.add(newItem);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return itemlist;
    }
}
