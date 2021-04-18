package com.example.universebuilder;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<FichaUniverso> mData;
    private LayoutInflater mInflater;
    private Context context;
    private OnUniverseListener mOnUniverseListener;

    public ListAdapter(List<FichaUniverso> mData, Context context, OnUniverseListener onUniverseListener) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mOnUniverseListener=onUniverseListener;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ficha_universo, null);
        return new ListAdapter.ViewHolder(view,mOnUniverseListener);

    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItems(List<FichaUniverso> items){
        mData=items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iconImage;
        TextView universo, descripcion;
        LinearLayout fondo;
        OnUniverseListener onUniverseListener;

        ViewHolder(View itemView,OnUniverseListener onUniverseListener){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            universo = itemView.findViewById(R.id.universeTextView);
            descripcion = itemView.findViewById(R.id.descripcionTextView);
            fondo = itemView.findViewById(R.id.linearLayoutGeneral);
            this.onUniverseListener = onUniverseListener;
            itemView.setOnClickListener(this);
        }

        void bindData(final FichaUniverso item){
            universo.setText(item.getNombre());
            descripcion.setText(item.getDescripcion());
            fondo.setBackgroundColor(Color.parseColor(item.getColor()));
        }

        @Override
        public void onClick(View v) {
            onUniverseListener.onUniverseClick(getAdapterPosition());
        }
    }

    public interface OnUniverseListener{
        void onUniverseClick(int position);
    }
}
