package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewSentence extends AppCompatActivity {

    ArrayList<String> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sentence);

        ListView lv = findViewById(R.id.list_all_phrase);
        String[] festivals = {
                "Diwali",
                "Holi",
                "Christmas",
                "Eid",
                "Baisakhi",
                "Diwali",
                "Holi",
                "Christmas",
                "Eid",
                "Baisakhi",
                "Diwali",
                "Holi",
                "Christmas",
                "Eid",
                "Baisakhi",
                "Diwali",
                "Holi",
                "Christmas",
                "Eid",
                "Baisakhi",
                "Halloween"
        };
        itemList=new ArrayList<String>(Arrays.asList(festivals));

//        TextView textView = findViewById(R.id.txtview);

//        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_textview, R.id.textView, festivals);

        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.list_textview,R.id.text_view,itemList);

        lv.setAdapter(adapter);

//        https://stackoverflow.com/questions/17339762/how-to-convert-settext-to-a-listview-in-android/17340026

//        https://www.studytonight.com/android/android-listview
    }
}
