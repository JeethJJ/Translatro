package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
import com.ibm.watson.language_translator.v3.util.Language;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;

import java.util.concurrent.TimeUnit;

public class Translated extends AppCompatActivity {

    private LanguageTranslator translationService;
    TextView translated_phrase;
    TextView english_phrase;
    TextView language;
    private StreamPlayer player = new StreamPlayer();
    private TextToSpeech textService;
    String lang;
    String languageKEY;
    String phrase;
    String translatedPhrase;
    Button button3;
    Snackbar snackbar;
    ProgressBar pb;  //https://www.youtube.com/watch?v=VmLXxCSxtds
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translated);
        translated_phrase = findViewById(R.id.translated_phrase);
        english_phrase = findViewById(R.id.english_phrase);
        language = findViewById(R.id.language);
        button3 = findViewById(R.id.button3);
        pb = findViewById(R.id.progressBar);
        translationService = initLanguageTranslatorService();
        textService = initTextToSpeechService();

        pb.setVisibility(View.VISIBLE);
        button3.setEnabled(false);     // the button is available only after the translation is finished
        Intent intent = getIntent();
        lang = intent.getStringExtra("language");  // getting all the stuff transferred from the previous activity
        languageKEY = intent.getStringExtra("languageKEY");
        phrase = intent.getStringExtra("phrase");

        language.setText(lang+" :");
        english_phrase.setText(phrase);


        new TranslationTask().execute(phrase);//translator

        if(!ConnectivityCheck.isConnected(getApplicationContext())){     // checking the connectivity
            button3.setEnabled(false);
            snackbar = Snackbar.make(findViewById(R.id.translated),R.string.cantPronounce,Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    public void pronounce(View view) {     // when the pronounce button is clicked
        button3.setEnabled(false);
        pb.setVisibility(View.VISIBLE);
        new SynthesisTask().execute(translatedPhrase); // sending the word to speak
    }

    //Translator
    private LanguageTranslator initLanguageTranslatorService() {
        IamAuthenticator authenticator = new IamAuthenticator(getString(R.string.language_translator_apikey));
        LanguageTranslator service = new LanguageTranslator("2018-05-01", authenticator);
        service.setServiceUrl(getString(R.string.language_translator_url));
        return service;
    }

    private class TranslationTask extends AsyncTask<String, Void, String> {     // running the translator in a different thread
        @Override     // if not the app will crash because the translator will take more time end do UI thread might crash
        protected String doInBackground(String... params) {
            String firstTranslation=null;
            try {
                TranslateOptions translateOptions = new TranslateOptions.Builder().addText(params[0]).source(Language.ENGLISH).target(languageKEY).build();
                TranslationResult result = translationService.translate(translateOptions).execute().getResult();
                firstTranslation = result.getTranslations().get(0).getTranslation();
            }catch (Exception e){
                snackbar = Snackbar.make(findViewById(R.id.translated),"Your connection is poor!! Cannot translate!",Snackbar.LENGTH_LONG);
                snackbar.show();
                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException ex) {
                }
                finish();
            }
            return firstTranslation;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            translated_phrase.setText(s);
            translatedPhrase = s;
            button3.setEnabled(true);
            pb.setVisibility(View.GONE);
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            db.addTranslatedPhrase(lang,phrase,translatedPhrase);
        }
    }

    //text to speech
    private TextToSpeech initTextToSpeechService() {
        Authenticator authenticator = new IamAuthenticator(getString(R.string.text_speech_apikey));
        TextToSpeech service = new TextToSpeech(authenticator);
        service.setServiceUrl(getString(R.string.text_speech_url));
        return service;
    }

    private class SynthesisTask extends AsyncTask<String, Void, String> {     // running a translator in a different thread
        // if not the ui thread Will crash
        @Override
        protected String doInBackground(String... params) {
            try {
                SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder().text(params[0]).voice(SynthesizeOptions.Voice.EN_US_LISAVOICE).accept(HttpMediaType.AUDIO_WAV).build();
                player.playStream(textService.synthesize(synthesizeOptions).execute().getResult());
                return "Did synthesize";
            }catch (Exception e){
                snackbar = Snackbar.make(findViewById(R.id.translated),"Your connection is poor!! Cannot pronounce!",Snackbar.LENGTH_LONG);
                snackbar.show();
                return "Didn't synthesize";
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            button3.setEnabled(true);
            pb.setVisibility(View.GONE);
        }
    }
}
