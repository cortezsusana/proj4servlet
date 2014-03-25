/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.uc.dei.ar.proj3.grupob.facades;

import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pt.uc.dei.ar.proj3.grupob.entities.Music;
import pt.uc.dei.ar.proj3.grupob.jsf.util.MusicAlreadyExistException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.YearException;

/**
 *
 * @author sofia susana
 */
public class MusicFacadeTest {
    
    static MusicFacade instance;
    static EJBContainer container;
    
    public MusicFacadeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws NamingException {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (MusicFacade) container.getContext().lookup("java:global/classes/MusicFacade");
    }
    
    @AfterClass
    public static void tearDownClass() {
        container.close();
    }

    
    /**
     * Test of getSearchByTitle method, of class MusicFacade.
     */
    @Test
    public void testGetSearchByTitle() {
        System.out.println("getSearchByTitle");
        String title = "getSearchByTitle";
        List<Music> expResult = null;
        List<Music> result = instance.getSearchByTitle(title);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSearchByArtist method, of class MusicFacade.
     */
    @Test
    public void testGetSearchByArtist() {
        System.out.println("getSearchByArtist");
        String artist = "getSearchByArtist";
        List<Music> expResult = null;
        List<Music> result = instance.getSearchByArtist(artist);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSearchByBoth method, of class MusicFacade.
     */
    @Test
    public void testGetSearchByBoth() {
        System.out.println("testgetSearchByBoth");
        String both = "testgetSearchByBoth";
        List<Music> expResult = null;
        List<Music> result = instance.getSearchByBoth(both);
        assertEquals(expResult, result);
    }

    /**
     * Test of createMusic method, of class MusicFacade.
     * @throws pt.uc.dei.ar.proj3.grupob.jsf.util.YearException
     * @throws pt.uc.dei.ar.proj3.grupob.jsf.util.MusicAlreadyExistException
     */
    @Test
    public void testCreateMusic() throws YearException, MusicAlreadyExistException {
        System.out.println("createMusic");
         Music m = new Music();
        m.setAlbum("teste");
        m.setArtist("teste");
        m.setTitle("teste");
        m.setYearAlbum(2000);
        m.setPathMusic("teste.mp3");
        instance.create(m);
        
        Music result = instance.getSearchByArtist("teste").get(0);
        assertEquals(m.getAlbum(), result.getAlbum());
        assertEquals(m.getArtist(), result.getArtist());
        assertEquals(m.getTitle(), result.getTitle());
        assertEquals(m.getYearAlbum(), result.getArtist());

        instance.remove(m);
    }

    /**
     * Test of existsPath method, of class MusicFacade.
     */
    @Test
    public void testExistsPath() {
        System.out.println("existsPath");
        String path = "esteCaminhoNaoExiste";
        boolean expResult = false;
        boolean result = instance.existsPath(path);
        assertEquals(expResult, result);
    }
    
}
