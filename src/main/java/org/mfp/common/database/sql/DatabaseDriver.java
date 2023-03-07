package org.mfp.common.database.sql;

import java.sql.*;

/**
 * Created by gawarho on 2/18/18.
 */
public class DatabaseDriver {
    Connection conn = null;

    public DatabaseDriver(String dbName, String userName, String password) {
        this("mysql", "localhost", "3306", dbName, userName, password);
    }

    public DatabaseDriver(String db, String hostname, String port, String dbName, String userName, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:" + db + "://" + hostname + ":" + port + "/" + dbName, userName, password);
            //System.out.println("Connected to " + db);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void init() {

    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean doStatement(String sqlCommand) throws SQLException {
        boolean retVal = false;
        Statement st = conn.createStatement();
        st.executeUpdate(sqlCommand);
        retVal = true;

        return retVal;
    }

    public ResultSet doQuery(String selectCommand) {
        ResultSet rs = null;

        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(selectCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public static void main(String[] args) {
        DatabaseDriver dd = new DatabaseDriver("usssa", "gawarho", "1q2w3e4r5t");
    }
}
