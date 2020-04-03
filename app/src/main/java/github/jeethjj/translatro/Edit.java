//http://android-designing.blogspot.com/2018/05/1.html
package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Edit extends AppCompatActivity {

    int selectedPosition = -1;
    DatabaseHelper db;
    String edit;
    boolean clickedEditOnce = false;
    EditText et;
    CustomList cl;
    ArrayList<String> arrayList;
    Cursor phrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        db=new DatabaseHelper(getApplicationContext());
        et=findViewById(R.id.editText);

        ListView listView = findViewById(R.id.list_edit);
        phrases = db.getPhrases();
        arrayList = new ArrayList<>();
        while(phrases.moveToNext()){
            arrayList.add(phrases.getString(1));
        }
        cl = new CustomList(arrayList);
        listView.setAdapter(cl);
    }

    public void edit(View view) {
        et.setText(edit);
    }

    public void save(View view) {
        String newPhrase = et.getText().toString();
        if(!newPhrase.equals(null)  &&  !newPhrase.equals("")){
            db.updatePhrase(newPhrase,db.getID(edit));
            phrases = db.getPhrases();
            arrayList = new ArrayList<>();
            while(phrases.moveToNext()){
                arrayList.add(phrases.getString(1));
            }
            cl.notifyDataSetChanged();
        }
    }

    private class CustomList extends BaseAdapter {

        private ArrayList< String> allPhrases;

        private CustomList(ArrayList< String> allPhrases){
            this.allPhrases = allPhrases;
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

            final TextView customTextView = (TextView) view.findViewById(R.id.customTextView);
            customTextView.setText(allPhrases.get(position));

            customCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = (Integer) v.findViewById(R.id.customRadio).getTag();
                    Toast.makeText(getApplicationContext(), customTextView.getText(), Toast.LENGTH_SHORT).show();
                    edit= (String) customTextView.getText();
                    if(clickedEditOnce){
                        et.setText(edit);
                    }
                    clickedEditOnce = true;
                    notifyDataSetChanged();
                }
            });
            return view;
        }
    }
}
