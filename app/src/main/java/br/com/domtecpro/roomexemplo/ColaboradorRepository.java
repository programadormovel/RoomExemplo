package br.com.domtecpro.roomexemplo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Repositório de Dados
 * Classe responsável pelo CRUD nas bases de dados disponíveis
 * localmente o remotamente
 */
class ColaboradorRepository {
    // Declaração do objeto de acesso aos dados do colaborador
    private ColaboradorDao mColaboradorDao;
    // objeto do tipo LiveData, que carrega a lista de colaboradores na ViewModel
    private LiveData<List<Colaborador>> mAllColaboradores;

    // Note that in order to unit test the ColaboradorRepository, you have to remove
    // the Application dependency.
    // This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    // Tradução do texto acima:
    // Note que para executar o teste unitário neste repositório, você deve remover
    // a dependência do objeto application criado a partir de Application
    // Isto adicionará complexidade e muito mais código, em este exemplo não é sobre teste.
    // Veja o projeto BasicSample no repositório de componentes da arquitetura android em
    // https://github.com/googlesamples
    ColaboradorRepository(Application application) {
        // Criado um banco de dados utilizando RoomDatabase
        ColaboradorRoomDatabase db = ColaboradorRoomDatabase.getDatabase(application);
        // Conexão criada no banco de dados local
        mColaboradorDao = db.colaboradorDao();
        // Consulta à lista de colaboradores ordenada, que será enviada à ViewModel
        mAllColaboradores = mColaboradorDao.getColaboradoresOrdenados();
    }

    // Room executa todas as querys em uma thread separada.
    // Room executes all queries on a separate thread.
    // O Observador LiveData irá notificar o observador que esta na MainActivity quando a
    // informação for trocada na tela, isto é, uma consulta à base de dados garantirá
    // que os dados perdidos na destruição do ciclo de vida serão mantidos na tela
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Colaborador>> getAllColaboradores() {
        return mAllColaboradores;
    }

    /** Você não deve efetuar a chamada deste método em uma tela (Activity ou Fragment)
    // ou causará uma falha no app*/
    // You must call this on a non-UI thread or your app will throw an exception.
    /** Room irá travar a interface gráfica se uma operação durar muito tempo */
    // Room ensures that you're not doing any long running operations on the main thread,
    // blocking the UI.
    void insert(Colaborador colaborador) {
        // método de execução paralela (thread)
        ColaboradorRoomDatabase.databaseWriteExecutor.execute(() -> {
            // Este método é chamado pela ViewModel
            try {
                // Inserindo colaborador no banco local (SQLite via RoomDatabase)
                mColaboradorDao.insert(colaborador);
                // Inserindo registro no banco externo
                ColaboradorDaoExterno.inserirColaborador(colaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
