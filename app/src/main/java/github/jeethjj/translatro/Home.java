package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class Home extends AppCompatActivity {
    Button translate;
    Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        translate = findViewById(R.id.translate_btn);
        if(!ConnectivityCheck.isConnected(getApplicationContext())){     // checking the connectivity to limit the activities
            translate.setEnabled(false);
            snackbar = Snackbar.make(findViewById(R.id.home),R.string.noConn,4000);
            snackbar.show();
        }else{
            translate.setEnabled(true);
        }
    }

    @Override
    protected void onStart() {     // re-checking the connectivity
        super.onStart();
        if(!ConnectivityCheck.isConnected(getApplicationContext())){
            translate.setEnabled(false);
            snackbar = Snackbar.make(findViewById(R.id.home),R.string.noConn,4000);
            snackbar.show();
        }else{
            translate.setEnabled(true);
        }
    }

    @Override
    protected void onResume() {     // re-checking the connectivity
        super.onResume();
        if(!ConnectivityCheck.isConnected(getApplicationContext())){
            translate.setEnabled(false);
            snackbar = Snackbar.make(findViewById(R.id.home),R.string.noConn,4000);
            snackbar.show();
        }else{
            translate.setEnabled(true);
        }
    }

    // navigation to appropriate activities
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
