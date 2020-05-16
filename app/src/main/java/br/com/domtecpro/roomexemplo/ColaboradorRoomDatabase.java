package br.com.domtecpro.roomexemplo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// A classe Dao precisa ser abstrata e ser filha da classe RoomDatabase
// A notação @Database define que a classe é uma Room database
//      O parâmetro entities define as tabelas do banco de dados
//      e define o número da versão do banco dados
//      exportSchema é utilizado para controle de versão do banco de dados
@Database(entities = {Colaborador.class}, version = 1, exportSchema = false)
public abstract class ColaboradorRoomDatabase extends RoomDatabase {

    // Objeto abstrato de conexão
    public abstract ColaboradorDao colaboradorDao();
    // Objeto volátil criado a partir esta mesma classe
    private static volatile ColaboradorRoomDatabase INSTANCE;
    // Número de threads disponíveis para o objeto de conexão
    private static final int NUMBER_OF_THREADS = 4;
    // Objeto que permitirá a execução da conexão no modo assíncrono
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /** método de obtenção do banco de dados
     * Cria o banco de dados se ele não existir em tempo de execução (INSTANCE)
     * O método addCallBack fará a execução do método onOpen() que se encontra
     * no objeto criado abaixo chamado: sRoomDatabaseCallback
     * O método build() constrói o banco de dados
     * @param context
     * @return
     */
    static ColaboradorRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ColaboradorRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ColaboradorRoomDatabase.class, "colaborador_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Objeto estático do tipo Callback
    private static Callback sRoomDatabaseCallback = new Callback() {
        // Método onOpen realiza a abertura do banco de dados criado
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                // Banco SQLiteDatabase (interno)
                ColaboradorDao dao = INSTANCE.colaboradorDao();
                // Consulta externa ao banco de dados SQL Server
                List<Colaborador> listaExterna;
                listaExterna = ColaboradorDaoExterno.pesquisarColaboradoresEx();
                // Verificar se a lista pesquisada externamente existe, então apaga o banco interno
                if(listaExterna.size()>0){
                    // apagando dados internos
                    dao.deleteAll();

                    // preenchendo dados externos no banco interno
                    // Trazendo dados do SQL Server para o SQLiteDatabase
                    for(Colaborador colab : listaExterna){
                        dao.insert(colab);
                    }
                }

                // Simulação de dados locais
                /*Colaborador colaborador = new Colaborador(1, "Adriano",
                        "Programador", "11111111111",
                        "Rua Passarinhos, 77",
                        "programadormovel@gmail.com",
                        null, null);
                dao.insert(colaborador);
                colaborador = new Colaborador(2, "Néia",
                        "Cachorreira", "11111111111",
                        "Rua Passarinhos, 77",
                        "neia@gmail.com",
                        null, null);
                dao.insert(colaborador);*/
            });
        }
    };
}
