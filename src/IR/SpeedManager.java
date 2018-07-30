/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JLabel;

/**
 *
 * @author njau_
 */
public class SpeedManager {
    private static SpeedManager speedmanager;
    private Queue<PanelData> queue ;
    private JLabel speed ;
    private int limit=5;
    
    public SpeedManager(){
        queue = new LinkedList<PanelData>();
        speed = MainFrame.getMainFrame().getSpeedLabel();
    }
    
    public void addData(PanelData paneldata){
        if(queue.size()<limit){
            queue.offer(paneldata);
        }else{
            long interval=paneldata.getDate().getTime()-queue.poll().getDate().getTime();
            speed.setText(String.valueOf(interval/1000/limit)+"s/件");
            addData(paneldata);
        }
    }
    
    public static SpeedManager getSpeedManager(){
        if(speedmanager==null)
            speedmanager=new SpeedManager();
        return speedmanager;
    }
    
    public void setlimit(int n){
        limit=n;
    }
    
    public void stopCounting(){
        queue.clear();
        speed.setText("null");
    }
}
