package com.example.cocktailandroidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "commentsdb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "comments";
    private static final String NAME_COL = "myComment";
    private static final String ID_COL = "id";
    private static final String CARD_ID_COL = "cardId";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("+ ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT , "+ CARD_ID_COL + " TEXT , "+ NAME_COL + " TEXT)";

        db.execSQL(query);
    }


    public void addNewComment(String id,String comment) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, comment);
        values.put(CARD_ID_COL, id);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<CommentsInfo> readComments() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<CommentsInfo> CommentsInfoArrayList = new ArrayList<>();
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                CommentsInfoArrayList.add(new CommentsInfo(cursorCourses.getString(1),cursorCourses.getString(2)));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return CommentsInfoArrayList;
    }

    public String searchById(String str){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM comments WHERE cardID=? ", new String[] {str});
        //Cursor cursorCourses = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        String comm = "";
        cursorCourses.moveToFirst();
        comm = cursorCourses.getString(2);
        if (comm == ""){
            return "";
        }
        cursorCourses.close();
        return comm;


    }


    public void updateNote(String originalNote, String note) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, note);


        db.update(TABLE_NAME, values, "myComment=?", new String[]{originalNote});
        db.close();
    }

}