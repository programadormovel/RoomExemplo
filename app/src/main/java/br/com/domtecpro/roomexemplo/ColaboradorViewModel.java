package br.com.domtecpro.roomexemplo;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Camada de Regra de Negócio
 * Inteligência do aplicativo
 * Não devemos carregar nenhum elemento da tela aqui
 * Enquanto a Atividade (Activity) respeita o seu ciclo de vida,
 * a ViewModel permanece ativa e alimentando a tela com dados
 * Para tornar a experiência do usuário satisfatória
 */
public class ColaboradorViewModel extends AndroidViewModel {

    // Objeto de consulta ao repositório de dados declarado
    private ColaboradorRepository mRepository;

    // Objeto de consulta dos colaboradores declarado
    private LiveData<List<Colaborador>> mAllColaboradores;

    /**
     * Construtor da ViewModel
     * Quando a ViewModel é criada em tempo de execução ela criar um objeto do Repositório de Dados
     * e realiza a consulta dos dados que virão deste repositório
     * O Repositório faz a consulta dos dados disponíveis localmente ou remotamente
     * @param application
     */
    public ColaboradorViewModel(Application application) {
        super(application);
        mRepository = new ColaboradorRepository(application);
        mAllColaboradores = mRepository.getAllColaboradores();
    }

    // Objeto de pesquisa de colaboradores, alimenta a tela através do Observador
    LiveData<List<Colaborador>> getAllColaboradores() { return mAllColaboradores; }

    // Método que solicita ao repositório a inserção de dados na base disponível
    // Este método é chamado pela MainActivity
    public void insert(Colaborador colaborador) {
        // Este método é chamado pela ViewModel, a atividade poder ser destruída,
        // mas a inserção será completada pela ViewModel através do Repositório
        mRepository.insert(colaborador);
    }
}
