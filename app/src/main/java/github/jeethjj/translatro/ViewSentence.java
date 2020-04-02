package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
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
        Cursor phrases = db.getPhrases();
        while(phrases.moveToNext()){
            itemList.add(phrases.getString(1));
        }

        ListView lv = findViewById(R.id.list_all_phrase);


//        TextView textView = findViewById(R.id.txtview);

//        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_textview, R.id.textView, festivals);

        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.list_textview,R.id.text_view,itemList);
        lv.setAdapter(adapter);

//        https://stackoverflow.com/questions/17339762/how-to-convert-settext-to-a-listview-in-android/17340026

//        https://www.studytonight.com/android/android-listview
    }
}
