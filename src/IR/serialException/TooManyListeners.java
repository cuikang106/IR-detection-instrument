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
public class TooManyListeners extends Exception{
    private static final long serialVersionUID = 1L;

    public TooManyListeners() {}

    @Override
    public String toString() {
        return "监听类对象过多！";
    }
}
