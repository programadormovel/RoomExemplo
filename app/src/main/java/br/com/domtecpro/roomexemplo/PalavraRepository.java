package br.com.domtecpro.roomexemplo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class PalavraRepository {
    private PalavraDao mPalavraDao;
    private LiveData<List<Palavra>> mAllPalavras;

    // Note that in order to unit test the PalavraRepository, you have to remove
    // the Application dependency.
    // This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    PalavraRepository(Application application) {
        PalavraRoomDatabase db = PalavraRoomDatabase.getDatabase(application);
        mPalavraDao = db.palavraDao();
        mAllPalavras = mPalavraDao.getPalavrasOrdenadas();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Palavra>> getAllPalavras() {
        return mAllPalavras;
    }

    // You must call this on a non-UI thread or your app will throw an exception.
    // Room ensures that you're not doing any long running operations on the main thread,
    // blocking the UI.
    void insert(Palavra palavra) {
        PalavraRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPalavraDao.insert(palavra);
        });
    }
}
