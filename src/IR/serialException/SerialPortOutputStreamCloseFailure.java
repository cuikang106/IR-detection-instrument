/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR.serialException;

/**
 *
 * @author Cui Kang
 */
public class SerialPortOutputStreamCloseFailure extends Exception{
    private static final long serialVersionUID = 1L;

    public SerialPortOutputStreamCloseFailure() {}

    @Override
    public String toString() {
        return "关闭串口对象的输出流出错!";
    }
}
