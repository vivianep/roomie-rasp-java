import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

	
	public static Connection createConnection() throws SQLException, ClassNotFoundException{
		
		String url = "jdbc:mysql://localhost:3306/roomie"; //Nome da base de dados
		String user = "root"; //nome do usuário do MySQL
		String password = "12345678"; //senha do MySQL
		Connection conexao = null;
		conexao = DriverManager.getConnection(url, user, password);
		
		return conexao;
	}
	
	public ResultSet retrieveData(Connection conexao,String sql) throws SQLException{
		
		  Statement stmt = conexao.createStatement();
	      ResultSet rs = stmt.executeQuery(sql);

	      return rs;
	     
		
	}
}
