package br.com.domtecpro.roomexemplo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de conexão externa ao dodos do colaborador
 * Acesso aos dados contidos no SQL Server
 */
public class ColaboradorDaoExterno {
    /**
     * Método de pesquisa dos colaboradores contidos na tabela Colaborador
     * do SQL Server
     * @return
     */
    public static List<Colaborador> pesquisarColaboradoresEx(){
        // Objeto que irá armazenar a lista de colaboradores
        List<Colaborador> lista = null;

        // Estrutura try/catch para pesquisar dados do colaborador
        try {
            // Objeto de definição da declaração de pesquisa
            PreparedStatement pst = ConexaoExterna.conectar().prepareStatement(
                    "Select * from Colaborador Order by id ASC"
            );
            // Objeto ResultSet que receberá os dados pesquisados
            ResultSet res = pst.executeQuery();
            // criação da lista declarada acima para recebimento dos dados
            lista = new ArrayList<Colaborador>();
            // Laço de repetição, se houver dados armazena na lista
            while(res.next()){
                // lista armazena dados
                // que apontam para a linha atual do ResultSet (res)
                lista.add(new Colaborador(
                        res.getInt(1), //id
                        res.getString(2), //nome
                        res.getString(3), //cargo
                        res.getString(4), //cpf
                        res.getString(5), //endereço
                        res.getString(6), //email
                        res.getString(7), //nome da foto
                        res.getBytes(8) //foto
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // retorna dados dos colaboradores para mostrar na tela ou lista
        return lista;
    }
}
