package com.example.system.testmts.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.system.testmts.R;
import com.example.system.testmts.model.Cow;
import com.example.system.testmts.model.Data;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATA = "data";

    private Cow cow;
    private boolean isNewCow = true;

    private FloatingActionButton graph_add;
    private FloatingActionButton saveData;

    private Spinner spinnerBreed;
    private Spinner spinnerSuit;

    private TextView age;
    private TextView cowId;

    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();
        intentData();
    }

    private void init() {
        spinnerBreed = findViewById(R.id.spinner_breed);
        spinnerSuit = findViewById(R.id.spinner_suit);
        graph = findViewById(R.id.graph);
        graph_add = findViewById(R.id.graph_add);
        saveData = findViewById(R.id.save_data);
        age = findViewById(R.id.cow_age);
        cowId = findViewById(R.id.cow_id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentDate();
        breedSpinner();
        suitSpinner();

        graph_add.setOnClickListener(this);
        saveData.setOnClickListener(this);
    }

    private void currentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat ageFormat = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = ageFormat.format(calendar.getTime());
        age.setText(strDate);
    }

    private void graph() {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }

    private void breedSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.cow_breed_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBreed.setAdapter(arrayAdapter);
    }

    private void suitSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.cow_suit_array, android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSuit.setAdapter(arrayAdapter2);
    }

    private void intentData() {
        Intent intent = getIntent();
        if (intent != null) {
            this.cow = (Cow) intent.getSerializableExtra(DATA);
            if (cow != null) {
                isNewCow = false;
                cowId.setText(String.valueOf(cow.getId()));
                spinnerBreed.setSelection(((ArrayAdapter<String>) spinnerBreed.getAdapter()).getPosition(String.valueOf(cow.getBreed())));
                spinnerSuit.setSelection(((ArrayAdapter<String>) spinnerSuit.getAdapter()).getPosition(String.valueOf(cow.getSuit())));
            }
        }
    }

    private void getToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_data:
                String breed = String.valueOf(spinnerBreed.getSelectedItem());
                if (TextUtils.isEmpty(breed)) {
                    getToast(getString(R.string.enter_breed));
                    break;
                }

                String suit = String.valueOf(spinnerSuit.getSelectedItem());
                if (TextUtils.isEmpty(suit)) {
                    getToast(getString(R.string.enter_suit));
                    break;
                }


                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat ageFormat = new SimpleDateFormat("dd.MM.yyyy");
                String stringAge = ageFormat.format(calendar.getTime());
                if (TextUtils.isEmpty(String.valueOf(stringAge))) {
                    getToast(getString(R.string.enter_age));
                    break;
                }

                if (isNewCow) {
                    Data.insertData(breed, suit, stringAge);
                } else {
                    Data.updateData(cow.getId(), breed, suit, stringAge);
                }
                finish();
                break;
            case R.id.graph_add:
                graph();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_cow_info:
                if (cow != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                    builder
                            .setTitle(R.string.delete_alert)
                            .setMessage(R.string.delete_alert_quest)
                            .setCancelable(false)
                            .setPositiveButton(R.string.alert_no_answer, (dialog, i) -> dialog.cancel())
                            .setNegativeButton(R.string.alert_yes_answer, (dialog, id) -> {
                                dialog.cancel();
                                Data.deleteData(cow.getId());
                                finish();
                            });
                    builder.create().show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
