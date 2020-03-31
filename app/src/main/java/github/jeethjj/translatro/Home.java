package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void addPhrase(View view) {
        Intent intend = new Intent(Home.this, Add.class);
        startActivity(intend);
        overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
    }

    public void disPhrase(View view) {
        Intent intend = new Intent(Home.this, ViewSentence.class);
        startActivity(intend);
        overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
    }

    public void editPhrase(View view) {
        Intent intend = new Intent(Home.this, Edit.class);
        startActivity(intend);
        overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
    }

    public void langSub(View view) {
        Intent intend = new Intent(Home.this, Subscriptions.class);
        startActivity(intend);
        overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
    }

    public void translate(View view) {
        Intent intend = new Intent(Home.this, Translate.class);
        startActivity(intend);
        overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
    }

    public void savedTrans(View view) {
        Intent intend = new Intent(Home.this, Saved.class);
        startActivity(intend);
        overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
    }
}
