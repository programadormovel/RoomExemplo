package br.com.domtecpro.roomexemplo;

import android.os.StrictMode;
import android.widget.Toast;

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
            StrictMode.ThreadPolicy politica;
            politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);
            /*// Verifica se o driver de conexão esta importado
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            // Efetua a conexão através da string de conexão
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" +
                    "192.168.0.111;databaseName=PRAP3;user=sa;password=123456;");
*/
            // MYSQL
            Class.forName("org.gjt.mm.mysql.Driver");
/*            conn = DriverManager.getConnection("jdbc:mysql://187.45.196.191:3306/prap3mysql&autoReconnect=true&failOverReadOnly=false&maxReconnects=10",
                    "prap3mysql","master2211##");*/

            conn = DriverManager.getConnection("jdbc:mysql://187.45.196.191:3306/prap3mysql?user=prap3mysql&password=master2211##&useUnicode=true&characterEncoding=UTF-8");

        } catch(SQLException e){
            e.getMessage();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
