package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

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

public class Translated extends AppCompatActivity {

    private LanguageTranslator translationService;
    TextView translated_phrase;
    private StreamPlayer player = new StreamPlayer();
    private TextToSpeech textService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translated);
        translated_phrase = findViewById(R.id.translated_phrase);
        translationService = initLanguageTranslatorService();
        new TranslationTask().execute("Hello World and my friend");//translator
        new SynthesisTask().execute("Good morning. How is quarantine?"); //speaker
    }


}
