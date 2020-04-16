package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class Add extends AppCompatActivity {
    EditText tv;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        tv=findViewById(R.id.add_text);
        db=new DatabaseHelper(getApplicationContext()); // creating the database helper object

    }

    public void add(View view) {     // function when the user clicks the add button
        String addText = tv.getText().toString();
        if(addText != null &&  !addText.equals("")){  // phrase will get added to the database only if it doesn't exist or is it not null
            db.addPhrase(addText);
            finish();
        }else{
            Snackbar snackbar = Snackbar.make(findViewById(R.id.add),"The phrase cannot be empty!!",Snackbar.LENGTH_LONG);     // alert if phrase is empty
            snackbar.show();
        }
    }
}
