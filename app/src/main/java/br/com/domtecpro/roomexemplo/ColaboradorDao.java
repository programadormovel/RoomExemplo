package br.com.domtecpro.roomexemplo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Interface de acesso aos dados do Colaborador no SQliteDatabase (local)
 */
@Dao
public interface ColaboradorDao {

    // Insere os dados contidos no objeto colaborador no banco de dados local
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Colaborador colaborador);

    // Apaga todos os colaboradores contidos na tabela Colaborador
    @Query("Delete FROM Colaborador")
    void deleteAll();

    // Realiza a pesquisa de todos os colaboradores
    // ordenados de forma crescente pelo código
    @Query("Select * FROM Colaborador ORDER BY 1 ASC")
    LiveData<List<Colaborador>> getColaboradoresOrdenados();
}
