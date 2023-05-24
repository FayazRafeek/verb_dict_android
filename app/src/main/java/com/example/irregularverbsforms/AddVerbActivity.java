package com.example.irregularverbsforms;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irregularverbsforms.Model.Verb;
import com.example.irregularverbsforms.databinding.ActivityAddVerbBinding;

public class AddVerbActivity extends AppCompatActivity {

    ActivityAddVerbBinding binding;
    private VerbsDatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddVerbBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new VerbsDatabaseHelper(this);

        binding.btnVerbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveVerb();
                dbHelper.populateDatabase();
            }
        });


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void saveVerb() {
        String basicForm = binding.inpBasicForm.getText().toString().trim();
        String pastSimple = binding.inpPastSimple.getText().toString().trim();
        String pastPrinciple = binding.inpPastPrinciple.getText().toString().trim();

        if (basicForm.isEmpty() || pastSimple.isEmpty() || pastPrinciple.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Verb verb = new Verb(0, basicForm, pastSimple, pastPrinciple);
        dbHelper.addVerb(verb);

        Toast.makeText(this, "Verb saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
