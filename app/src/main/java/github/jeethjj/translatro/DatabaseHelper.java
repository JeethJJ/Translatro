package github.jeethjj.translatro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_1 + " ("+COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 +" TEXT )";
        String createTable2 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_2 + " ("+COL3+" INTEGER PRIMARY KEY AUTOINCREMENT, " + COL4 +" TEXT UNIQUE, " + COL5 +" INTEGER )";
        String createTable3 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_3 + " ("+COL6+" INTEGER PRIMARY KEY AUTOINCREMENT, " + COL7 +" TEXT , " + COL8 +" TEXT , "+ COL9 +" TEXT )";
        db.execSQL(createTable);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        addAllLangsIfNotExist();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addPhrase(String phrase) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("phrase",phrase);
        long result = sqldb.insert(TABLE_NAME_1, null, cv);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getPhrases(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_1;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getID(String phrase){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME_1 +
                " WHERE " + COL2 + " = '" + phrase + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updatePhrase(String newPhrase, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_1 + " SET " + COL2 +
                " = '" + newPhrase + "' WHERE " + COL1 + " = '" + id + "'";
        db.execSQL(query);
    }

    public void deleteName(int id, String phrase){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME_1 + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + phrase + "'";
        db.execSQL(query);
    }

    public boolean addTranslatedPhrase(String lang, String eng_text, String trans_text) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("lang",lang);
        cv.put("eng_text",eng_text);
        cv.put("trans_text",trans_text);
        long result = sqldb.insert(TABLE_NAME_3, null, cv);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getTranslatedPhrases(String lang){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_3+
                " WHERE " + COL7 + " = '" + lang + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void addAllLangsIfNotExist(){
        String[] langs={" Afrikaans ", " Albanian "," Arabic ", " Armenian "," Azerbaijani ", " Bashkir "," Basque ", " Belarusian "," Bengali ", " Bosnian "," Bulgarian ", " Catalan "," Central Khmer ", " Chinese (Simplified) "," Chinese (Traditional) ", " Chuvash "," Croatian ", " Czech "," Danish ", " Dutch "," English ", " Esperanto "," Estonian ", " Finnish "," French ", " Georgian "," German ", " Greek "," Gujarati ", " Haitian "," Hebrew ", " Hindi "," Hungarian ", " Icelandic "," Indonesian ", " Irish "," Italian ", " Japanese "," Kazakh ", " Kirghiz "," Korean ", " Kurdish "," Latvian ", " Lithuanian "," Malay ", " Malayalam "," Maltese ", " Mongolian "," Norwegian Bokmal ", " Norwegian Nynorsk "," Panjabi ", " Persian "," Polish ", " Portuguese "," Pushto ", " Romanian "," Russian ", " Serbian "," Slovakian ", " Slovenian "," Somali ", " Spanish "," Swedish ", " Tamil "," Telugu ", " Thai "," Turkish ", " Ukrainian "," Urdu ", " Vietnamese "};
        String[] keys={" af ", " sq "," ar ", " hy "," az ", " ba "," eu ", " be "," bn ", " bs "," bg ", " ca "," km ", " zh "," zh-TW ", " cv "," hr ", " cs "," da ", " nl "," en ", " eo "," et ", " fi "," fr ", " ka "," de ", " el "," gu ", " ht "," he ", " hi "," hu ", " is "," id ", " ga "," it ", " ja "," kk ", " ky "," ko ", " ku "," lv ", " lt "," ms ", " ml "," mt ", " mn "," nb ", " nn "," pa ", " fa "," pl ", " pt "," ps ", " ro "," ru ", " sr "," sk ", " sl "," so ", " es "," sv ", " ta "," te ", " th "," tr ", " uk "," ur ", " vi "};
        for (String s :langs){
            SQLiteDatabase sqldb = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("langusge",s);
            cv.put("lang_status",0);
            sqldb.insert(TABLE_NAME_2, null, cv);
        }
    }

    public Cursor getLangStatus(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_2;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updatelangStatus(String lang, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_2 + " SET " + COL5 +
                " = '" + status + "' WHERE " + COL4 + " = '" + lang + "'";
        db.execSQL(query);
    }




}
