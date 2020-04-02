package github.jeethjj.translatro;


import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class Subscriptions extends AppCompatActivity {

    ArrayList<String> selectedItems;
    ArrayList<String> arrayList;
    ArrayList<Integer> status;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        selectedItems=new ArrayList<>();
        db= new DatabaseHelper(getApplicationContext());
        Cursor languages = db.getLangStatus();

        arrayList = new ArrayList<>();
        while(languages.moveToNext()){
            arrayList.add(languages.getString(1));
        }

        status = new ArrayList<>();
        while(languages.moveToNext()){
            status.add(languages.getInt(2));
        }

        ListView upgrade_list=  findViewById(R.id.list_upgrade);
        CustomList cl = new CustomList(arrayList);
        upgrade_list.setAdapter(cl);
    }

    public void update(View view) {
        for(String s : selectedItems) {
            db.updatelangStatus(s,1);
        }
        finish();
    }

    private class CustomList extends BaseAdapter {

        private ArrayList< String> langs;

        private CustomList(ArrayList< String> langs){
            this.langs = langs;
        }

        @Override
        public int getCount() {
            return langs.size();
        }

        @Override
        public Object getItem(int position) {
            return langs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, final View customView, final ViewGroup parent) {
            View view = customView;
            if (view == null){
                view = View.inflate(Subscriptions.this, R.layout.checkable_list, null);
            }
            final ConstraintLayout customCard = view.findViewById(R.id.customCardCheck);
            final CheckBox cb =view.findViewById(R.id.customCheck);
            final TextView customTextView = (TextView) view.findViewById(R.id.customTextViewCheck);
            customTextView.setText(langs.get(position));
            if(status.get(position) ==1){
                cb.setChecked(true);
                selectedItems.add((String) customTextView.getText());
            }
            if(selectedItems.contains(langs.get(position))){
                cb.setChecked(true);
            }
            customCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!cb.isChecked()) {
                        cb.setChecked(true);
                        selectedItems.add((String) customTextView.getText());
                        Toast.makeText(getApplicationContext(),customTextView.getText(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        cb.setChecked(false);
                        selectedItems.remove(customTextView.getText());
                    }
                }
            });
            return view;
        }
    }


}
