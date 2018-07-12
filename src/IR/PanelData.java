/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author Cui Kang
 */
public class PanelData {
    private Boolean success_fail;
    private String date;
    private String time;
    private int id;

    public PanelData(Boolean success_fail, int id) {
        this.success_fail = success_fail;
        this.id = id;
        Date dt=new Date();
        this.time=String.format("%tT", dt);
        this.date=String.format("%tF", dt);
    }

    public PanelData(Boolean success_fail, String date, String time, int id) {
        this.success_fail = success_fail;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    @Override
    public String toString() {
        return  "ID：" + id + ", 合格：" + success_fail + ", 时间：" + time + ", 日期：" + date ;
    }
    
    public void insertToDB(){
        try{
            Statement stateMent=InitDB.getInitDB().getStmt();
            stateMent.execute("insert into panel_table values("+id+","+success_fail+",'"+date+"','"+ time + "')");
            String sql = "select date,sum_success,sum_fail from sum_table where date='" + date+"'";   //SQL语句
            ResultSet set = stateMent.executeQuery(sql);         //将sql语句上传至数据库执行
            int sum_success;
            int sum_fail;
            if (set.next()) {                
                if (success_fail) {
                    sum_success= set.getInt(2)+1;
                    sum_fail=set.getInt(3);
                }
                else{                
                    sum_success= set.getInt(2);
                    sum_fail=set.getInt(3)+1;
                }
                sql = "update SUM_table set sum_success="+sum_success+",sum_fail="+sum_fail+" where date='" + date+"'"; 
            } else {
                if (success_fail) {
                    sum_success= 1;
                    sum_fail=0;
                }
                else{                
                    sum_success= 0;
                    sum_fail=1;
                }
                sql = "insert into SUM_table values('"+date+"',"+sum_success+","+sum_fail+")"; 
            }
            stateMent.executeUpdate(sql);
            System.out.println("已录入数据库");
            Log.logout("已录入数据库");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void check(){
        try{
            ResultSet resultSet=InitDB.getInitDB().getRs("select * from panel_table");

            while(resultSet.next()){
                resultSet.getBoolean("success_fail");
                PanelData panelDate=new PanelData(resultSet.getBoolean("success_fail"),resultSet.getString("date"),resultSet.getString("time"),resultSet.getInt("id"));
                System.out.println(panelDate.toString());
                Log.logout(panelDate.toString());
            }
            System.out.println("查询成功");
            Log.logout("查询成功");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void display(){
        if(success_fail){
            MainFrame.getMainFrame().getSuccessOrFailLabel().setText("合格");
        }else{
            MainFrame.getMainFrame().getSuccessOrFailLabel().setText("不合格");
        }
    }
}
