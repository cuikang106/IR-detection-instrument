/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR.wifi;

import IR.IdManager;
import IR.Log;
import IR.PanelData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Cui Kang
 */
public class SocketClient implements Runnable{
    private Thread thread ;
    private Boolean stopMe = true;
    private Socket socket;
    
    private static SocketClient socketclient;
    
    public SocketClient(String host ,int port){
        try {
            this.socket=new Socket(host,port);
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void sendmessage(String string){
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void run() {
        try {
            // 要连接的服务端IP地址和端口
            // 与服务端建立连接
//            socket = new Socket(host, port);
            // 建立连接后获得输出流
//            OutputStream outputStream = socket.getOutputStream();
//            String message = "connected!";
//            socket.getOutputStream().write(message.getBytes("UTF-8"));

            InputStream inputstream = socket.getInputStream();
            BufferedReader read=new BufferedReader(new InputStreamReader(inputstream,"UTF-8"));
            System.out.println("开始接收" );
            while(stopMe){
                String line;
                if((line=read.readLine())!=null){
                    if(line.equals("end")){
                        break;
                    }
                    if(line.equals("Y")){
                        PanelData panelData=new PanelData(true,IdManager.getIdManager().getId());
                        System.out.println(panelData.toString());
                        Log.logout(panelData.toString());
                        panelData.insertToDB();
                        panelData.display();
                    }
                    if(line.equals("N")){
                        PanelData panelData=new PanelData(false,IdManager.getIdManager().getId());
                        System.out.println(panelData.toString());
                        Log.logout(panelData.toString());
                        panelData.insertToDB();
                        panelData.display();
                    }
                    System.out.println("get message from server:"+line);
                }
            }
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Socket连接失败！", "错误", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void start(){
        if(thread==null)
            thread = new Thread(this);
        thread.start();
    }
    
    public static void stopSocketClient(){
        if(socketclient!=null){
            socketclient.stopMe=false;
            try {
                socketclient.socket.close();
            } catch (IOException ex) {
                Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static SocketClient getSocketClient(String host ,int port){
        if(socketclient==null)
            socketclient = new SocketClient(host, port);
        return socketclient;
    } 
    
    public void setup(){
        stopMe=true;
        this.start();
    }
}
