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
@Database(entities = {Palavra.class}, version = 1, exportSchema = false)
public abstract class PalavraRoomDatabase extends RoomDatabase {

    public abstract PalavraDao palavraDao();

    private static volatile PalavraRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PalavraRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PalavraRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PalavraRoomDatabase.class, "palavra_database")
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                PalavraDao dao = INSTANCE.palavraDao();
                dao.deleteAll();

                Palavra palavra = new Palavra("Hello");
                dao.insert(palavra);
                palavra = new Palavra("World");
                dao.insert(palavra);
            });
        }
    };
}
