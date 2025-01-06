package VeriTabanı_Baglantısı;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class veribaglanti {

	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=UzaktanSinavSistemi;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC sürücüsü bulunamadı: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Bağlantı hatası:");
            yazdirSQLException(e);
        }
        return connection;
    }

    // SQLException detaylarını yazdıran metot
    public static void yazdirSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                System.err.println("SQL Hatası Mesajı: " + e.getMessage());
                System.err.println("SQL Durum Kodu: " + ((SQLException) e).getSQLState());
                System.err.println("Hata Kodu: " + ((SQLException) e).getErrorCode());
                Throwable sebep = ex.getCause();
                if (sebep != null) {
                    System.err.println("Neden: " + sebep);
                }
            }
        }
    }
}
