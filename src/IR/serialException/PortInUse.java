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
public class PortInUse extends Exception{
    private static final long serialVersionUID = 1L;

    public PortInUse() {}

    @Override
    public String toString() {
        return "端口已被占用！打开串口操作未完成！";
    }
}
