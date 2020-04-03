package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Add extends AppCompatActivity {
    EditText tv;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        tv=findViewById(R.id.add_text);
        db=new DatabaseHelper(getApplicationContext());

    }

    public void add(View view) {
        String addText = tv.getText().toString();
        if(addText != null &&  !addText.equals("")){
            db.addPhrase(addText);
            finish();
        }
    }
}
