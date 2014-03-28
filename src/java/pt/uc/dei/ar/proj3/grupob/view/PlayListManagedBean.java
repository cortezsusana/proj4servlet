/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.ar.proj3.grupob.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import pt.uc.dei.ar.proj3.grupob.ejb.UserBean;
import pt.uc.dei.ar.proj3.grupob.jsf.util.MusicAlreadyExistException;

import pt.uc.dei.ar.proj3.grupob.entities.Music;
import pt.uc.dei.ar.proj3.grupob.entities.Playlist;
import pt.uc.dei.ar.proj3.grupob.facades.MusicFacade;
import pt.uc.dei.ar.proj3.grupob.facades.PlaylistFacade;
import pt.uc.dei.ar.proj3.grupob.jsf.util.JsfUtil;
import pt.uc.dei.ar.proj3.grupob.jsf.util.PlaylistAlreadyExistException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.YearException;

/**
 *
 * @author sofia susana
 */
@Named
@RequestScoped
public class PlayListManagedBean implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PlaylistFacade playFacade;
    @Inject
    private MusicFacade musicFacade;
    private Playlist newPlay,currentPlaylist;
    private List<Playlist> listPlays;
    @Inject
    private UserBean user;
    private String erro;
    private long idPlay;
    private long musicId;

    public PlayListManagedBean() {
    }

    @PostConstruct
    public void initNewPlaylist() {
        listPlays = playFacade.getUserPlaylists(user.getUser());
        currentPlaylist = new Playlist();
        newPlay = new Playlist();
    }

    public MusicFacade getMusicFacade() {
        return musicFacade;
    }

    public void setMusicFacade(MusicFacade musicFacade) {
        this.musicFacade = musicFacade;
    }

    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    public void setCurrentPlaylist(Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    public long getIdPlay() {
        return idPlay;
    }

    public void setIdPlay(long idPlay) {
        this.idPlay = idPlay;
    }
    public PlaylistFacade getPlayFacade() {
        return playFacade;
    }

    public void setPlayFacade(PlaylistFacade playFacade) {
        this.playFacade = playFacade;
    }

    public Playlist getNewPlay() {
        return newPlay;
    }

    public void setNewPlay(Playlist newPlay) {
        this.newPlay = newPlay;
    }

    public List<Playlist> getListPlays() {
        return listPlays;
    }

    public void setListPlays(List<Playlist> listPlays) {
        this.listPlays = listPlays;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String remove(Playlist selected) {
        playFacade.removePlaylist(selected);
        listPlays = playFacade.getUserPlaylists(user.getUser());
        return "listPlaylists";
    }

    public void addMusic(Music newMusic) {
        try {
            playFacade.addNewMusic(currentPlaylist, newMusic);
            JsfUtil.addSuccessMessage("Music was successfully add to playlist "+currentPlaylist.getName().toUpperCase());
        } catch (MusicAlreadyExistException | YearException ex) {
            Logger.getLogger(PlayListManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            erro=ex.getMessage();
        }
    }
    
    public String createPlay() {
        try {
            newPlay.setUserPlayid(user.getUser());
            newPlay.setDatePlay(new Date());
            playFacade.createPlaylist(newPlay,user.getUser());
            //userPlaylists();
            currentPlaylist=newPlay;
            newPlay = null;
        } catch (PlaylistAlreadyExistException e) {
            Logger.getLogger(PlayListManagedBean.class.getName()).log(Level.SEVERE, null, e);
            //JsfUtil.addErrorMessage(e.getMessage());
            erro = e.getMessage();
        }
        return "listPlaylists";
    }
    
    public String addPlayTop10() {
        try {
            newPlay.setName("TOP10");
            newPlay.setUserPlayid(user.getUser());
            newPlay.setDatePlay(new Date());
            playFacade.addTop10toPLay(newPlay, user.getUser());
            currentPlaylist=newPlay;
            newPlay = null;
        } catch (PlaylistAlreadyExistException e) {
            Logger.getLogger(PlayListManagedBean.class.getName()).log(Level.SEVERE, null, e);
            //JsfUtil.addErrorMessage(e.getMessage());
            erro = e.getMessage();
        }
        return "listPlaylists";
    }
    /*
     Find users playlist
     */
    public List<Playlist> userPlaylists() {
        listPlays = user.getUser().getPlaylists();
        return listPlays;
    }

    /*
     List all musics of the user and allows to choose a playlist
     */
    public String chooseList(Music m) {
        if (!listPlays.isEmpty()) {
            listPlays = user.getUser().getPlaylists();
            erro = null;
            musicId = m.getId();
            return "addMusicToPlaylist";
        } else {
            JsfUtil.addSuccessMessage("Please insert at least one playlist ");
            return null;
        }
    }

    /*
     adiciona musicas à playlist seleccionada
     */
    public String addMusicToPlay() {
        try {
            playFacade.addMusic(currentPlaylist, musicFacade.find(musicId));
            JsfUtil.addSuccessMessage("Music was successfully add to playlist "+currentPlaylist.getName().toUpperCase().toString());
        } catch (MusicAlreadyExistException e) {
            Logger.getLogger(MusicListManagedBean.class.getName()).log(Level.SEVERE, null, e);
            JsfUtil.addErrorMessage(e.getMessage()+" in the playlist named "+currentPlaylist.getName().toUpperCase().toString()+".");
        }
        return "listMusics";
    }
    
  

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        if (!value.matches("\\d+")) {
            throw new ConverterException("The value is not a valid playlist ID: " + value);
        }

        Long id = Long.parseLong(value);
        return playFacade.find(id);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }

        if (!(value instanceof Playlist)) {
            throw new ConverterException("The value is not a Playlist: " + value);
        }

        String id = ((Playlist) value).getId().toString();
        return (id != null) ? id.toString() : null;
    }

}
