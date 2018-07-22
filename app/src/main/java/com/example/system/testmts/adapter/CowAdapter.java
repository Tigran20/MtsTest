package com.example.system.testmts.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.system.testmts.R;
import com.example.system.testmts.model.Cow;
import com.example.system.testmts.view.DetailsActivity;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class CowAdapter extends RecyclerView.Adapter<CowAdapter.CowViewHolder> {

    private Context mContext;
    private List<Cow> mCows;
    public static final String DATA = "data";

    public CowAdapter(Context context, List<Cow> cows) {
        mContext = context;
        mCows = cows;
    }

    @NonNull
    @Override
    public CowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cow_item, parent, false);
        return new CowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CowViewHolder holder, int position) {
        final Cow cow = mCows.get(position);

        holder.id.setText(String.valueOf(cow.getId()));
        holder.breed.setText(cow.getBreed());
        holder.suit.setText(cow.getSuit());
        holder.age.setText(String.valueOf(cow.getAge()));
        holder.cardView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(DATA, cow);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mCows.size();
    }

    public class CowViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView id;
        private TextView breed;
        private TextView suit;
        private TextView age;

        public CowViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            id = itemView.findViewById(R.id.cow_id);
            breed = itemView.findViewById(R.id.cow_breed);
            suit = itemView.findViewById(R.id.cow_suit);
            age = itemView.findViewById(R.id.cow_age);
        }
    }
}
