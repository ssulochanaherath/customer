package lk.ijse.customer.db;

import java.sql.*;

public class DbConnection {
    private static DbConnection dbConnection;

    private Connection connection;

    private DbConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/customer",
                "root",
                "Ijse@1234"
        );
    }

    public static DbConnection getInstance() throws SQLException {
        return ( null == dbConnection ) ? dbConnection = new DbConnection() : dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnections(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }
}
