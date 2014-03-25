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
import pt.uc.dei.ar.proj3.grupob.entities.Playlist;
import pt.uc.dei.ar.proj3.grupob.entities.Userplay;
import pt.uc.dei.ar.proj3.grupob.jsf.util.MusicAlreadyExistException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.YearException;

/**
 *
 * @author sofia susana
 */
public class PlaylistFacadeTest {

    static PlaylistFacade instance;
    static EJBContainer container;

    public PlaylistFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws NamingException {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (PlaylistFacade) container.getContext().lookup("java:global/classes/PlaylistFacade");
    }

    @AfterClass
    public static void tearDownClass() {
        container.close();
    }

    /**
     * Test of getUserPlaylists method, of class PlaylistFacade.
     */
    @Test
    public void testGetUserPlaylists() {
        System.out.println("getUserPlaylists");
        Userplay user = null;
        List<Playlist> expResult = null;
        List<Playlist> result = instance.getUserPlaylists(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlaylistOrderBy method, of class PlaylistFacade.
     */
    @Test
    public void testGetPlaylistOrderBy() {
        System.out.println("getPlaylistOrderBy");
        String column = "";
        Userplay user = null;
        List<Playlist> expResult = null;
        List<Playlist> result = instance.getPlaylistOrderBy(column, user);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlaylistOrderByDESC method, of class PlaylistFacade.
     */
    @Test
    public void testGetPlaylistOrderByDESC() {
        System.out.println("getPlaylistOrderByDESC");
        String column = "";
        Userplay user = null;
        List<Playlist> expResult = null;
        List<Playlist> result = instance.getPlaylistOrderByDESC(column, user);
        assertEquals(expResult, result);
    }

    /**
     * Test of addMusic method, of class PlaylistFacade.
     * @throws pt.uc.dei.ar.proj3.grupob.jsf.util.MusicAlreadyExistException
     */
    @Test
    public void testAddMusic() throws MusicAlreadyExistException {
        System.out.println("addMusic");
        Playlist p = new Playlist();
        p.setName("teste");
        Music m = new Music();
        m.setAlbum("teste");
        m.setArtist("teste");
        m.setTitle("teste");
        m.setYearAlbum(2000);
        m.setPathMusic("teste.mp3");
        instance.addMusic(p, m);
    }

    /**
     * Test of addNewMusic method, of class PlaylistFacade.
     * @throws pt.uc.dei.ar.proj3.grupob.jsf.util.MusicAlreadyExistException
     * @throws pt.uc.dei.ar.proj3.grupob.jsf.util.YearException
     */
    @Test
    public void testAddNewMusic() throws MusicAlreadyExistException, YearException {
        System.out.println("addNewMusic");
        Playlist entity = null;
        Music m = null;
        instance.addNewMusic(entity, m);
    }

    /**
     * Test of removeMusicFromPlays method, of class PlaylistFacade.
     */
    @Test
    public void testRemoveMusicFromPlays() {
        System.out.println("removeMusicFromPlays");
        Music m = null;
        instance.removeMusicFromPlays(m);
    }

    /**
     * Test of removeTotalMusic method, of class PlaylistFacade.
     */
    @Test
    public void testRemoveTotalMusic()  {
        System.out.println("removeTotalMusic");
        Music m = null;
        instance.removeTotalMusic(m);
    }

    /**
     * Test of deleteUser method, of class PlaylistFacade.
     */
    @Test
    public void testDeleteUser()  {
        System.out.println("deleteUser");
        Userplay user = null;
        instance.deleteUser(user);
    }

    
    /**
     * Test of create method, of class PlaylistFacade.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Playlist entity = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        PlaylistFacade instance = (PlaylistFacade)container.getContext().lookup("java:global/classes/PlaylistFacade");
        instance.create(entity);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class PlaylistFacade.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Playlist entity = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        PlaylistFacade instance = (PlaylistFacade)container.getContext().lookup("java:global/classes/PlaylistFacade");
        instance.edit(entity);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class PlaylistFacade.
     */
    @Test
    public void testRemove() throws Exception {
        System.out.println("remove");
        Playlist entity = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        PlaylistFacade instance = (PlaylistFacade)container.getContext().lookup("java:global/classes/PlaylistFacade");
        instance.remove(entity);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find method, of class PlaylistFacade.
     */
    @Test
    public void testFind() throws Exception {
        System.out.println("find");
        Object id = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        PlaylistFacade instance = (PlaylistFacade)container.getContext().lookup("java:global/classes/PlaylistFacade");
        Playlist expResult = null;
        Playlist result = instance.find(id);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAll method, of class PlaylistFacade.
     */
    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        PlaylistFacade instance = (PlaylistFacade)container.getContext().lookup("java:global/classes/PlaylistFacade");
        List<Playlist> expResult = null;
        List<Playlist> result = instance.findAll();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRange method, of class PlaylistFacade.
     */
    @Test
    public void testFindRange() throws Exception {
        System.out.println("findRange");
        int[] range = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        PlaylistFacade instance = (PlaylistFacade)container.getContext().lookup("java:global/classes/PlaylistFacade");
        List<Playlist> expResult = null;
        List<Playlist> result = instance.findRange(range);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class PlaylistFacade.
     */
    @Test
    public void testCount() throws Exception {
        System.out.println("count");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        PlaylistFacade instance = (PlaylistFacade)container.getContext().lookup("java:global/classes/PlaylistFacade");
        int expResult = 0;
        int result = instance.count();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
