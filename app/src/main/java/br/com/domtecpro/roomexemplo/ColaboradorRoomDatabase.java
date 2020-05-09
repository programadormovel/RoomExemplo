package br.com.domtecpro.roomexemplo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// A classe Dao precisa ser abstrata e ser filha da classe RoomDatabase
// A notação @Database define que a classe é uma Room database
//      O parâmetro entities define as tabelas do banco de dados
//      e define o número da versão do banco dados
//      exportSchema é utilizado para controle de versão do banco de dados
@Database(entities = {Colaborador.class}, version = 1, exportSchema = false)
public abstract class ColaboradorRoomDatabase extends RoomDatabase {

    public abstract ColaboradorDao colaboradorDao();

    private static volatile ColaboradorRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ColaboradorRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ColaboradorRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ColaboradorRoomDatabase.class, "Colaborador_database")
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                ColaboradorDao dao = INSTANCE.colaboradorDao();
                dao.deleteAll();

                Colaborador colaborador = new Colaborador(1, "Adriano",
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
                dao.insert(colaborador);
            });
        }
    };
}
