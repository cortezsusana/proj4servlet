/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.ar.proj3.grupob.jsf.util;

/**
 *
 * @author sofia susana
 */
public class NotRegistedEmailException extends Exception {

    public NotRegistedEmailException() {
        super("E-mail incorrect or not registed");
    }

}
