package br.com.domtecpro.roomexemplo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ColaboradorListAdapter extends
        RecyclerView.Adapter<ColaboradorListAdapter.ColaboradorViewHolder> {

    class ColaboradorViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final AppCompatTextView mIdView;
        public final AppCompatTextView mContentView;
        //included by PM
        public final AppCompatTextView mDetailView;

        public Colaborador mItem;

        private ColaboradorViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mIdView = (AppCompatTextView) mView.findViewById(R.id.item_number);
            mContentView = (AppCompatTextView) mView.findViewById(R.id.content);
            //included by PM
            mDetailView = (AppCompatTextView) mView.findViewById(R.id.details);
        }
    }

    private final LayoutInflater mInflater;
    private List<Colaborador> mColaboradores; // Cached copy of Colaboradores

    ColaboradorListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ColaboradorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ColaboradorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ColaboradorViewHolder holder, int position) {
        if (mColaboradores != null) {
            Colaborador current = mColaboradores.get(position);
            //holder.colaboradorItemView.setText(current.getPalavra());

            holder.mItem = mColaboradores.get(position);
            holder.mIdView.setText(mColaboradores.get(position).getId());
            holder.mContentView.setText(mColaboradores.get(position).getNome());
            // included by PM
            holder.mDetailView.setText(mColaboradores.get(position).getCargo());

        } else {
            // Covers the case of data not being ready yet.
            holder.mContentView.setText("No Colaborador");
        }
    }

    void setColaboradores(List<Colaborador> colaboradores){
        mColaboradores = colaboradores;
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

