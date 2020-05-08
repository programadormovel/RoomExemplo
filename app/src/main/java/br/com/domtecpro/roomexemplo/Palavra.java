package br.com.domtecpro.roomexemplo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tabela_de_palavras")
public class Palavra {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "palavra")
    private String mPalavra;

    public Palavra(@NonNull String palavra) {this.mPalavra = palavra;}

    public String getPalavra(){return this.mPalavra;}
}
