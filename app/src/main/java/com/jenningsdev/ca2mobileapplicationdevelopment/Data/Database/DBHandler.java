package com.jenningsdev.ca2mobileapplicationdevelopment.Data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.jenningsdev.ca2mobileapplicationdevelopment.Data.Model.Book;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    Context context;

    public static final String DATABASE_NAME = "BookDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Book";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_STATUS = "status";
    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_AUTHOR + " TEXT, " +
                        COLUMN_CATEGORY + " TEXT, " +
                        COLUMN_START_DATE + " TEXT, " +
                        COLUMN_STATUS + " INTEGER" +
                        ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Book book = new Book(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS)) == 1
                );
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookList;
    }

    public void addBook(String title, String author, String category, String startDate, boolean status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_START_DATE, startDate);
        cv.put(COLUMN_STATUS, status);

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public void removeBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
