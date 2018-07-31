/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR.serialPort;

import IR.IdManager;
import IR.Log;
import IR.PanelData;
import IR.serialException.NoSuchPort;
import IR.serialException.NotASerialPort;
import IR.serialException.PortInUse;
import IR.serialException.ReadDataFromSerialPortFailure;
import IR.serialException.SerialPortInputStreamCloseFailure;
import IR.serialException.SerialPortParameterFailure;
import IR.serialException.TooManyListeners;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author njau_
 */
public class DataView extends javax.swing.JFrame {
    
    private ArrayList<String> commList = null;    //保存可用端口号
    private SerialPort serialPort = null;    //保存串口对象
    private static DataView dataView;
    
    /**
     * Creates new form DateView
     */
    public DataView() {
        initComponents();
        commList=SerialTool.findPort();
    }
    
    public static DataView getDataView(){
        if(dataView==null){
            dataView=new DataView();
        }
        dataView.setLocationRelativeTo(null);
        return dataView;
    }

    public void showframe(){
        dataView.setVisible(true);
    }
    
    public static void closeDataView(){
        if(dataView!=null)
            dataView.dispose();
    }
    
    private class SerialListener implements SerialPortEventListener{
        @Override
        public void serialEvent(SerialPortEvent serialPortEvent){
            switch(serialPortEvent.getEventType()){
                case SerialPortEvent.BI: // 10 通讯中断
                    JOptionPane.showMessageDialog(null, "与串口设备通讯中断", "错误", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case SerialPortEvent.OE: // 7 溢位（溢出）错误

                case SerialPortEvent.FE: // 9 帧错误

                case SerialPortEvent.PE: // 8 奇偶校验错误

                case SerialPortEvent.CD: // 6 载波检测

                case SerialPortEvent.CTS: // 3 清除待发送数据

                case SerialPortEvent.DSR: // 4 待发送数据准备好了

                case SerialPortEvent.RI: // 5 振铃指示

                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空d
                    break;
                
                case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
                    byte[] data = null;
                    try{
                        if (serialPort==null){
                            JOptionPane.showMessageDialog(null, "串口对象为空！监听失败！", "错误", JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            data=SerialTool.readFromPort(serialPort);
                            
                            //自定义解析过程
                            if(data==null||data.length<1){
                                JOptionPane.showMessageDialog(null, "未读取到有效数据！", "错误", JOptionPane.INFORMATION_MESSAGE);                              
                                System.exit(0);
                            }else{
                                String dataOriginal=new String(data);   //将字节数组数据转换位为保存了原始数据的字符串
                                String dataValid="";
                                String [] elements=null;
                                //解析数据
                                if(dataOriginal.charAt(0)=='*'){
                                    dataValid=dataOriginal.substring(1);
                                    elements = dataValid.split(" ");
                                    if (elements == null || elements.length < 1) {    //检查数据是否解析正确
                                        JOptionPane.showMessageDialog(null, "数据解析出错", "错误", JOptionPane.INFORMATION_MESSAGE);
                                        System.exit(0);
                                    }else{
                                        //使用分割得到的数据,根据预定义的字符串内容执行逻辑
                                        if(elements[0].equals("OK")){   //检测结果为合格品
                                            PanelData panelData=new PanelData(true,IdManager.getIdManager().getId());
                                            System.out.println(panelData.toString());
                                            Log.logout(panelData.toString());
                                            panelData.insertToDB();
                                            //panelData.check();
                                            panelData.display();
                                        }else if(elements[0].equals("inferior")){   //检测结果为次品
                                            PanelData panelData=new PanelData(false,IdManager.getIdManager().getId());
                                            System.out.println(panelData.toString());
                                            Log.logout(panelData.toString());
                                            panelData.insertToDB();
                                            //panelData.check();
                                            panelData.display();
                                        }
                                    }
                                }
                            }
                        }
                    }catch(ReadDataFromSerialPortFailure | SerialPortInputStreamCloseFailure e){
                        //do something
                        System.exit(0);
                    }
                    break;
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        showSerialButton = new javax.swing.JButton();
        openSerialButton = new javax.swing.JButton();
        bpsInput = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        commInput = new javax.swing.JTextField();

        showSerialButton.setText("现有串口");
        showSerialButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showSerialButtonActionPerformed(evt);
            }
        });

        openSerialButton.setText("打开串口");
        openSerialButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openSerialButtonActionPerformed(evt);
            }
        });

        bpsInput.setColumns(6);

        jLabel1.setText("波特率");

        jLabel2.setText("串口名");

        commInput.setColumns(6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showSerialButton)
                    .addComponent(openSerialButton)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(bpsInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(commInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(showSerialButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(openSerialButton)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(commInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(bpsInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showSerialButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showSerialButtonActionPerformed
        // TODO add your handling code here:
        Log.logout("当前可用串口：");
        for(String tmp : commList){
            System.out.println(tmp);
            Log.logout(tmp);
        }
    }//GEN-LAST:event_showSerialButtonActionPerformed

    private void openSerialButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openSerialButtonActionPerformed
        // TODO add your handling code here:
        String commName;
        String bpsStr;
        commName = commInput.getText();
        bpsStr=bpsInput.getText();
        //检查串口名称是否获取正确
        if(commName==null||commName.equals("")){
            JOptionPane.showMessageDialog(null, "没有输入有效串口！", "错误", JOptionPane.INFORMATION_MESSAGE);
        }else{
            //检查波特率是否获取正确
            if(bpsStr==null||bpsStr.equals("")){
                JOptionPane.showMessageDialog(null, "没有输入有效波特率！", "错误", JOptionPane.INFORMATION_MESSAGE);
            }else{
                //串口名、波特率均获取正确时
                int bps = Integer.parseInt(bpsStr);
                try{
                    //获取指定端口名及波特率的串口对象
                    serialPort = SerialTool.openPort(commName, bps);
                    //在该串口对象上添加监听器
                    SerialTool.addListener(serialPort, new SerialListener());
                    JOptionPane.showMessageDialog(null, "监听成功，稍后显示有效数据","提示", JOptionPane.INFORMATION_MESSAGE);
                }catch(SerialPortParameterFailure | NotASerialPort | NoSuchPort | PortInUse | TooManyListeners e){
                    JOptionPane.showMessageDialog(null, e, "错误", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_openSerialButtonActionPerformed

    public Boolean hasSerialPort(){
        if(this.serialPort==null){
            return false;
        }
        return true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DataView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DataView v= new DataView();
                v.setLocationRelativeTo(null);
                v.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bpsInput;
    private javax.swing.JTextField commInput;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton openSerialButton;
    private javax.swing.JButton showSerialButton;
    // End of variables declaration//GEN-END:variables
}
