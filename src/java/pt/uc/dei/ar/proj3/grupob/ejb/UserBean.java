/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.ar.proj3.grupob.ejb;

import java.io.Serializable;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import pt.uc.dei.ar.proj3.grupob.entities.Userplay;

/**
 *
 * @author sofia susana
 */
@Stateful
@SessionScoped
public class UserBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Userplay user;

    public UserBean() {
    }

    public Userplay getUser() {
        return user;
    }

    public void setUser(Userplay user) {
        this.user = user;
    }

}
