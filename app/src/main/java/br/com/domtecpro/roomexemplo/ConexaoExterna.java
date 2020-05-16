package br.com.domtecpro.roomexemplo;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de conexão externa ao SQLServer ou outra base externa desejada
 */
public class ConexaoExterna {
    /**
     * Método que realiza a conexão através de uma string definida
     * @return
     */
    public static Connection conectar(){
        // Objeto de conexão que será retornado
        Connection conn = null;

        try {
            // Solicita permissão para qualquer tráfego na rede
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            // Verifica se o driver de conexão esta importado
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            // Efetua a conexão através da string de conexão
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" +
                    "192.168.0.231;databaseName=PRAP3;user=sa;password=123456;");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
