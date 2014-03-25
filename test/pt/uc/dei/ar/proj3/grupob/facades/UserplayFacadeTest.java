/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.ar.proj3.grupob.facades;

import javax.ejb.embeddable.EJBContainer;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.uc.dei.ar.proj3.grupob.entities.Userplay;
import pt.uc.dei.ar.proj3.grupob.jsf.util.DuplicateEmailException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.NotRegistedEmailException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.PasswordException;

/**
 *
 * @author sofia susana
 */
public class UserplayFacadeTest {

    public UserplayFacadeTest() {
    }

    static EJBContainer container;
    static UserplayFacade facade;

    @BeforeClass
    public static void setUpClass() throws Exception {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        facade = (UserplayFacade) container.getContext().lookup("java:global/classes/UserplayFacade");
    }

    @AfterClass
    public static void tearDownClass() {
        container.close();
    }

    @Test
    public void testUserCreate() throws PasswordException, DuplicateEmailException {
        System.out.println("testUserCreate");

        Userplay u = new Userplay();
       
        u.setName("teste");
        u.setEmail("teste@.gmail.com");
        u.setPassword("testePass");
        String passConf = "testePass";

        facade.create(u);
        Userplay result = facade.getUserbyEmail(u.getEmail());

        assertEquals(u.getEmail(), result.getEmail());
        assertEquals(u.getName(), result.getName());

        facade.remove(u);
    }

    // Userplay searchLogged(String email, String password) throws NotRegistedEmailException, PasswordException 
    @Test
    public void testGetUserByEmail() {
        System.out.println("testGetUserByEmail");

        String expectedEmail = "teste@teste.com";
        Userplay u = new Userplay();
        u.setName("teste");
        u.setEmail("teste@.gmail.com");
        u.setPassword("testePass");

        facade.create(u);

        Userplay result = facade.getUserbyEmail(expectedEmail);
        assertEquals(u, result);

        facade.remove(u);
    }

    @Test(expected = NotRegistedEmailException.class)
    public void testGetNonExistantUserByEmail() throws NotRegistedEmailException {
        System.out.println("testGetNonExistantUserByEmail");
        String email = "testesssssss@teste.com";
        Userplay result = facade.getUserbyEmail(email);
        assertNull(result);
    }

   


}
