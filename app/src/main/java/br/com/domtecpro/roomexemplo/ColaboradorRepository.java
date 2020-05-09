package br.com.domtecpro.roomexemplo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class ColaboradorRepository {
    private ColaboradorDao mColaboradorDao;
    private LiveData<List<Colaborador>> mAllColaboradores;

    // Note that in order to unit test the ColaboradorRepository, you have to remove
    // the Application dependency.
    // This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    ColaboradorRepository(Application application) {
        ColaboradorRoomDatabase db = ColaboradorRoomDatabase.getDatabase(application);
        mColaboradorDao = db.colaboradorDao();
        mAllColaboradores = mColaboradorDao.getColaboradoresOrdenados();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Colaborador>> getAllColaboradores() {
        return mAllColaboradores;
    }

    // You must call this on a non-UI thread or your app will throw an exception.
    // Room ensures that you're not doing any long running operations on the main thread,
    // blocking the UI.
    void insert(Colaborador colaborador) {
        ColaboradorRoomDatabase.databaseWriteExecutor.execute(() -> {
            mColaboradorDao.insert(colaborador);
        });
    }
}
