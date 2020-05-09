package br.com.domtecpro.roomexemplo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ColaboradorViewModel mColaboradorViewModel;
    public static final int NOVA_COLABORADOR_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ColaboradorListAdapter adapter = new ColaboradorListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mColaboradorViewModel = new ViewModelProvider(this).get(ColaboradorViewModel.class);

        mColaboradorViewModel.getAllColaboradores().observe(this, new Observer<List<Colaborador>>() {
            @Override
            public void onChanged(@Nullable final List<Colaborador> colaboradores) {
                // Update the cached copy of the Colaboradores in the adapter.
                adapter.setColaboradores(colaboradores);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(MainActivity.this, NovoColaboradorActivity.class);
                startActivityForResult(intent, NOVA_COLABORADOR_ACTIVITY_REQUEST_CODE);*/
            }
        });
    }

   /* public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOVA_COLABORADOR_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Colaborador colaborador = new Colaborador(data.getStringExtra(NovoColaboradorActivity.EXTRA_REPLY));
            mColaboradorViewModel.insert(colaborador);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }*/
}
