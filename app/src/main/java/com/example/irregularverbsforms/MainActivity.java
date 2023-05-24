package com.example.irregularverbsforms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.irregularverbsforms.Model.Verb;
import com.example.irregularverbsforms.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private VerbsDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goToAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddVerbActivity.class));
            }
        });

        dbHelper = new VerbsDatabaseHelper(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecyclerView();
    }

    VerbAdapter verbAdapter;
    void updateRecyclerView(){
        if(verbAdapter == null){
            verbAdapter = new VerbAdapter(new ArrayList<>());
            binding.mainRecycler.setAdapter(verbAdapter);
            binding.mainRecycler.setLayoutManager(new LinearLayoutManager(this));

            itemTouchHelper.attachToRecyclerView(binding.mainRecycler);
        }

        List<Verb> verbList = dbHelper.getAllVerbs();

        verbAdapter.updateVerbList(verbList);
    }

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Verb verbToDelete = verbAdapter.getItem(position);
            dbHelper.deleteVerb(verbToDelete);
            verbAdapter.removeItem(position);
            Toast.makeText(MainActivity.this, "Verb deleted", Toast.LENGTH_SHORT).show();
        }

        
        
    });
    
    
}