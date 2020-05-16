package br.com.domtecpro.roomexemplo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Adaptador utilizado pelo RecyclerView que carregará os dodos dos colaboradores
 * Carrega os dados dos itens (cada colaborador) na lista
 */
public class ColaboradorListAdapter extends
        RecyclerView.Adapter<ColaboradorListAdapter.ColaboradorViewHolder> {

    /**
     * Classe ViewHolder
     * Realiza o de/para - objeto Java (classe)/objeto XML (tela)
     */
    class ColaboradorViewHolder extends RecyclerView.ViewHolder {

        public final View mView;                        //colaborador
        public final AppCompatTextView mIdView;         //id colaborador
        public final AppCompatTextView mContentView;    //nome colaborador
        //included by PM
        public final AppCompatTextView mDetailView;     //cargo colaborador
        public final AppCompatImageView mFotoView;      //foto colaborador

        public Colaborador mItem;                       //objeto colaborador (item da lista)


        // Método construtor do ViewHolder, realiza o de/para
        // Realiza o vínculo Tela x Objeto java (item da lista - RecyclerView)
        private ColaboradorViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mIdView = (AppCompatTextView) mView.findViewById(R.id.item_number);
            mContentView = (AppCompatTextView) mView.findViewById(R.id.content);
            mDetailView = (AppCompatTextView) mView.findViewById(R.id.details);
            mFotoView = (AppCompatImageView) mView.findViewById(R.id.foto);
        }
    }

    // objeto que carrega o item da lista na tela em tempo de execução
    private final LayoutInflater mInflater;
    private List<Colaborador> mColaboradores; // Armazena a cópia dos Colaboradores
    /**
     * objeto que receberá a conversão da imagem de bytes para bitmap
     * e permite o carregamento da imagem no objeto AppCompatImageView da tela
     */
    public Bitmap mImagemExemplo;

    // Construtor da classe, recebe o contexto atual (tela atual) para carregamento da lista
    ColaboradorListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    // método que cria e retorna uma visão (View) do item atual, para carregamento na lista
    @Override
    public ColaboradorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ColaboradorViewHolder(itemView);
    }

    /**
     * método que realiza o vínculo dos dados nos objetos declarados no ViewHolder
     * Isto é, realiza o carregamento dos dados na tela, em cada item da lista
     */
    @Override
    public void onBindViewHolder(ColaboradorViewHolder holder, int position) {
        if (mColaboradores != null) {
            Colaborador current = mColaboradores.get(position);

            holder.mItem = mColaboradores.get(position);
            holder.mIdView.setText(String.valueOf(mColaboradores.get(position).getId()));
            holder.mContentView.setText(mColaboradores.get(position).getNome());
            // included by PM
            holder.mDetailView.setText(mColaboradores.get(position).getCargo());

            Bitmap bitmap = null;
            if(mColaboradores.get(position).getFoto()==null){
                bitmap = mImagemExemplo;
            }else{
                bitmap = Util.converterByteToBipmap(mColaboradores.get(position).getFoto());
            }

            if (bitmap != null) {
                holder.mFotoView.setScaleType(AppCompatImageView.ScaleType.FIT_XY);
                holder.mFotoView.setImageBitmap(bitmap);
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.mContentView.setText("No Colaborador");
        }
    }

    /**
     * Método que alimenta a lista de colaboradores do adaptador, vindos do repositório,
     * através do ViewModel
     * @param colaboradores
     */
    void setColaboradores(List<Colaborador> colaboradores){
        mColaboradores = colaboradores;
        notifyDataSetChanged();
    }

    void setColaboradores(List<Colaborador> colaboradores, byte[] bmp){
        mColaboradores = colaboradores;
        mImagemExemplo = Util.converterByteToBipmap(bmp);
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mColaboradores has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mColaboradores != null)
            return mColaboradores.size();
        else return 0;
    }

}

