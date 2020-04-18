package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Filtured extends AppCompatActivity {
    String language;
    TextView languageTopic;
    DatabaseHelper db;
    Cursor translatedData;
    ArrayList<ArrayList<String>> translatedPhrases;
    ListView lv;
    private StreamPlayer player = new StreamPlayer();
    private TextToSpeech textService;
    ProgressBar pb;
    Button pron_phrase;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtured);
        Intent intent = getIntent();
        language = intent.getStringExtra("language");   // get the language
        languageTopic = findViewById(R.id.language_from_to);
        languageTopic.setText("English to "+language);     // Setting the heading
        textService = initTextToSpeechService();
        pb = findViewById(R.id.progressBar2);
        pb.setVisibility(View.GONE);
        db = new DatabaseHelper(getApplicationContext());
        translatedData = db.getTranslatedPhrases(language);
        translatedPhrases= new ArrayList<ArrayList<String>>();       // adding all the translated phrases to the array
        if(translatedData.moveToFirst()) {
            do{
                ArrayList<String> temp= new ArrayList<>();
                temp.add(translatedData.getString(2));
                temp.add(translatedData.getString(3));
                translatedPhrases.add(temp);
            }while (translatedData.moveToNext());
        }
        lv = findViewById(R.id.saved_translations_list);
        CustomList cl = new CustomList(translatedPhrases);
        lv.setAdapter(cl);     // Setting the adapt it to the list for you



    }

    private class CustomList extends BaseAdapter {  // custom adapter to view the translated phrase with the pronounce button

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
            pron_phrase = view.findViewById(R.id.pron_phrase);

            if(!ConnectivityCheck.isConnected(getApplicationContext())){   // and the pronounce option is only available if connected to the Internet
                pron_phrase.setEnabled(false);
                Snackbar snackbar = Snackbar.make(findViewById(R.id.translated),R.string.cantPronounce,Snackbar.LENGTH_LONG);
                snackbar.show();
            }

            customTextViewLanguage.setText(language);
            customTextViewEnglishText.setText(translated.get(position).get(0));
            customTextViewTranslatedText.setText(translated.get(position).get(1));

            pron_phrase.setOnClickListener(new View.OnClickListener() {     // to pronounce the phrase
                @Override
                public void onClick(View v) {
                    String pron = customTextViewTranslatedText.getText().toString();
                    pb.setVisibility(View.VISIBLE);
                    lv.setEnabled(false);
                    new SynthesisTask().execute(pron); //speaker
                }
            });

            return view;
        }
    }

    private TextToSpeech initTextToSpeechService() {
        Authenticator authenticator = new IamAuthenticator(getString(R.string.text_speech_apikey));
        TextToSpeech service = new TextToSpeech(authenticator);
        service.setServiceUrl(getString(R.string.text_speech_url));
        return service;
    }

    private class SynthesisTask extends AsyncTask<String, Void, String> {     // running the text to speech in a new thread
        @Override
        protected String doInBackground(String... params) {
            try {
                SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder().text(params[0]).voice(SynthesizeOptions.Voice.EN_US_LISAVOICE).accept(HttpMediaType.AUDIO_WAV).build();
                player.playStream(textService.synthesize(synthesizeOptions).execute().getResult());
                return "Did synthesize";
            }catch (Exception e){
                snackbar = Snackbar.make(findViewById(R.id.filtured),"Your connection is poor!! Cannot pronounce!",Snackbar.LENGTH_LONG);
                snackbar.show();
                return "Didn't synthesize";
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pb.setVisibility(View.GONE);
            lv.setEnabled(true);
        }
    }
}
