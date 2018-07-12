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
public class ReadDataFromSerialPortFailure extends Exception{
    private static final long serialVersionUID = 1L;

    public ReadDataFromSerialPortFailure() {}

    @Override
    public String toString() {
        return "从串口读取数据时出错!";
    }
}
