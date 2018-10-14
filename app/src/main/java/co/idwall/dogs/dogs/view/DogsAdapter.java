package co.idwall.dogs.dogs.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.idwall.dogs.R;
import co.idwall.dogs.dogs.OnItemClickListener;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.DogsViewHolder> {

    private OnItemClickListener mItemListener;
    private List<String> mDogs;

    DogsAdapter(List<String> dogs, OnItemClickListener itemListener) {
        mItemListener = itemListener;
        mDogs = dogs;
    }

    @NonNull
    @Override
    public DogsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_dogs, viewGroup, false);
        final DogsViewHolder dogsViewHolder = new DogsViewHolder(root);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onItemClick(v, dogsViewHolder.getAdapterPosition());
            }
        });

        return dogsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DogsViewHolder dogsViewHolder, int i) {
        String dog = mDogs.get(i);
        Picasso.get()
                .load(dog)
                .placeholder(R.drawable.dog)
                .error(android.R.drawable.ic_menu_camera)
                .into(dogsViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mDogs.size();
    }

    static class DogsViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        DogsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
