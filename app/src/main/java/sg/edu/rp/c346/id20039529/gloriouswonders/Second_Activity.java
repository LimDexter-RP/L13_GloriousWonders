package sg.edu.rp.c346.id20039529.gloriouswonders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Second_Activity extends AppCompatActivity {
            ListView lv;
            ArrayList<Item> itemList;
            ArrayAdapter<Item> alPlace;
            Button btn5Stars;
            Button btnBack;

            @Override
            protected void onResume() {
                super.onResume();
                DBHelper dbh = new DBHelper(this);
                itemList.clear();
                itemList.addAll(dbh.getAllItems());
                alPlace.notifyDataSetChanged();

            }

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_second);

                setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_second));

                lv = (ListView) this.findViewById(R.id.lv);
                btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);
                btnBack = (Button) this.findViewById(R.id.buttonBack);

                DBHelper dbh = new DBHelper(this);
                itemList = dbh.getAllItems();
                dbh.close();

                alPlace = new CustomAdapter(this, R.layout.row, itemList);
                lv.setAdapter(alPlace);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(Second_Activity.this, Third_Activity.class);
                        Intent item = i.putExtra("item", itemList.get(position));
                        startActivity(i);
                    }
                });

                btn5Stars.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBHelper dbh = new DBHelper(Second_Activity.this);
                        itemList.clear();
                        itemList.addAll(dbh.getAllSongsByStars(5));
                        alPlace.notifyDataSetChanged();
                    }
                });

                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
}