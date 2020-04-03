package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class Filtured extends AppCompatActivity {
    String language;
    TextView languageTopic;
    DatabaseHelper db;
    Cursor translatedData;
    ArrayList<ArrayList<String>> translatedPhrases;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtured);
        Intent intent = getIntent();
        language = intent.getStringExtra("language");
        languageTopic = findViewById(R.id.language_from_to);
        db = new DatabaseHelper(getApplicationContext());
        translatedData = db.getTranslatedPhrases(language);
        translatedPhrases= new ArrayList<ArrayList<String>>();
        int index = 0;
        while(translatedData.moveToNext()){
            translatedPhrases.get(index).add(translatedData.getString(2));
            translatedPhrases.get(index).add(translatedData.getString(3));
            index++;
        }
        lv = findViewById(R.id.saved_translations_list);
        CustomList cl = new CustomList(translatedPhrases);
        lv.setAdapter(cl);

    }

    private class CustomList extends BaseAdapter {

        private ArrayList<ArrayList<String>> translated;

        private CustomList(ArrayList<ArrayList<String>> translated){
            this.translated = translated;
        }

        @Override
        public int getCount() {
            return translated.size();
        }

        @Override
        public Object getItem(int position) {
            return translated.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, final View customView, final ViewGroup parent) {
            View view = customView;
            if (view == null){
                view = View.inflate(Filtured.this, R.layout.translated_words_card, null);
            }
            final ConstraintLayout customCard = view.findViewById(R.id.customCardTranslated);

            final TextView customTextViewLanguage = (TextView) view.findViewById(R.id.translated_lang);
            final TextView customTextViewEnglishText = (TextView) view.findViewById(R.id.englishTextTranslated);
            final TextView customTextViewTranslatedText = (TextView) view.findViewById(R.id.translated_text_translated);

            customTextViewLanguage.setText(language);
            customTextViewEnglishText.setText(translated.get(position).get(0));
            customTextViewTranslatedText.setText(translated.get(position).get(1));

            return view;
        }
    }
}
