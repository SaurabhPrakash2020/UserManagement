package com.xadmin.usermanagement.dao;

import java.sql.*;
import java.util.*;

import com.xadmin.usermanagement.bean.User;

public class UserDao {
    private static String jdbcURL = "jdbc:mysql://localhost:3306/userdb?useSSL=false";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "Saurabh9792@";
    private static String jdbcDriver = "com.mysql.cj.jdbc.Driver";

    private static final String INSERT_USERS_SQL = "INSERT INTO users" + "(name,email,country) VALUES " + "(?,?,?);";
    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id=?;";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where id= ?;";
    private static final String UPDATE_USERS_SQL = "update users set name=?,email=?,country=? where id=?;";

    public UserDao() {
    }

    protected static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void insertUser(User user) {
        System.out.println(INSERT_USERS_SQL);
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_USERS_SQL)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            System.out.println(ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public static User selectUser(int id) {
        User user = null;
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_ID);) {
            ps.setInt(1, id);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public static List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public static boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
                PreparedStatement st = connection.prepareStatement(UPDATE_USERS_SQL);) {
            System.out.println("updated User:" + st);
            st.setString(1, user.getName());
            st.setString(2, user.getEmail());
            st.setString(3, user.getCountry());
            st.setInt(4, user.getId());
            rowUpdated = st.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public static boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(DELETE_USERS_SQL);) {
            ps.setInt(1, id);
            rowDeleted = ps.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    private static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
