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
public class NotASerialPort extends Exception {
    private static final long serialVersionUID = 1L;

    public NotASerialPort() {}

    @Override
    public String toString() {
        return "端口指向设备不是串口类型！打开串口操作未完成！";
    }
}
