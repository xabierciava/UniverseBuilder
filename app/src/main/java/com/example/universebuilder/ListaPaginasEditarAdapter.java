package com.example.universebuilder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import model.FichaPagina;

public class ListaPaginasEditarAdapter  extends RecyclerView.Adapter<ListaPaginasEditarAdapter.ViewHolder> {

    private List<FichaPagina> paginas;
    private LayoutInflater mInflater;
    private Context context;
    OnPaginaListener mOnPaginaListener;

    public ListaPaginasEditarAdapter(List<FichaPagina> paginas, Context context, OnPaginaListener mOnPaginaListener) {
        this.paginas = paginas;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mOnPaginaListener = mOnPaginaListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ficha_pagina, null);
        return new ListaPaginasEditarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaPaginasEditarAdapter.ViewHolder holder, int position) {
        holder.bindData(paginas.get(position));
    }

    @Override
    public int getItemCount() {
        return paginas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        String id;
        List<String> listaEtiquetas;


        ViewHolder(View itemView){
            super(itemView);
            titulo = itemView.findViewById(R.id.paginaTextView);

        }

        void bindData(final FichaPagina item){
            titulo.setText(item.getTitulo());
            id = item.getId();
            listaEtiquetas = item.getListaEtiquetas();
        }
    }

    public interface OnPaginaListener {
        void onPaginaClick(int position);
    }

}
