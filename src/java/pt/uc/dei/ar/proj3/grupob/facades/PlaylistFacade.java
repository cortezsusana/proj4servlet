/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.ar.proj3.grupob.facades;

import java.util.List;
import pt.uc.dei.ar.proj3.grupob.entities.Playlist;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pt.uc.dei.ar.proj3.grupob.jsf.util.MusicAlreadyExistException;
import pt.uc.dei.ar.proj3.grupob.entities.Music;
import pt.uc.dei.ar.proj3.grupob.entities.Userplay;
import pt.uc.dei.ar.proj3.grupob.jsf.util.PlaylistAlreadyExistException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.YearException;

/**
 *
 * @author sofia susana
 */
@Stateless
public class PlaylistFacade extends AbstractFacade<Playlist> {

    @PersistenceContext(unitName = "Proj3GetplayPU")
    private EntityManager em;
    @Inject
    private MusicFacade musicFacade;
 
   
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaylistFacade() {
        super(Playlist.class);
    }

    /**
     * search all the playlist of user
     * @param user
     * @return list of user's playlists
     */
    public List<Playlist> getUserPlaylists(Userplay user) {
        Query q = em.createNamedQuery("Playlist.findByUser");
        q.setParameter("userPlayID", user);
        try {
            List<Playlist> play = (List<Playlist>) q.getResultList();
            return play;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * order de playlist list 
     * @param column - name of colum to order
     * @param user
     * @return list of playlists ordered ascendent
     */
    public List<Playlist> getPlaylistOrderBy(String column, Userplay user) {
        Query q = em.createNamedQuery("Playlist.OrderBy" + column);
        q.setParameter("userPlayID", user);
        try {
            List<Playlist> playlist = (List<Playlist>) q.getResultList();
            return playlist;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * order de playlist list 
     * @param column - name of colum to order
     * @param user
     * @return list of playlists ordered ascendent
     */
    public List<Playlist> getPlaylistOrderByDESC(String column, Userplay user) {
        Query q = em.createNamedQuery("Playlist.OrderByDESC" + column);
        q.setParameter("userPlayID", user);
        try {
            List<Playlist> playlist = (List<Playlist>) q.getResultList();
            return playlist;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Add a Music m to a Playlist entity
     * @param entity
     * @param m 
     * @throws MusicAlreadyExistException 
     */
    public void addMusic(Playlist entity, Music m) throws MusicAlreadyExistException {
        if ((find(entity.getId()).getMusics().contains(m))) {
            throw new MusicAlreadyExistException();
        } else {
            entity.getMusics().add(m);
            getEntityManager().merge(entity);
            m.getPlaylistCollection().add(entity);
            getEntityManager().merge(m);
        }
    }

    /**
     * Create a new music and Add the Music m to a Playlist entity
     * @param entity
     * @param m
     * @throws MusicAlreadyExistException
     * @throws YearException 
     */
    public void addNewMusic(Playlist entity, Music m) throws MusicAlreadyExistException, YearException {
        try {
            musicFacade.createMusic(m);
            entity.getMusics().add(m);
            getEntityManager().merge(entity);
            m.getPlaylistCollection().add(entity);
            getEntityManager().merge(m);
        }catch(MusicAlreadyExistException e){
            throw new MusicAlreadyExistException();
        }catch(YearException e){
            throw new YearException();
        }
    }

    /**
     * remove the music m from all playlists
     *
     * @param p
     * @param m
     */
    public void removeMusicFromPlays(Playlist p, Music m) {
        p.getMusics().remove(m);
        getEntityManager().merge(p);
        m.getPlaylistCollection().remove(p);
        getEntityManager().merge(m);

    }
    //remove the music
    public void removeMusicFromPlays(Music m) {
        for (Playlist p : findAll()) {
            if (p.getMusics().contains(m)) {
                p.getMusics().remove(m);
                getEntityManager().merge(p);
                m.getPlaylistCollection().remove(p);
                getEntityManager().merge(m);
            }
        }
    }

    /**
     * remove the music m from all playlists and delete music
     * @param m 
     */
    public void removeTotalMusic(Music m) {
        removeMusicFromPlays(m);
        getEntityManager().remove(getEntityManager().merge(m));
    }

    /**
     * remove all the user data and remove the user
     * @param user 
     */
    public void deleteUser(Userplay user) {
        for (Music m : user.getMusics()) {
            removeMusicFromPlays(m);
        }
        getEntityManager().remove(getEntityManager().merge(user));
    }    
    
    public void createPlaylist(Playlist p, Userplay u) throws PlaylistAlreadyExistException {
        if (!existPlaylist(p)) {
            getEntityManager().persist(p);
            u.getPlaylists().add(p);
            getEntityManager().merge(u);
        } else {
            throw new PlaylistAlreadyExistException();
        }
    }
    
    public void addTop10toPLay(Playlist p, Userplay u) throws PlaylistAlreadyExistException {
        try {
            createPlaylist(p, u);
            List<Music> topList = musicFacade.top10List();
            p.setMusics(topList);
            for (Music m : topList) {
                m.getPlaylistCollection().add(p);
                getEntityManager().merge(m);
            }
        } catch (PlaylistAlreadyExistException e) {
            throw new PlaylistAlreadyExistException();
        }
    }
    
    public void removePlaylist(Playlist p){
        for(Music m: p.getMusics()){
            m.getPlaylistCollection().remove(p);
            getEntityManager().merge(m);
        }
        getEntityManager().remove(getEntityManager().merge(p));
    }

    public MusicFacade getMusicFacade() {
        return musicFacade;
    }
    
    public void editPlaylist(Playlist entity, Userplay u) {
        getEntityManager().merge(entity);
        List<Playlist> p = u.getPlaylists();
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i).getPlay_id() == entity.getId()) {
                p.get(i).setName(entity.getName());
                getEntityManager().merge(u);
            }
        }
    }
    

    public boolean existPlaylist(Playlist p) {
        for (Playlist pl : findAll()) {
            if (pl.getUserPlayid().equals(p.getUserPlayID()) &&  pl.getName().equals(p.getName())) {
                return true;
            }
        }
        return false;
    }

}
