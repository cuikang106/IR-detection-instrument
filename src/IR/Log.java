/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

import javax.swing.JTextArea;

/**
 *
 * @author Cui Kang
 */
public class Log {
    private static JTextArea jTextAreaLog=MainFrame.getMainFrame().getTextArea();
    public static void logout(String string){
        jTextAreaLog.append(string+"\r\n");
    }
}
