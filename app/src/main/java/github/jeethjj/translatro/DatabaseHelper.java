package github.jeethjj.translatro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME_1 = "phrases";
    private static final String COL1 = "phrase_id";
    private static final String COL2 = "phrase";
    private static final String TABLE_NAME_2 = "languages";
    private static final String COL3 = "lang_id";
    private static final String COL4 = "langusge";
    private static final String COL5 = "lang_status";
    private static final String TABLE_NAME_3 = "translated";
    private static final String COL6 = "translated_id";
    private static final String COL7 = "lang";
    private static final String COL8 = "eng_text";
    private static final String COL9 = "trans_text";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Translatro", null, 1);
    }
    SQLiteDatabase db= this.getWritableDatabase();

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        String createTable1 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_1 + " ("+COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 +" TEXT UNIQUE)";
        String createTable2 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_2 + " ("+COL3+" INTEGER PRIMARY KEY AUTOINCREMENT, " + COL4 +" TEXT UNIQUE, " + COL5 +" INTEGER )";
        String createTable3 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_3 + " ("+COL6+" INTEGER PRIMARY KEY AUTOINCREMENT, " + COL7 +" TEXT , " + COL8 +" TEXT , "+ COL9 +" TEXT )";
        db.execSQL(createTable1);     // execute quarries to create the database tables if they do not exist
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        addAllLangsIfNotExist(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addPhrase(String phrase) {
//        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("phrase",phrase);
        long result = db.insert(TABLE_NAME_1, null, cv);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getPhrases(){      // to get all the saved phrases
//        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_1 +" ORDER BY "+COL2+" ASC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

//    public int getID(String phrase){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME_1 +
//                " WHERE " + COL2 + " = '" + phrase + "'";
//        Cursor data = db.rawQuery(query, null);
//        data.moveToFirst();
//        return data.getInt(0);
//    }

    public void updatePhrase(String newPhrase, int id){     // to edit the phrase
//        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_1 + " SET " + COL2 +
                " = '" + newPhrase + "' WHERE " + COL1 + " = '" + id + "'";
        db.execSQL(query);
    }

    public void deleteName(int id, String phrase){  // this will delete the phrase from the database
//        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME_1 + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + phrase + "'";
        db.execSQL(query);
    }

    public void addTranslatedPhrase(String lang, String eng_text, String trans_text) {    // saving the translated phrase please in the database
//        SQLiteDatabase sqldb = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME_3+
                " WHERE " + COL9 + " = '" + trans_text + "'";
        Cursor data = db.rawQuery(query, null);
        if(data.getCount()==0){
            ContentValues cv = new ContentValues();
            cv.put("lang",lang);
            cv.put("eng_text",eng_text);
            cv.put("trans_text",trans_text);
            long result =  db.insert(TABLE_NAME_3, null, cv);
        }
    }

    public Cursor getTranslatedPhrases(String lang){  // get the translated phrase depending on the language
//        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_3+
                " WHERE " + COL7 + " = '" + lang + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void addAllLangsIfNotExist(SQLiteDatabase db){   // add all the languages if they do not exist
        Cursor c = getLangStatus();
        if (c.getCount()==0) {
            String[] langs = {"Arabic", "Bulgarian", "Chinese (Simplified)", "Chinese (Traditional)", "Croatian", "Czech", "Danish", "Dutch", "Estonian", "Finnish", "French", "German", "Greek", "Hebrew", "Hindi", "Hungarian", "Irish", "Italian", "Indonesian", "Japanese", "Korean" , "Latvian", "Lithuanian", "Malay", "Norwegian Bokmal", "Polish", "Portuguese", "Romanian", "Russian", "Slovak", "Slovenian", "Spanish", "Swedish",  "Thai", "Turkish", "Urdu", "Vietnamese"};
            for (String s : langs) {
                ContentValues cv = new ContentValues();
                cv.put("langusge", s);
                cv.put("lang_status", 0);
                db.insert(TABLE_NAME_2, null, cv);
            }
        }
    }

    public Cursor getLangStatus(){     // get all the subscribed languages
//        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_2;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updatelangStatus(String lang, int status){     // update language subscriptions
//        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_2 + " SET " + COL5 +
                " = '" + status + "' WHERE " + COL4 + " = '" + lang + "'";
        db.execSQL(query);
    }




}
