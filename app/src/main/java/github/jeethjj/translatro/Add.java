package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Add extends AppCompatActivity {
    TextView tv;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        tv=findViewById(R.id.add_text);
        db=new DatabaseHelper(getApplicationContext());

    }

    public void add(View view) {
        String addText = (String) tv.getText();
        if(!addText.equals(null)  &&  !addText.equals("")){
            db.addPhrase(addText);
            finish();
        }
    }
}
