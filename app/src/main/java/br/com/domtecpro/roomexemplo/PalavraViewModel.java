package br.com.domtecpro.roomexemplo;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class PalavraViewModel extends AndroidViewModel {



    private PalavraRepository mRepository;

    private LiveData<List<Palavra>> mAllPalavras;

    public PalavraViewModel (Application application) {
        super(application);
        mRepository = new PalavraRepository(application);
        mAllPalavras = mRepository.getAllPalavras();
    }

    LiveData<List<Palavra>> getAllPalavras() { return mAllPalavras; }

    public void insert(Palavra palavra) { mRepository.insert(palavra); }
}
