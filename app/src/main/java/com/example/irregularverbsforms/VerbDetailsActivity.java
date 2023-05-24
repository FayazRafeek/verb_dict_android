package com.example.irregularverbsforms;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.irregularverbsforms.Model.Verb;
import com.example.irregularverbsforms.databinding.ActivityVerbDetailsBinding;

public class VerbDetailsActivity extends AppCompatActivity {

    private VerbsDatabaseHelper dbHelper;

    ActivityVerbDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVerbDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dbHelper = new VerbsDatabaseHelper(this);

        int verbId = getIntent().getIntExtra("verb_id", -1);
        Verb verb = dbHelper.getVerbById(verbId);

        if (verb != null) {
            binding.txtBasicForm.setText(verb.getBasicForm());
            binding.txtPastSimple.setText(verb.getPastSimple());
            binding.txtPastPrinciple.setText(verb.getPastPrinciple());
        }
    }
}
