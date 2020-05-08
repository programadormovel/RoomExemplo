package br.com.domtecpro.roomexemplo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PalavraDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Palavra palavra);

    @Query("Delete FROM tabela_de_palavras")
    void deleteAll();

    @Query("Select * FROM tabela_de_palavras ORDER BY palavra ASC")
    LiveData<List<Palavra>> getPalavrasOrdenadas();
}
