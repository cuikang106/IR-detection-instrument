/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Cui Kang
 */
public class TimeCount extends Thread{
    private static TimeCount timeCount_instance;
    private Thread thread;
    private volatile boolean exit = false;
    private final JLabel timeLabel=MainFrame.getMainFrame().getTimeLabel();
    
    public static TimeCount getTimeCount(){
        if(timeCount_instance==null)
            timeCount_instance=new TimeCount();
        return timeCount_instance;
    }
    
    @Override
    public void run(){
        System.out.println("run!");
        timeLabel.setText("Let's start!");
        for(int i=0;exit==false;++i){
            timeLabel.setText(String.valueOf(i));
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }           
    }
    
    @Override
    public void start(){
        System.out.println("start!");
        if (thread == null) {
            thread = new Thread (this);           
        }
        thread.start ();
    }
    
    public void beginCount(){
        this.exit=false;
        this.start();
    }
    
    public void stopCount(){
        this.exit=true;
        this.thread=null;
        timeLabel.setText("未开始");
    }
}
