package com.example.redshift;

import java.sql.*;


public class App 
{
    public static void main(String[] args) throws SQLException {
        String connectionString = args[0];
        String user = args[1];
        String password = args[2];

        Connection conn = DriverManager.getConnection(connectionString, user, password);
        System.out.println("Attempting working query");
        workingQuery(conn);
        System.out.println("Attempting broken query");
        brokenQuery(conn);
        System.out.println("Attempting another broken query");
        brokenQuery(conn);
        System.out.println("Done!");
        conn.close();
    }

    /*
     * A working example of a simplified query which checks if a variable is null. Prints "2024-11-05"
     */
    public static void workingQuery(Connection conn) throws SQLException {
        PreparedStatement psts = conn.prepareStatement("select getdate(), date_trunc('day', getdate()) from sys_query_history where ? is null limit 1;");
        psts.setNull(1, Types.NUMERIC);
        ResultSet rs = psts.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getDate("date_trunc"));
        }
    }

    /*
     * This is how clojure.jdbc sets null values on queries. Prints "2000-01-01"
     */
    public static void brokenQuery(Connection conn) throws SQLException {
        PreparedStatement psts = conn.prepareStatement("select getdate(), date_trunc('day', getdate()) from sys_query_history where ? is null limit 1;");
        psts.setObject(1, null);
        ResultSet rs = psts.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getDate("date_trunc"));
        }
    }

    /*
     * PreparedStatement will call setNull with Type.OTHER (1111) internally when setObject(i, null) is called. Prints "2000-01-01"
     */
    public static void brokenQuery2(Connection conn) throws SQLException {
        PreparedStatement psts = conn.prepareStatement("select getdate(), date_trunc('day', getdate()) from sys_query_history where ? is null limit 1;");
        psts.setNull(1, Types.OTHER);
        ResultSet rs = psts.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getDate("date_trunc"));
        }
    }

}