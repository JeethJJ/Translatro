//http://android-designing.blogspot.com/2018/05/1.html
package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Edit extends AppCompatActivity {

    public static ArrayList< String> allPhrases;
    int selectedPosition = -1;
    DatabaseHelper db;
    String edit="";
    boolean clickedEditOnce = false;
    EditText et;
    CustomList cl;
    ArrayList<String> arrayList;
    Cursor phrases;
    Button save;
    ListView listView;

    @Override
    protected void onSaveInstanceState(Bundle outState) {    // save the instance for orientation changes
        super.onSaveInstanceState(outState);
        outState.putString("edit",edit);
        outState.putBoolean("clickedEditOnce",clickedEditOnce);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        db=new DatabaseHelper(getApplicationContext());
        et=findViewById(R.id.editText);
        save =findViewById(R.id.button2);
        if(savedInstanceState!= null){  // is the orientation is changed it should start from where it stopped
            this.edit = savedInstanceState.getString("edit");
            this.clickedEditOnce = savedInstanceState.getBoolean("clickedEditOnce");
        }

        if(!clickedEditOnce) {     // To track whether the edit button is already clicked
            save.setEnabled(false);
            et.setEnabled(false);
        }

        listView = findViewById(R.id.list_edit);
        phrases = db.getPhrases();
        arrayList = new ArrayList<>();
        while(phrases.moveToNext()){    // adding all the data that return from the database to a list
            arrayList.add(phrases.getString(1));
        }
        cl = new CustomList();
        allPhrases=arrayList;
        listView.setAdapter(cl);
    }

    public void edit(View view) {
        clickedEditOnce = true;
        et.setEnabled(true);
        et.setText(edit);
        save.setEnabled(true);
    }

    public void save(View view) {  // when the save button is clicked the Field Will get updated if there is a change
        String newPhrase = et.getText().toString();
        if(!newPhrase.equals(null)  &&  !newPhrase.equals("") &&  !newPhrase.equals(edit) ){
            int i = arrayList.indexOf(edit);
            db.updatePhrase(newPhrase,i+1);
            arrayList.set(i, newPhrase);
            allPhrases.set(i, newPhrase);

            et.getText().clear();
            save.setEnabled(false);
            et.setEnabled(false);
            cl.notifyDataSetChanged();
            clickedEditOnce = false;
        }else{
            Snackbar snackbar = Snackbar.make(findViewById(R.id.edit),"The phrase cannot be empty or the same as before!!",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private class CustomList extends BaseAdapter {     // a custom adapter with the text wheel and a radio button



        private CustomList(){
        }

        @Override
        public int getCount() {
            return allPhrases.size();
        }

        @Override
        public Object getItem(int position) {
            return allPhrases.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, final View customView, final ViewGroup parent) {
            View view = customView;
            if (view == null){
                view = View.inflate(Edit.this, R.layout.custom_list_radio_text, null);
            }
            final ConstraintLayout customCard = view.findViewById(R.id.customCard);
            final RadioButton radioButton =view.findViewById(R.id.customRadio);
            radioButton.setChecked(position == selectedPosition);
            radioButton.setTag(position);

            final TextView customTextView = view.findViewById(R.id.customTextView);
            customTextView.setText(allPhrases.get(position));  // adding the text

            if(edit.equals(customTextView.getText())){
                radioButton.setChecked(true);
            }

            customCard.setOnClickListener(new View.OnClickListener() {     // whenever you select option the text will be selected
                @Override
                public void onClick(View v) {
                    selectedPosition = (Integer) v.findViewById(R.id.customRadio).getTag();
                    edit= (String) customTextView.getText();
                    if(clickedEditOnce){
                        et.setText(edit);
                    }
                    notifyDataSetChanged();
                }
            });
            return view;
        }
    }
}
