package github.jeethjj.translatro;


import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
    private static ArrayList<String> added;
    ArrayList<String> arrayList;
    ArrayList<Integer> status;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        selectedItems=new ArrayList<>();
        added=new ArrayList<>();
        db= new DatabaseHelper(getApplicationContext());
        Cursor languages = db.getLangStatus();

        arrayList = new ArrayList<>();
        while(languages.moveToNext()){
            arrayList.add(languages.getString(1));
        }

        status = new ArrayList<>();

        if(languages.moveToFirst()) {
            do{
                status.add(languages.getInt(2));
                Log.i("status", String.valueOf(languages.getInt(2)));
            }while (languages.moveToNext());
        }

        int ww=0;
        for(int ss : status){
            if(ss==1){
                selectedItems.add(arrayList.get(ww));
            }
             ww++;
        }



        ListView upgrade_list=  findViewById(R.id.list_upgrade);
        CustomList cl = new CustomList(arrayList);
        upgrade_list.setAdapter(cl);
    }

    public void update(View view) {
        for(String s : arrayList) {
            db.updatelangStatus(s,0);
        }
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
//            Log.i("position", String.valueOf(position));
            View view = customView;
            if (view == null){
                view = View.inflate(Subscriptions.this, R.layout.checkable_list, null);
            }
            final ConstraintLayout customCard = view.findViewById(R.id.customCardCheck);
            final CheckBox cb =view.findViewById(R.id.customCheck);
            final TextView customTextView = (TextView) view.findViewById(R.id.customTextViewCheck);

//            Log.i("position", String.valueOf(position));
//            Log.i("setText",customTextView.getText().toString());
            customTextView.setText(langs.get(position));
//            Log.i("setText",customTextView.getText().toString());
//            checkAndUpdate(cb,langs.get(position));
//            Log.i("setText",customTextView.getText().toString());
            if (!selectedItems.contains(langs.get(position))) {
                cb.setChecked(false);
            }
            if (selectedItems.contains(langs.get(position))) {
                cb.setChecked(true);
            }

//            Log.i("positionText", String.valueOf(langs.get(position)));


//            for(String item : selectedItems){
//                if (item.equals(customTextView.getText().toString())){
//                    cb.setChecked(true);
//                }
//            }
//            if(selectedItems.contains(customTextView.getText().toString())){
//                cb.setChecked(true);
//            }
            customCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!selectedItems.contains(customTextView.getText().toString())){
                        cb.setChecked(true);
                        selectedItems.add(customTextView.getText().toString());
                        Toast.makeText(getApplicationContext(),customTextView.getText(), Toast.LENGTH_SHORT).show();
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

    private void checkAndUpdate(CheckBox cb, String s) {
        if(!added.contains(s)) {
            if (selectedItems.contains(s)) {
                cb.setChecked(true);
            }
            added.add(s);
        }
        if (!selectedItems.contains(s)) {
            cb.setChecked(false);
        }
    }


}

