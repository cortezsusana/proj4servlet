/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.ar.proj3.grupob.view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import pt.uc.dei.ar.proj3.grupob.jsf.util.NotRegistedEmailException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.PasswordException;
import pt.uc.dei.ar.proj3.grupob.ejb.UserBean;
import pt.uc.dei.ar.proj3.grupob.entities.Userplay;
import pt.uc.dei.ar.proj3.grupob.facades.PlaylistFacade;
import pt.uc.dei.ar.proj3.grupob.facades.UserplayFacade;
import pt.uc.dei.ar.proj3.grupob.jsf.util.DuplicateEmailException;

/**
 *
 * @author sofia susana
 */
@Named
@RequestScoped
public class UserManagedBean {

    private String passConfirm;
    private String erro;
    private String option;
    private Userplay user;
    private Userplay userEdit;
    @Inject
    private UserBean userEJB;
    @Inject
    private UserplayFacade userFacade;
    @Inject
    private PlaylistFacade playFacade;

    public UserManagedBean() {
    }

    @PostConstruct
    public void init() {
        user = new Userplay();
        userEdit = userEJB.getUser();
    }

    public Userplay getUserEdit() {
        return userEdit;
    }

    public void setUserEdit(Userplay userEdit) {
        this.userEdit = userEdit;
    }

    public Userplay getUser() {
        return user;
    }

    public void setUser(Userplay user) {
        this.user = user;
    }

    public UserplayFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserplayFacade userFacade) {
        this.userFacade = userFacade;
    }

    public PlaylistFacade getPlayFacade() {
        return playFacade;
    }

    public void setPlayFacade(PlaylistFacade playFacade) {
        this.playFacade = playFacade;
    }

    public String getPassConfirm() {
        return passConfirm;
    }

    public void setPassConfirm(String passConfirm) {
        this.passConfirm = passConfirm;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public UserBean getUserEJB() {
        return userEJB;
    }

    public void setUserEJB(UserBean userEJB) {
        this.userEJB = userEJB;
    }

    public String optionChosed() {
        if (option.equals("2")) {
            return "editUser";
        }
        if (option.equals("3")) {
            playFacade.deleteUser(userEJB.getUser());
            invalidateSession();
            return "index";
        }
        if (option.equals("4")) {
            invalidateSession();
            return "index";
        }
        return null;
    }

    public String deletedUser() {
        playFacade.deleteUser(userEJB.getUser());
        invalidateSession();
        return "index";
    }
    
    private void invalidateSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
    }

    public String editUser() {
        try {
            userFacade.editUserFacade(userEdit, passConfirm, userEJB.getUser().getEmail());
            return "listMusics";
        } catch (PasswordException | DuplicateEmailException ex) {
            Logger.getLogger(UserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            erro = ex.getMessage();
            return null;
        }

    }

    public String searchLogged() {
        try {
            userEJB.setUser(userFacade.searchLogged(user.getEmail(), user.getPassword()));
            return "listMusics";
        } catch (NotRegistedEmailException | PasswordException ex) {
            Logger.getLogger(UserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            erro = ex.getMessage();
            return "index";
        }
    }

    @PreDestroy
    public void destroy() {
        System.out.println("FUI-ME EMBORA...");
    }

}
