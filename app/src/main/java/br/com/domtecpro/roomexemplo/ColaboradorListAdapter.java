package br.com.domtecpro.roomexemplo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
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
        public final AppCompatImageView mFotoView;

        public Colaborador mItem;


        private ColaboradorViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mIdView = (AppCompatTextView) mView.findViewById(R.id.item_number);
            mContentView = (AppCompatTextView) mView.findViewById(R.id.content);
            mDetailView = (AppCompatTextView) mView.findViewById(R.id.details);
            mFotoView = (AppCompatImageView) mView.findViewById(R.id.foto);
        }
    }

    private final LayoutInflater mInflater;
    private List<Colaborador> mColaboradores; // Cached copy of Colaboradores
    public Bitmap mImagemExemplo;

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

            holder.mItem = mColaboradores.get(position);
            holder.mIdView.setText(String.valueOf(mColaboradores.get(position).getId()));
            holder.mContentView.setText(mColaboradores.get(position).getNome());
            // included by PM
            holder.mDetailView.setText(mColaboradores.get(position).getCargo());

            Bitmap bitmap = null;
            if(mColaboradores.get(position).getFoto()==null){
                bitmap = mImagemExemplo;
            }else{
                bitmap = converterByteToBipmap(mColaboradores.get(position).getFoto());
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

    void setColaboradores(List<Colaborador> colaboradores){
        mColaboradores = colaboradores;
        notifyDataSetChanged();
    }

    void setColaboradores(List<Colaborador> colaboradores, byte[] bmp){
        mColaboradores = colaboradores;
        mImagemExemplo = converterByteToBipmap(bmp);
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

    public Bitmap converterByteToBipmap(byte[] foto) {
        Bitmap bmp = null;
        Bitmap bitmapReduzido = null;
        byte[] x = foto;

        try {
            bmp = BitmapFactory.decodeByteArray(x, 0, x.length);

            bitmapReduzido = Bitmap.createScaledBitmap(bmp, 1080, 1000, true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmapReduzido;
    }
}

