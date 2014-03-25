/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.ar.proj3.grupob.view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import pt.uc.dei.ar.proj3.grupob.entities.Userplay;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pt.uc.dei.ar.proj3.grupob.jsf.util.DuplicateEmailException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.PasswordException;
import pt.uc.dei.ar.proj3.grupob.facades.UserplayFacade;

/**
 *
 * @author sofia susana
 */
@Named
@RequestScoped
public class SignUpManagedBean {

    @Inject
    private UserplayFacade userFacade;
    private String passConf;
    private Userplay user;
    private String erro;

    public SignUpManagedBean() {
    }

    @PostConstruct
    public void initUser() {
        user = new Userplay();
    }

    public UserplayFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserplayFacade userFacade) {
        this.userFacade = userFacade;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }

    public Userplay getUser() {
        return user;
    }

    public void setUser(Userplay user) {
        this.user = user;
    }

    public String getPassConf() {
        return passConf;
    }

    public void setPassConf(String passConf) {
        this.passConf = passConf;
    }

    public String createUser() {
        try {
            userFacade.createUser(user, passConf);
            return "index";
        } catch (PasswordException | DuplicateEmailException ex) {
            Logger.getLogger(SignUpManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            erro = ex.getMessage();
            return "signup";
        }
    }

}
