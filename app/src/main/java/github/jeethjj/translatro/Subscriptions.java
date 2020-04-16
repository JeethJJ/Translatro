package github.jeethjj.translatro;


import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class Subscriptions extends AppCompatActivity {

    ArrayList<String> selectedItems;
    ArrayList<String> arrayList;
    ArrayList<Integer> status;
    DatabaseHelper db;

    @Override
    protected void onSaveInstanceState(Bundle outState) {    // save the instance for orientation changes
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("selectedItems",selectedItems);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        selectedItems=new ArrayList<>();
        db= new DatabaseHelper(getApplicationContext());
        Cursor languages = db.getLangStatus();

        arrayList = new ArrayList<>(); // checking the selected language and adding to the list
        while(languages.moveToNext()){
            arrayList.add(languages.getString(1));
        }

        status = new ArrayList<>();

        if(languages.moveToFirst()) {  // iterating throughout the cursor
            do{
                status.add(languages.getInt(2));
            }while (languages.moveToNext());
        }

        int ww=0;
        for(int ss : status){
            if(ss==1){
                selectedItems.add(arrayList.get(ww));
            }
             ww++;
        }

        if(savedInstanceState!= null){  // is the orientation is changed it should start from where it stopped
            this.selectedItems = savedInstanceState.getStringArrayList("selectedItems");
        }

        ListView upgrade_list=  findViewById(R.id.list_upgrade);
        CustomList cl = new CustomList(arrayList);
        upgrade_list.setAdapter(cl);     // Setting up the list View
    }

    public void update(View view) {  // updating all the language statuses
        for(String s : arrayList) {
            db.updatelangStatus(s,0);
        }
        for(String s : selectedItems) {
            db.updatelangStatus(s,1);
        }
        finish();
    }

    private class CustomList extends BaseAdapter {  // custom adapter with a checkbox and a text View

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

            customTextView.setText(langs.get(position));     // Setting the text which is the language

            if (!selectedItems.contains(langs.get(position))) {  // checking the check box if the language is subscribed
                cb.setChecked(false);
            }
            if (selectedItems.contains(langs.get(position))) {
                cb.setChecked(true);
            }

            customCard.setOnClickListener(new View.OnClickListener() {  // every time a language is selected the list is updated
                @Override
                public void onClick(View v) {
                    if(!selectedItems.contains(customTextView.getText().toString())){
                        cb.setChecked(true);
                        selectedItems.add(customTextView.getText().toString());
                    }
                    else{
                        cb.setChecked(false);
                        selectedItems.remove(customTextView.getText().toString());
                    }
                }
            });
            return view;
        }
    }

}

