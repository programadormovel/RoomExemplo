package br.com.domtecpro.roomexemplo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDaoExterno {
    public static List<Colaborador> pesquisarColaboradoresEx(){
        List<Colaborador> lista = null;

        try {
            PreparedStatement pst = ConexaoExterna.conectar().prepareStatement(
                    "Select * from Colaborador"
            );
            ResultSet res = pst.executeQuery();
            lista = new ArrayList<Colaborador>();

            while(res.next()){
                lista.add(new Colaborador(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6),
                        res.getString(7),
                        res.getBytes(8)
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
