package br.com.domtecpro.roomexemplo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Classe de Negócio
 * A assinatura @Entity criará uma tabela correspondente
 * no SQLiteDatabase (via RoomDatabase)
 */
@Entity(tableName = "Colaborador")
public class Colaborador {

    /**
     * id (código) chave primária
     */
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @NonNull
    @ColumnInfo(name = "nome")
    private String nome;
    @NonNull
    @ColumnInfo(name = "cargo")
    private String cargo;
    @NonNull
    @ColumnInfo(name = "cpf")
    private String cpf;
    @NonNull
    @ColumnInfo(name = "endereco")
    private String endereco;
    @NonNull
    @ColumnInfo(name = "email")
    private String email;
    @Nullable
    @ColumnInfo(name = "caminhoFoto")
    private String caminhoFoto;
    @Nullable
    @ColumnInfo(name = "foto")
    private byte[] foto;

    public Colaborador(@NonNull int id, @NonNull String nome, @NonNull String cargo,
                        @NonNull String cpf, @NonNull String endereco,
                        @NonNull String email,
                        @Nullable String caminhoFoto,
                        @Nullable byte[] foto) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.cpf = cpf;
        this.endereco = endereco;
        this.email = email;
        this.caminhoFoto = caminhoFoto;
        this.foto = foto;
    }

    public Colaborador(@NonNull Colaborador colab) {
        this.id = colab.id;
        this.nome = colab.nome;
        this.cargo = colab.cargo;
        this.cpf = colab.cpf;
        this.endereco = colab.endereco;
        this.email = colab.email;
        this.caminhoFoto = colab.caminhoFoto;
        this.foto = colab.foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

}
