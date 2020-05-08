package br.com.domtecpro.roomexemplo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PalavraListAdapter extends RecyclerView.Adapter<PalavraListAdapter.PalavraViewHolder> {

    class PalavraViewHolder extends RecyclerView.ViewHolder {
        private final TextView palavraItemView;

        private PalavraViewHolder(View itemView) {
            super(itemView);
            palavraItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Palavra> mPalavras; // Cached copy of Palavras

    PalavraListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public PalavraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PalavraViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PalavraViewHolder holder, int position) {
        if (mPalavras != null) {
            Palavra current = mPalavras.get(position);
            holder.palavraItemView.setText(current.getPalavra());
        } else {
            // Covers the case of data not being ready yet.
            holder.palavraItemView.setText("No Palavra");
        }
    }

    void setPalavras(List<Palavra> palavras){
        mPalavras = palavras;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mPalavras has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mPalavras != null)
            return mPalavras.size();
        else return 0;
    }
}

