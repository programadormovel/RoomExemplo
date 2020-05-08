package br.com.domtecpro.roomexemplo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PalavraViewModel mPalavraViewModel;
    public static final int NOVA_PALAVRA_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PalavraListAdapter adapter = new PalavraListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPalavraViewModel = new ViewModelProvider(this).get(PalavraViewModel.class);

        mPalavraViewModel.getAllPalavras().observe(this, new Observer<List<Palavra>>() {
            @Override
            public void onChanged(@Nullable final List<Palavra> palavras) {
                // Update the cached copy of the Palavras in the adapter.
                adapter.setPalavras(palavras);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NovaPalavraActivity.class);
                startActivityForResult(intent, NOVA_PALAVRA_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOVA_PALAVRA_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Palavra Palavra = new Palavra(data.getStringExtra(NovaPalavraActivity.EXTRA_REPLY));
            mPalavraViewModel.insert(Palavra);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
