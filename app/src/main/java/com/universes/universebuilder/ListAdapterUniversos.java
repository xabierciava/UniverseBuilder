package com.universes.universebuilder;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import model.FichaUniverso;

public class ListAdapterUniversos extends RecyclerView.Adapter<ListAdapterUniversos.ViewHolder> {

    final int DRAGON=1;
    final int TERROR=2;
    final int NAVECITA=3;
    final int SUPERMAN=4;
    final int MUNDODISCO=5;
    final int EIFFEL=6;
    private List<FichaUniverso> mData;
    private LayoutInflater mInflater;
    private Context context;
    private OnUniverseListener mOnUniverseListener;

    public ListAdapterUniversos(List<FichaUniverso> mData, Context context, OnUniverseListener onUniverseListener) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mOnUniverseListener=onUniverseListener;
    }

    @NonNull
    @Override
    public ListAdapterUniversos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ficha_universo, null);
        return new ListAdapterUniversos.ViewHolder(view,mOnUniverseListener);

    }

    @Override
    public void onBindViewHolder(final ListAdapterUniversos.ViewHolder holder, int position) {
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
        OnUniverseListener onUniverseListener;

        ViewHolder(View itemView,OnUniverseListener onUniverseListener){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            universo = itemView.findViewById(R.id.universeTextView);
            descripcion = itemView.findViewById(R.id.descripcionTextView);
            this.onUniverseListener = onUniverseListener;
            itemView.setOnClickListener(this);
        }

        void bindData(final FichaUniverso item){
            universo.setText(item.getNombre());
            descripcion.setText(item.getDescripcion());
            switch(item.getIcono()){
                case DRAGON:
                    iconImage.setImageResource(R.drawable.dragon);
                    break;
                case TERROR:
                    iconImage.setImageResource(R.drawable.cthulhu);
                    break;
                case NAVECITA:
                    iconImage.setImageResource(R.drawable.navecita);
                    break;
                case SUPERMAN:
                    iconImage.setImageResource(R.drawable.superman);
                    break;
                case MUNDODISCO:
                    iconImage.setImageResource(R.drawable.mundodisco);
                    break;
                case EIFFEL:
                    iconImage.setImageResource(R.drawable.tugueifel);
                    break;
            }

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
