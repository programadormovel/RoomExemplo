package br.com.domtecpro.roomexemplo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * Classe principal, primeira tela a aparecer no app
 * Carrega o RecyclerView, lista com os colaboradores existentes na base local
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Objeto ViewModel do Colaborador, encarregado da regra de negócio
     */
    private ColaboradorViewModel mColaboradorViewModel;
    private ColaboradorListAdapter adapter;
    public static final int NOVO_COLABORADOR_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Objeto de carregamento da lista de colaboradores
         */
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        /**
         * Adaptador responsável pelo carregamento dos dados da lista
         * Recebe o dados da fonte de dados, e realiza o de-para
         * Colocando cada dado em seu lugar, em cada item carregado da lista
         */
        adapter = new ColaboradorListAdapter(this);
        // Liga o Adaptador à lista gerada
        recyclerView.setAdapter(adapter);
        // Define o gerenciador de layout da lista carregada
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /**
         * Criação do objeto ViewModel declarado acima,
         * definindo o ViewModel que será vinculado à este contexto (ou esta tela)
         * gerenciado por esta classe Activity
         */
        mColaboradorViewModel = new ViewModelProvider(this).get(ColaboradorViewModel.class);

        /**
         * Este é o Observador controlado pelo ViewModel, responsável por garantir que os dados
         * da tela não serão perdidos, caso o ciclo de vida da atividade seja interrompido
         * Ele recebe uma lista de colaboradores, consultada pela ViewModel (ColaboradorViewModel)
         * e ajustada na tela pelo Adaptador (ColaboradorListAdapter)
         */
        mColaboradorViewModel.getAllColaboradores().observe(this, new Observer<List<Colaborador>>() {
            @SuppressLint("ResourceType")
            @Override
            public void onChanged(@Nullable final List<Colaborador> colaboradores) {
                // Update the cached copy of the Colaboradores in the adapter.
                adapter.setColaboradores(colaboradores);
            }
        });

        /**
         * Botão Floating Action Button (fab)
         * O Espectador (Listener) aguarda um clique no botão
         * E uma tela de cadastro de colaboradores será chamada
         */
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NovoColaboradorActivity.class);
                startActivityForResult(intent, NOVO_COLABORADOR_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOVO_COLABORADOR_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getBundleExtra(NovoColaboradorActivity.EXTRA_REPLY);
            Colaborador colaborador = new Colaborador(
                    (adapter.getItemCount() + 1),
                    bundle.getString("nome"),
                    bundle.getString("cargo"),
                    bundle.getString("cpf"),
                    bundle.getString("endereco"),
                    bundle.getString("email"),
                    bundle.getString("caminhoFoto"),
                    bundle.getByteArray("foto")
                    );
            mColaboradorViewModel.insert(colaborador);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
