package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


public class ViewSentence extends AppCompatActivity {

    ArrayList<String> itemList;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sentence);
        db= new DatabaseHelper(getApplicationContext());

        itemList=new ArrayList<>();
        Cursor phrases = db.getPhrases();       // getting all the saved phrases
        while(phrases.moveToNext()){     // adding the phrases to the list
            itemList.add(phrases.getString(1));
        }

        ListView lv = findViewById(R.id.list_all_phrase);

        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.list_textview,R.id.text_view,itemList);     // adding the phrases to the adapter
        lv.setAdapter(adapter);     // adding the adapter to the list view
    }

    public void ok(View view) {
        finish();
    }
}
