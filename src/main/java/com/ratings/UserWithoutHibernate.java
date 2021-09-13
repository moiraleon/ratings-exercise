package com.ratings;

import java.sql.*;
import java.util.Properties;

public class UserWithoutHibernate {

    // A user of our website; stored in a database.

    private int userId;
    private String email;
    private String password;

    public UserWithoutHibernate(int userId, String email, String password) {
        //Create a user, given id, email, and password
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public void changePassword(String password) throws SQLException {
        //Change password for the user.

        String query = "UPDATE users SET password = ? " +
                "WHERE user_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, password);
            statement.setInt(2, this.userId);
            statement.executeUpdate();
        }
    }

    public static UserWithoutHibernate getById(int userId) throws SQLException {
        //Get a user from database by ID and return instance.

        String query = "SELECT * FROM USERS WHERE user_id=?";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return new UserWithoutHibernate(resultSet.getInt("user_id"), resultSet.getString("email"), resultSet.getString("password"));
        }
    }
    @Override
    public String toString() {
        return String.format("user_id: %s, email: %s, password: %s", this.userId, this.email, this.password);
    }


    private static Connection getConnection() throws SQLException {

        String dbms = "postgresql";
        String serverName = "localhost";
        String portNumber = "5432";

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "newuser");
        connectionProps.put("password", "password");

        conn = DriverManager.getConnection(
                "jdbc:" + dbms + "://" +
                        serverName +
                        ":" + portNumber + "/ratings",
                connectionProps);

        System.out.println("Connected to database");
        return conn;
    }
}
