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

public class Saved extends AppCompatActivity {
    Spinner sp;
    DatabaseHelper db;
    ArrayList<String> langs;
    String langToFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        sp= findViewById(R.id.select_lang_saved_spin);
        db=new DatabaseHelper(getApplicationContext());
        Cursor languages = db.getLangStatus();     // getting all the saved phrases

        langs = new ArrayList<>();      // adding the phrases to the array list
        while(languages.moveToNext()){
            if(languages.getInt(2)==1) {
                langs.add(languages.getString(1));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,langs);
        sp.setAdapter(adapter);     // viewing all the phrases through the adapter in the List View
        if(langs.size()==0){
            Button get = findViewById(R.id.get_saved);
            get.setEnabled(false);
        }
    }

    public void get(View view) {     // when the button is clicked select the language selected by the user and pass whoa it to the next activity
        langToFilter = sp.getSelectedItem().toString();
        Cursor translatedData = db.getTranslatedPhrases(langToFilter);
        if(translatedData.getCount()>0) {
            Intent intent = new Intent(Saved.this, Filtured.class);
            intent.putExtra("language", langToFilter);
            startActivity(intent);
            finish();
        }
        else{
            Snackbar snackbar = Snackbar.make(findViewById(R.id.saved),"No translations saved for "+langToFilter+" yet!!",Snackbar.LENGTH_LONG);
            snackbar.show();     // viewing a message if language is empty
        }
    }
}
