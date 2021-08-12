package sg.edu.rp.c346.id20039529.gloriouswonders;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Third_Activity extends AppCompatActivity {

    EditText etID, etTitle, etSingers, etYear;
    Button btnCancel, btnUpdate, btnDelete;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_third));

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        etID = (EditText) findViewById(R.id.etID);
        etTitle = (EditText) findViewById(R.id.etName);
        etSingers = (EditText) findViewById(R.id.etDescription);
        etYear = (EditText) findViewById(R.id.etCountry);
        ratingBar = findViewById(R.id.ratingbarStars);

        Intent i = getIntent();
        final Item currentItem = (Item) i.getSerializableExtra("item");

        etID.setText(currentItem.getId()+"");
        etTitle.setText(currentItem.getName());
        etSingers.setText(currentItem.getDescription());
        etYear.setText(currentItem.getCountry()+"");

        ratingBar.setRating(currentItem.getStars());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(Third_Activity.this);
                currentItem.setName(etTitle.getText().toString().trim());
                currentItem.setDescription(etSingers.getText().toString().trim());
                currentItem.setCountry(etYear.getText().toString().trim());


                //int selectedRB = rg.getCheckedRadioButtonId();
                //RadioButton rb = (RadioButton) findViewById(selectedRB);
                //currentSong.setStars(Integer.parseInt(rb.getText().toString()));
                currentItem.setStars((int) ratingBar.getRating());
                int result = dbh.updateItem(currentItem);
                if (result>0){
                    Toast.makeText(Third_Activity.this, "Song updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Third_Activity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Third_Activity.this);
                alertBuilder.setTitle("Danger");
                alertBuilder.setMessage("Are you sure you want to delete the island\n" + currentItem.getName());
                alertBuilder.setCancelable(true);
                alertBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper dbh = new DBHelper(Third_Activity.this);
                        int result = dbh.deleteItem(currentItem.getId());
                        if (result>0){
                            Toast.makeText(Third_Activity.this, "Song deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Third_Activity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertBuilder.setPositiveButton("Cancel", null);
                AlertDialog alertDialog = alertBuilder.create();
                alertBuilder.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Third_Activity.this);
                alertBuilder.setTitle("Danger");
                alertBuilder.setMessage("Are you sure you want discard the changes\n");
                alertBuilder.setCancelable(true);
                alertBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alertBuilder.setPositiveButton("Do NOT Discard", null);
                AlertDialog alertDialog = alertBuilder.create();
                alertBuilder.show();

            }
        });

    }


}