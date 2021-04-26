package com.example.universebuilder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import model.FichaPagina;
import model.Pagina;

public class ListaPaginasEditarAdapter  extends RecyclerView.Adapter<ListaPaginasEditarAdapter.ViewHolder> implements Filterable {

    private List<FichaPagina> paginas;
    private List<FichaPagina> paginasFull;
    private LayoutInflater mInflater;
    private Context context;
    OnPaginaListener mOnPaginaListener;

    public ListaPaginasEditarAdapter(List<FichaPagina> paginas, Context context, OnPaginaListener mOnPaginaListener) {
        this.paginas = paginas;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mOnPaginaListener = mOnPaginaListener;
        this.paginasFull = new ArrayList<>(paginas);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ficha_pagina, null);
        return new ListaPaginasEditarAdapter.ViewHolder(view,mOnPaginaListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaPaginasEditarAdapter.ViewHolder holder, int position) {
        holder.bindData(paginas.get(position));
    }

    @Override
    public int getItemCount() {
        return paginas.size();
    }

    @Override
    public Filter getFilter() {

        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FichaPagina> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(paginasFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(FichaPagina ficha : paginasFull){
                    if(ficha.getTitulo().toLowerCase().contains(filterPattern)){
                        filteredList.add(ficha);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            paginas.clear();
            paginas.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titulo;
        String id;
        List<String> listaEtiquetas;
        OnPaginaListener onPaginaListener;


        ViewHolder(View itemView, OnPaginaListener onPaginaListener){
            super(itemView);
            titulo = itemView.findViewById(R.id.paginaTextView);
            this.onPaginaListener = onPaginaListener;
            itemView.setOnClickListener(this);
        }

        void bindData(final FichaPagina item){
            titulo.setText(item.getTitulo());
            id = item.getId();
            listaEtiquetas = item.getListaEtiquetas();
        }


        @Override
        public void onClick(View v) {
            onPaginaListener.onPaginaClick(getAdapterPosition());
        }
    }



    public interface OnPaginaListener {
        void onPaginaClick(int position);
    }

}
