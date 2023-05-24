package com.example.irregularverbsforms;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irregularverbsforms.Model.Verb;
import com.example.irregularverbsforms.databinding.VerbListItemBinding;

import java.util.List;

public class VerbAdapter extends RecyclerView.Adapter<VerbAdapter.VerbViewHolder> {

    private List<Verb> verbList;

    public VerbAdapter(List<Verb> verbList) {
        this.verbList = verbList;
    }

    @NonNull
    @Override
    public VerbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VerbListItemBinding binding = VerbListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VerbViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VerbViewHolder holder, int position) {
        Verb verb = verbList.get(position);
        holder.bind(verb);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), VerbDetailsActivity.class);
                intent.putExtra("verb_id", verb.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return verbList.size();
    }

    static class VerbViewHolder extends RecyclerView.ViewHolder {
        private final VerbListItemBinding binding;

        VerbViewHolder(@NonNull VerbListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Verb verb) {
            binding.verbText.setText(verb.getBasicForm());
        }
    }

    public void updateVerbList(List<Verb> newList) {
        verbList.clear();
        verbList.addAll(newList);
        notifyDataSetChanged();
    }

    public Verb getItem(int position) {
        return verbList.get(position);
    }

    public void removeItem(int position) {
        verbList.remove(position);
        notifyItemRemoved(position);
    }
}
