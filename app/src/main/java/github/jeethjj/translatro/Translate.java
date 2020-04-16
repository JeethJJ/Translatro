package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Translate extends AppCompatActivity {
    Spinner phrase_spin;
    Spinner lang_spin;
    DatabaseHelper db;
    ArrayList<String> langs;
    ArrayList<String> allLangs;
    ArrayList<String> itemList;
    String langToTrans;
    String phraseToTrans;
    Button translate_btn;
    Snackbar snackbar;
    String[] keys = {"ar", "bg", "zh", "zh-TW", "hr","cs", "da", "nl", "et", "fi","fr", "de", "el", "he", "hi", "hu","ga", "it", "id", "ja", "ko", "lv","lt", "ms", "nb", "pl", "pt", "ro","ru", "sk", "sl", "es", "sv", "th","tr", "ur","vi"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        phrase_spin=findViewById(R.id.phrase_spin);
        lang_spin=findViewById(R.id.lang_spin);
        translate_btn=findViewById(R.id.translate_btn);
        translate_btn.setEnabled(false);

        db=new DatabaseHelper(getApplicationContext());    //add all langs
        Cursor languages = db.getLangStatus();

        langs = new ArrayList<>();
        while(languages.moveToNext()){  // the user it is given the translate option only for the subscribe to languages
            if(languages.getInt(2)==1) {
                langs.add(languages.getString(1));
            }
        }

        Cursor languages2 = db.getLangStatus();
        allLangs = new ArrayList<>();    //
        while(languages2.moveToNext()){
            allLangs.add(languages2.getString(1));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,langs);
        lang_spin.setAdapter(adapter);  // adding the languages to the spinner

        itemList = new ArrayList<>();  //add all phrases
        Cursor phrases = db.getPhrases();
        while(phrases.moveToNext()){
            itemList.add(phrases.getString(1));
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,itemList);
        phrase_spin.setAdapter(adapter2);     // adding the phrases to the spinner

        if(!String.valueOf(lang_spin.getSelectedItem()).equals("null") && !String.valueOf(phrase_spin.getSelectedItem()).equals("null")){
            translate_btn.setEnabled(true);
        }

        if(!ConnectivityCheck.isConnected(getApplicationContext())){
            translate_btn.setEnabled(false);
            snackbar = Snackbar.make(findViewById(R.id.translate),R.string.cantTranslate,Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    public void translate(View view) {     // when the translate button is clicked, all the selected options are transferred to the next activity
        langToTrans = lang_spin.getSelectedItem().toString();
        phraseToTrans = phrase_spin.getSelectedItem().toString();
        Intent intent = new Intent(Translate.this, Translated.class);
        intent.putExtra("language", langToTrans);
        intent.putExtra("languageKEY", keys[allLangs.indexOf(langToTrans)]);
        intent.putExtra("phrase", phraseToTrans);
        startActivity(intent);
        finish();
    }
}
