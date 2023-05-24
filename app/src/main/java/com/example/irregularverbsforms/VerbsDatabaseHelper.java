package com.example.irregularverbsforms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.irregularverbsforms.Model.Verb;

import java.util.ArrayList;
import java.util.List;

public class VerbsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "verbs.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_VERBS = "verbs";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BASIC_FORM = "basic_form";
    private static final String COLUMN_PAST_SIMPLE = "past_simple";
    private static final String COLUMN_PAST_PRINCIPLE = "past_principle";

    private static final String COLUMN_CREATED_AT = "created_at";

    public VerbsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_VERBS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BASIC_FORM + " TEXT, " +
                COLUMN_PAST_SIMPLE + " TEXT, " +
                COLUMN_PAST_PRINCIPLE + " TEXT, " +
                COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VERBS);
        onCreate(db);
    }

    public void addVerb(Verb verb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BASIC_FORM, verb.getBasicForm());
        values.put(COLUMN_PAST_SIMPLE, verb.getPastSimple());
        values.put(COLUMN_PAST_PRINCIPLE, verb.getPastPrinciple());
        db.insert(TABLE_VERBS, null, values);
        db.close();
    }

    public List<Verb> getAllVerbs() {
        List<Verb> verbList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_VERBS + " ORDER BY " + COLUMN_CREATED_AT + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int basicFormIndex = cursor.getColumnIndex(COLUMN_BASIC_FORM);
                int pastSimpleIndex = cursor.getColumnIndex(COLUMN_PAST_SIMPLE);
                int pastPrincipleIndex = cursor.getColumnIndex(COLUMN_PAST_PRINCIPLE);

                int id = cursor.getInt(idIndex);
                String basicForm = basicFormIndex != -1 ? cursor.getString(basicFormIndex) : "";
                String pastSimple = pastSimpleIndex != -1 ? cursor.getString(pastSimpleIndex) : "";
                String pastPrinciple = pastPrincipleIndex != -1 ? cursor.getString(pastPrincipleIndex) : "";

                Verb verb = new Verb(id, basicForm, pastSimple, pastPrinciple);
                verbList.add(verb);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return verbList;
    }

    public void deleteVerb(Verb verb) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VERBS, COLUMN_ID + " = ?", new String[]{String.valueOf(verb.getId())});
        db.close();
    }

    public Verb getVerbById(int verbId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_VERBS, null, COLUMN_ID + " = ?", new String[]{String.valueOf(verbId)}, null, null, null);
        Verb verb = null;
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int basicFormIndex = cursor.getColumnIndex(COLUMN_BASIC_FORM);
            int pastSimpleIndex = cursor.getColumnIndex(COLUMN_PAST_SIMPLE);
            int pastPrincipleIndex = cursor.getColumnIndex(COLUMN_PAST_PRINCIPLE);

            int id = cursor.getInt(idIndex);
            String basicForm = basicFormIndex != -1 ? cursor.getString(basicFormIndex) : "";
            String pastSimple = pastSimpleIndex != -1 ? cursor.getString(pastSimpleIndex) : "";
            String pastPrinciple = pastPrincipleIndex != -1 ? cursor.getString(pastPrincipleIndex) : "";

            verb = new Verb(id, basicForm, pastSimple, pastPrinciple);
            cursor.close();
        }
        db.close();
        return verb;
    }

    public void populateDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        // Sample data
        String[][] sampleData = {
                {"be", "was/were", "been"},
                {"begin", "began", "begun"},
                {"break", "broke", "broken"},
                {"come", "came", "come"},
                {"do", "did", "done"},
                {"go", "went", "gone"},
                {"have", "had", "had"},
                {"see", "saw", "seen"},
                {"take", "took", "taken"},
                {"write", "wrote", "written"}
        };

        for (int i = 0; i < 10; i++) {
            values.put(COLUMN_BASIC_FORM, sampleData[i][0]);
            values.put(COLUMN_PAST_SIMPLE, sampleData[i][1]);
            values.put(COLUMN_PAST_PRINCIPLE, sampleData[i][2]);
            db.insert(TABLE_VERBS, null, values);
            values.clear();
        }

        db.close();
    }

}
