/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

import javax.swing.JOptionPane;

/**
 *
 * @author njau_
 */
public class Alarm {
    private static Alarm alarm;
    private int continous_fail;
    
    public void Alarm(){
        continous_fail = 0;
    }
    
    public static Alarm getAlarm(){
        if(alarm==null){
            alarm=new Alarm();
        }
        return alarm;
    }
    
    public void addData(PanelData paneldata){
        if(continous_fail<3){
            if(paneldata.getSuccess0rFail()){
                continous_fail=0;
            }else{
                continous_fail++;
            }
        }else{
            JOptionPane.showMessageDialog(null, "检测到连续不合格品，请暂停检查设备！", "警报", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void stopAlarm(){
        continous_fail=0;
    }
}
