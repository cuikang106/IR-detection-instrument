/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author njau_
 */
public class InitDB {
    private String DBDriver = null;
    private String url = null;
    private String user = null;
    private String password = null;
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private static InitDB initDB_obj = null ;

    public InitDB() { // Java DB网络模式的访问
        this.DBDriver = "org.apache.derby.jdbc.ClientDriver";
        this.url = "jdbc:derby://localhost:1527/IR Database";
        this.user = "app";
        this.password = "app" ;
        try {
            Class.forName(DBDriver); // 加载驱动
            conn = DriverManager.getConnection(url, user, password);//建立连接
            stmt = conn.createStatement(); // 获取访问对象
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 需要传参数的公用访问
    public InitDB(String DBDriver,String url, String user,String password) {
        this.DBDriver = DBDriver ;
        this.url = url ;
        this.user = user ;
        this.password = password;
        try {
            Class.forName(DBDriver);
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static InitDB getInitDB() {
        if(initDB_obj==null)
            initDB_obj = new InitDB();
        return initDB_obj ;
    }

    public Connection getConn() {
        return conn;
    }

    public Statement getStmt() {
        return stmt;
    }
    
    public ResultSet getRs(String sql) { // 获取查询结果集
        if (sql.toLowerCase().indexOf("select") != -1) {
            try {
                rs = stmt.executeQuery(sql);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return rs;
    }

    public void closeDB() { // 关闭连接等，释放资源
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
