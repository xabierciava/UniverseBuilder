package com.example.universebuilder;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universebuilder.R;

import java.util.List;

import api.ApiInterface;
import api.ServiceGenerator;
import model.FichaUniversoExplorar;
import model.Universo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapterExplorarUniversos extends RecyclerView.Adapter<com.example.universebuilder.ListAdapterExplorarUniversos.ViewHolder> {
    private List<FichaUniversoExplorar> mData;
    private LayoutInflater mInflater;
    private Context context;
    private com.example.universebuilder.ListAdapterExplorarUniversos.OnUniverseListener mOnUniverseListener;

    public ListAdapterExplorarUniversos(List<FichaUniversoExplorar> mData, Context context, com.example.universebuilder.ListAdapterExplorarUniversos.OnUniverseListener onUniverseListener) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mOnUniverseListener=onUniverseListener;
    }

    @NonNull
    @Override
    public com.example.universebuilder.ListAdapterExplorarUniversos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ficha_universo_explorar, null);
        return new com.example.universebuilder.ListAdapterExplorarUniversos.ViewHolder(view,mOnUniverseListener);

    }

    @Override
    public void onBindViewHolder(final com.example.universebuilder.ListAdapterExplorarUniversos.ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItems(List<FichaUniversoExplorar> items){
        mData=items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iconImage;
        ImageView corazon;
        TextView universo, descripcion;
        com.example.universebuilder.ListAdapterExplorarUniversos.OnUniverseListener onUniverseListener;

        ViewHolder(View itemView, com.example.universebuilder.ListAdapterExplorarUniversos.OnUniverseListener onUniverseListener){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            universo = itemView.findViewById(R.id.universeTextView);
            descripcion = itemView.findViewById(R.id.descripcionTextView);
            corazon = itemView.findViewById(R.id.imagenFavoritos);
            this.onUniverseListener = onUniverseListener;
            itemView.setOnClickListener(this);
        }

        void bindData(final FichaUniversoExplorar item){
            universo.setText(item.getNombre());
            descripcion.setText(item.getDescripcion());
            switch(item.getIcono()){
                case 1:
                    iconImage.setImageResource(R.drawable.dragon);
                    break;
                case 2:
                    iconImage.setImageResource(R.drawable.cthulhu);
                    break;
            }
            if(item.getFavorito()){
                corazon.setImageResource(R.drawable.ic_baseline_favorite_24);
            }else{
                corazon.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }
            corazon.setOnClickListener(v -> {
                if (item.getFavorito()){
                    item.setFavorito(false);
                    corazon.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    unsetFavorito(item.getId());
                }else{
                    item.setFavorito(true);
                    corazon.setImageResource(R.drawable.ic_baseline_favorite_24);
                    setFavorito(item.getId());
                }
            });

        }



        public void setFavorito(String idUniverso){
            String idUsuario;
            SharedPreferences prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE);
            idUsuario = prefs.getString("id","");

            ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
            Call<String> call = apiInterface.setFavorito(idUsuario,idUniverso);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("tag", t.getMessage());
                }
            });
        }

        public void unsetFavorito(String idUniverso){
            String idUsuario;
            SharedPreferences prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE);
            idUsuario = prefs.getString("id","");

            ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
            Call<String> call = apiInterface.unsetFavorito(idUsuario,idUniverso);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("tag", t.getMessage());
                }
            });
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
