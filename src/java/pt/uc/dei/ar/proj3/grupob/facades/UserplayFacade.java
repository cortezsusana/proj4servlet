/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.ar.proj3.grupob.facades;

import pt.uc.dei.ar.proj3.grupob.entities.Userplay;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pt.uc.dei.ar.proj3.grupob.jsf.util.DuplicateEmailException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.Encripta;
import pt.uc.dei.ar.proj3.grupob.jsf.util.NotRegistedEmailException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.PasswordException;

/**
 *
 * @author sofia susana
 */
@Stateless
public class UserplayFacade extends AbstractFacade<Userplay> {

    @PersistenceContext(unitName = "Proj3GetplayPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserplayFacade() {
        super(Userplay.class);
    }

    /**
     * search the user with the given email
     * @param email
     * @return user
     */
    public Userplay getUserbyEmail(String email) {
        Query q = em.createNamedQuery("Userplay.findByEmail");
        q.setParameter("email", email);
        try {
            Userplay user = (Userplay) q.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * create a new user
     * @param user
     * @param passConf
     * @throws PasswordException
     * @throws DuplicateEmailException 
     */
    public void createUser(Userplay user, String passConf) throws PasswordException, DuplicateEmailException {
        if (getUserbyEmail(user.getEmail()) != null) {
            throw new DuplicateEmailException();
        } else if (!user.getPassword().equals(passConf)) {
            throw new PasswordException();
        } else {
            String passEncripted = Encripta.encriptaWithMD5(user.getPassword());
            user.setPassword(passEncripted);
            getEntityManager().persist(user);
        }
    }

    /**
     * verify if exists user with that email e password 
     * @param email
     * @param password
     * @return user
     * @throws NotRegistedEmailException
     * @throws PasswordException 
     */
    public Userplay searchLogged(String email, String password) throws NotRegistedEmailException, PasswordException {
        Userplay usertemp = getUserbyEmail(email);
        String passEncripted = Encripta.encriptaWithMD5(password);
        if (usertemp != null && !usertemp.getPassword().equals(passEncripted)) {
            throw new PasswordException();
        } else if (usertemp == null) {
            throw new NotRegistedEmailException();
        } else {
            return usertemp;
        }
    }

    /**
     * edit user
     * @param user
     * @param passConf
     * @param email
     * @throws PasswordException
     * @throws DuplicateEmailException 
     */
    public void editUserFacade(Userplay user, String passConf, String email) throws PasswordException, DuplicateEmailException {
        Userplay usertemp = getUserbyEmail(email);
        if (!user.getPassword().equals(passConf)) {
            throw new PasswordException();
        } else {
            if (usertemp == null || usertemp.getId() == user.getId()) {
                String passEncripted = Encripta.encriptaWithMD5(user.getPassword());
                user.setPassword(passEncripted);
                getEntityManager().merge(user);
            } else {
                throw new DuplicateEmailException();
            }
        }
    }
}
