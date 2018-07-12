/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Cui Kang
 */
public class IdManager {
    private int id;
    private static IdManager idManager_obj=null;
    
    public IdManager(){
        try{
        ResultSet rs=InitDB.getInitDB().getRs("select max(id) as id from panel_table");
        rs.next();
        id=rs.getInt("id");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static IdManager getIdManager(){
        if(idManager_obj==null){
            idManager_obj=new IdManager();
        }
        return idManager_obj;
    }
    
    public int getId(){
        id++;
        MainFrame.getMainFrame().getIdLabel().setText(String.valueOf(id));
        return id;
    }
}
