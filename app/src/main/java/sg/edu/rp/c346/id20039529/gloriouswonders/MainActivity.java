package sg.edu.rp.c346.id20039529.gloriouswonders;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    Button btnView;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.buttonAdd);
        btnView = findViewById(R.id.buttonView);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    // Inflate the input.xml layout file
                    LayoutInflater inflater =
                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.view, null);

                    final EditText etTitle, etDesc, etCountry;
                    final RatingBar ratingBar;

                    // Obtain the UI component in the input.xml layout
                    // It needs to be defined as "final", so that it can used in the onClick() method later
                    etTitle =  viewDialog.findViewById(R.id.etName);
                    etDesc = viewDialog.findViewById(R.id.etDescription);
                    etCountry =  viewDialog.findViewById(R.id.etCountry);
                    ratingBar = viewDialog.findViewById(R.id.ratingBarStars);

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                    myBuilder.setView(viewDialog);// Set the view of the dialog
                    myBuilder.setTitle("Insert New Location");
                    myBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Extract the data entered by the user
                            String title = etTitle.getText().toString().trim();
                            String desc = etDesc.getText().toString().trim();
                            String country = etCountry.getText().toString().trim();
                            if (title.length() == 0 || desc.length() == 0 || country.length() == 0){
                                Toast.makeText(MainActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            DBHelper dbh = new DBHelper(MainActivity.this);

                            int rating = (int) ratingBar.getRating();
                            dbh.insertItem(title, desc, country, rating);
                            dbh.close();
                            Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_LONG).show();

                            etTitle.setText("");
                            etDesc.setText("");
                            etCountry.setText("");
                        }
                    });
                    myBuilder.setNegativeButton("CANCEL", null);
                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();
                }
            });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Second_Activity.class);
                startActivity(i);
            }
        });
        }
    }

