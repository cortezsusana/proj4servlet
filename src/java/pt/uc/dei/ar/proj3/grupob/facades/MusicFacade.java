/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.ar.proj3.grupob.facades;

import static java.util.Calendar.YEAR;
import java.util.GregorianCalendar;
import java.util.List;
import pt.uc.dei.ar.proj3.grupob.entities.Music;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pt.uc.dei.ar.proj3.grupob.jsf.util.MusicAlreadyExistException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.YearException;

/**
 *
 * @author sofia susana
 */
@Stateless
public class MusicFacade extends AbstractFacade<Music> {

    @PersistenceContext(unitName = "Proj3GetplayPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MusicFacade() {
        super(Music.class);
    }
    
    /**
     * search musics by title
     * @param title
     * @return list of music
     */
    public List<Music> getSearchByTitle(String title) {
        Query q = em.createNamedQuery("Music.SearchByTitle");
        q.setParameter("title", "%" + title + "%");
        try {
            List<Music> found = (List<Music>) q.getResultList();
            return found;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * search musics by artist
     * @param artist
     * @return list of Music
     */
    public List<Music> getSearchByArtist(String artist) {
        Query q = em.createNamedQuery("Music.SearchByArtist");
        q.setParameter("artist", "%" + artist + "%");
        try {
            List<Music> found = (List<Music>) q.getResultList();
            return found;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * search musics by title and artist
     * @param titleOrArtist 
     * @return list of Music
     */
    public List<Music> getSearchByBoth(String titleOrArtist) {
        Query q = em.createNamedQuery("Music.SearchByBoth");
        q.setParameter("artist", "%" + titleOrArtist + "%").setParameter("title", "%" + titleOrArtist + "%");
        try {
            List<Music> found = (List<Music>) q.getResultList();
            return found;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * create new music
     * @param entity
     * @throws YearException
     * @throws MusicAlreadyExistException 
     */
    public void createMusic(Music entity) throws YearException, MusicAlreadyExistException {
        int thisYear = new GregorianCalendar().get(YEAR);
      
        if (entity.getYearAlbum() > thisYear) {
            throw new YearException();
        } else if (existsPath(entity.getPathMusic())) {
            throw new MusicAlreadyExistException();
        } else {
            getEntityManager().persist(entity);
        }

    }

    /**
     * see if path already exist
     * @param path
     * @return 
     */
    public boolean existsPath(String path) {
        for (Music m : findAll()) {
            if (m.getPathMusic().equals(path)) {
                return true;
            }
        }
        return false;
    }
    


}
