package br.com.domtecpro.roomexemplo;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ColaboradorViewModel extends AndroidViewModel {

    private ColaboradorRepository mRepository;

    private LiveData<List<Colaborador>> mAllColaboradores;

    public ColaboradorViewModel(Application application) {
        super(application);
        mRepository = new ColaboradorRepository(application);
        mAllColaboradores = mRepository.getAllColaboradores();
    }

    LiveData<List<Colaborador>> getAllColaboradores() { return mAllColaboradores; }

    public void insert(Colaborador colaborador) { mRepository.insert(colaborador); }
}
