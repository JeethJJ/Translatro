//http://android-designing.blogspot.com/2018/05/1.html
package github.jeethjj.translatro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Edit extends AppCompatActivity {

    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ListView listView = findViewById(R.id.list_edit);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0 ; i < 10; i++){
            arrayList.add("Item : " + (i));
        }

        CustomList cl = new CustomList(arrayList);
        listView.setAdapter(cl);
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
            customTextView.setText("Position Position Position Position Position Position Position Position");

            customCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = (Integer) v.findViewById(R.id.customRadio).getTag();
                    Toast.makeText(getApplicationContext(), customTextView.getText(), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });
            return view;
        }
    }
}
