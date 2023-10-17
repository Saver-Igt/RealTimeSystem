package org.example.dataBase;

import java.sql.*;

public class PostgresDataBase {
    private final String url = "jdbc:postgresql://localhost:5432/PostgresDataBase";
    private final String user = "postgres";
    private final String password = "456";
    /*
    public static void main(String[] args) throws  Exception{
        Connection connection  = DriverManager.getConnection(url, user, password);

        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM test.cashbox");

        while (results.next()) {
            Integer id = results.getInt(1);
            String name = results.getString(2);
            System.out.println(results.getRow() + ". " + id + "\t"+ name);
        }
        connection.close();
    }
     */
    public Connection getNewConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
