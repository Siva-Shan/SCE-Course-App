package com.example.sce.screen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sce.db.branch.BranchDao;
import com.example.sce.model.Branch;
import com.example.sce.model.User;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sce.R;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class NewBranch extends AppCompatActivity {

    private EditText etBranchCode;
    private EditText etBranchName;
    private Button btnSave;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private Button buttonSelectLocation;
    private Branch branch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_branch);

        etBranchCode = findViewById(R.id.etBranchCode);
        etBranchName = findViewById(R.id.etBranchName);
        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();

        branch = (Branch) intent.getSerializableExtra("branch");

        if (branch == null) {
            btnSave.setText("Add");
        } else {
            if (!TextUtils.isEmpty(branch.getBranchCode()) && !TextUtils.isEmpty(branch.getBranchName())) {
                etBranchCode.setText(branch.getBranchCode());
                etBranchName.setText(branch.getBranchName());
            }
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (branch == null) {
                    saveBranch();
                } else {
                    updateBranch();
                }

            }
        });

        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));

        buttonSelectLocation = findViewById(R.id.button_select_location);
        buttonSelectLocation.setOnClickListener(view -> openAutocompleteActivity());
    }

    private void openAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(
                com.google.android.libraries.places.api.model.Place.Field.ID,
                com.google.android.libraries.places.api.model.Place.Field.NAME,
                com.google.android.libraries.places.api.model.Place.Field.LAT_LNG
        );

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                com.google.android.libraries.places.api.model.Place place = Autocomplete.getPlaceFromIntent(data);
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    //textViewLocation.setText("Selected Location: " + latLng.latitude + ", " + latLng.longitude);
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                System.out.println("bbbb : "+status.getStatusMessage());
                // TODO: Handle the error.
            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("cccc : error occured");
                // The user canceled the operation.
            }
        }
    }

    private void saveBranch() {
        String branchCode = etBranchCode.getText().toString().trim();
        String branchName = etBranchName.getText().toString().trim();

        if (branchCode.isEmpty() || branchName.isEmpty()) {
            Toast.makeText(NewBranch.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            BranchDao branchDao = new BranchDao(getApplicationContext());
            Branch branch = new Branch(branchCode, branchName, 12.222, 12.555);//TODO
            branchDao.addBranch(branch);

            Toast.makeText(NewBranch.this, "Branch saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateBranch() {
        String branchCode = etBranchCode.getText().toString().trim();
        String branchName = etBranchName.getText().toString().trim();

        if (branchCode.isEmpty() || branchName.isEmpty()) {
            Toast.makeText(NewBranch.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            BranchDao branchDao = new BranchDao(getApplicationContext());
            branch.setBranchCode(branchCode);
            branch.setBranchName(branchName);
            //TODO
            branchDao.updateBranch(branch);

            Toast.makeText(NewBranch.this, "Branch updated", Toast.LENGTH_SHORT).show();
            finish();
        }


    }
}
