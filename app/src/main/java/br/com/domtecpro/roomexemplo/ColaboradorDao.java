package br.com.domtecpro.roomexemplo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ColaboradorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Colaborador colaborador);

    @Query("Delete FROM Colaborador")
    void deleteAll();

    @Query("Select * FROM Colaborador ORDER BY 1 ASC")
    LiveData<List<Colaborador>> getColaboradoresOrdenados();
}
