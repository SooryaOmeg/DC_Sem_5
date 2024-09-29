import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.sql.*;

class MySQLIntegration {

    Connection getConnection() throws RemoteException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/soorya";
        String username = "root";
        String password = "";

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(jdbcUrl,username,password);
            System.out.println("Connection to MySQL established!");
        }
        catch (SQLException e) {
            System.out.println("Sql Exception: " + e);
        }
        return conn;
    };

    public String getTableData(String name) throws RemoteException {

        MySQLIntegration sql = new MySQLIntegration();
        Connection conn = sql.getConnection();
        Statement stmt = null;
        StringBuilder tableData = new StringBuilder();

        try {
            stmt = conn.createStatement();
            String sqlSelect = "SELECT id, question, answer, " + name +" from question";
            ResultSet rs = stmt.executeQuery(sqlSelect);

            while (rs.next()) {
                String id = rs.getString("id");
                String question = rs.getString("question");
                String answer = rs.getString("answer");
                String reply = rs.getString(name);
                tableData.append(id).append("\t").append(question).append("\t").append(answer).append("\t").append(reply).append("\n");
            }
            rs.close();
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }
        return tableData.toString();
    }

    String Score(String name) throws RemoteException {
        MySQLIntegration sql = new MySQLIntegration();
        Connection conn = sql.getConnection();
        Statement stmt = null;
        StringBuilder tableData = new StringBuilder();
        try {
            stmt = conn.createStatement();
            String score = "select count(answer) as score from question where answer = " + name +" ;";
            ResultSet rs = stmt.executeQuery(score);
            while (rs.next()) {
                String score1 = rs.getString("score");
                tableData.append(score1);
            }
            rs.close();
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return tableData.toString();
    }
}

class HelloWorldImpl extends UnicastRemoteObject implements HelloWorld {

    protected HelloWorldImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello(String message) throws RemoteException {
            MySQLIntegration sql = new MySQLIntegration();
            return sql.getTableData(message);
    }

    @Override
    public String getScore(String message) throws RemoteException {
        MySQLIntegration sql = new MySQLIntegration();
        return sql.Score(message);
    }
}
