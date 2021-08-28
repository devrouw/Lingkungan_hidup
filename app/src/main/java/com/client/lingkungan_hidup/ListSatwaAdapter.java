package com.client.lingkungan_hidup;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListSatwaAdapter extends RecyclerView.Adapter<ListSatwaAdapter.ViewHolder> {
     private ArrayList<Satwa> localdata = ListSatwaActivity.satwas;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("debug adapt", "onCreate called");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_satwa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("debug adapt", "onBind called");
        holder.txtNamaSatwa.setText(localdata.get(position).nama);
        holder.txtSpesies.setText(localdata.get(position).spesies);
        holder.txtAsal.setText(localdata.get(position).asal);
        holder.txtDeskripsi.setText(localdata.get(position).deskripsi);
        Picasso.get().load(MainActivity.image_url + localdata.get(position).gambar).into(holder.imgSatwa);

    }

    @Override
    public int getItemCount() {
        return localdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView getTxtNamaSatwa() {
            return txtNamaSatwa;
        }

        public TextView getTxtDeskripsi() {
            return txtDeskripsi;
        }

        public ImageView getImgSatwa() {
            return imgSatwa;
        }

        private final TextView txtNamaSatwa;

        public TextView getTxtAsal() {
            return txtAsal;
        }

        public TextView getTxtSpesies() {
            return txtSpesies;
        }

        private final TextView txtAsal;
        private final TextView txtSpesies;
        private final TextView txtDeskripsi;
        private final ImageView imgSatwa;

        public ViewHolder(View view) {
            super(view);

            txtNamaSatwa = (TextView) view.findViewById(R.id.txtCardNamaSpesies);
            txtDeskripsi = (TextView) view.findViewById(R.id.txtCardDesc);
            txtAsal = (TextView) view.findViewById(R.id.txtCardAsal);
            txtSpesies = (TextView) view.findViewById(R.id.txtCardSpesies);
            imgSatwa = (ImageView) view.findViewById(R.id.imageViewCardSatwa);
        }



    }

}
