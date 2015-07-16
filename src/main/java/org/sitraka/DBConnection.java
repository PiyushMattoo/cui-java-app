package org.sitraka;

import java.sql.*;

public class DBConnection {
  private static volatile DBConnection instance = null;
  public Connection conn = null;

  protected DBConnection() {
    // Classic Singleton Pattern
        // Let's make a connection...
    System.out.println("CWD: " + System.getProperty("user.dir"));

    try {
      Class.forName("org.sqlite.JDBC");
      String dbURL = "jdbc:sqlite:target/cui/WEB-INF/classes/database.db";
      this.conn = DriverManager.getConnection(dbURL);
      System.out.println("Setting up connection");

      if (this.conn != null) {
        System.out.println("Connected to the database");
        DatabaseMetaData dm = (DatabaseMetaData) this.conn.getMetaData();
        System.out.println("Driver name: " + dm.getDriverName());
        System.out.println("Driver version: " + dm.getDriverVersion());
        System.out.println("Product name: " + dm.getDatabaseProductName());
        System.out.println("Product version: " + dm.getDatabaseProductVersion());

      }
      else {
        System.out.println("Not connected to the database.");
      }

    } catch (ClassNotFoundException ex) {
      System.out.println("ClassNotFoundException");
      ex.printStackTrace();
    } catch (SQLException ex) {
      System.out.println("SQLException");
      ex.printStackTrace();
    }
  }

  public static synchronized DBConnection getInstance() {
    if (instance == null) {
      instance = new DBConnection();
    }

    return instance;
  }

  public Object query(String queryString) {
    try {
      Statement stmt = this.conn.createStatement();
      ResultSet rs = stmt.executeQuery(queryString);
      return rs;
    }
    catch (SQLException ex) {
      System.out.println("SQLException");
      ex.printStackTrace();
      return null;
    }
  }

  public Object update(String queryString) {
    try {
      Statement stmt = this.conn.createStatement();
      return stmt.executeUpdate(queryString);
    }
    catch (Exception ex) {
      System.out.println("SQLException");
      ex.printStackTrace();
      return null;
    }
  }
}
