package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
        Cursor languages = db.getLangStatus();

        langs = new ArrayList<>();
        while(languages.moveToNext()){
            if(languages.getInt(2)==1) {
                langs.add(languages.getString(1));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,langs);
        sp.setAdapter(adapter);
    }

    public void get(View view) {
        langToFilter = sp.getSelectedItem().toString();
        Intent intent = new Intent(Saved.this, Filtured.class);
        intent.putExtra("language", langToFilter);
        startActivity(intent);
        finish();
    }
}
