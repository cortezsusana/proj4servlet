/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.ar.proj3.grupob.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import pt.uc.dei.ar.proj3.grupob.ejb.UserBean;
import pt.uc.dei.ar.proj3.grupob.entities.Music;
import pt.uc.dei.ar.proj3.grupob.entities.Playlist;
import pt.uc.dei.ar.proj3.grupob.facades.MusicFacade;
import pt.uc.dei.ar.proj3.grupob.facades.PlaylistFacade;
import pt.uc.dei.ar.proj3.grupob.jsf.util.FileUploadException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.JsfUtil;
import pt.uc.dei.ar.proj3.grupob.jsf.util.MusicAlreadyExistException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.Upload;
import pt.uc.dei.ar.proj3.grupob.jsf.util.YearException;

/**
 *
 * @author sofia susana
 */
@Named
@ViewScoped
public class EditController implements Serializable, Converter {

    private Music currentMusic;
    private Playlist currentPlay;
    private long idPlay;
    @Inject
    private PlaylistFacade playFacade;
    @Inject
    private MusicFacade musicFacade;
    @Inject
    private UserBean user;
    private UIPanel viewPlaylist;
    private UIPanel listPlaylist;
    private UIPanel newPlaylist;
    private List<Playlist> listPlays;
    private Part part;
    private Upload upload;

   
    public EditController() {
    }

    @PostConstruct
    public void initNewPlaylist() {
        listPlays = playFacade.getUserPlaylists(user.getUser());
        currentPlay = new Playlist();
        currentMusic = new Music();
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public UIPanel getNewPlaylist() {
        return newPlaylist;
    }

    public void setNewPlaylist(UIPanel newPlaylist) {
        this.newPlaylist = newPlaylist;
    }

    public List<Playlist> getListPlays() {
        return listPlays;
    }

    public void setListPlays(List<Playlist> listPlays) {
        this.listPlays = listPlays;
    }

    public UIPanel getListPlaylist() {
        return listPlaylist;
    }

    public void setListPlaylist(UIPanel listPlaylist) {
        this.listPlaylist = listPlaylist;
    }

    public Music getCurrentMusic() {
        return currentMusic;
    }

    public void setCurrentMusic(Music currentMusic) {
        this.currentMusic = currentMusic;
    }

    public Playlist getCurrentPlay() {
        return currentPlay;
    }

    public void setCurrentPlay(Playlist currentPlay) {
        this.currentPlay = currentPlay;
    }

    public PlaylistFacade getPlayFacade() {
        return playFacade;
    }

    public void setPlayFacade(PlaylistFacade playFacade) {
        this.playFacade = playFacade;
    }

    public MusicFacade getMusicFacade() {
        return musicFacade;
    }

    public void setMusicFacade(MusicFacade musicFacade) {
        this.musicFacade = musicFacade;
    }

    public long getIdPlay() {
        return idPlay;
    }

    public void setIdPlay(long idPlay) {
        this.idPlay = idPlay;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public UIPanel getViewPlaylist() {
        return viewPlaylist;
    }

    public void setViewPlaylist(UIPanel viewPlaylist) {
        this.viewPlaylist = viewPlaylist;
    }

    public Upload getUpload() {
        return upload;
    }

    public void setUpload(Upload upload) {
        this.upload = upload;
    }
    
    public void show(Playlist selected) {
        idPlay = selected.getId();
        currentPlay = selected;
        viewPlaylist.setRendered(true);
        listPlaylist.setRendered(false);
        newPlaylist.setRendered(false);
    }

    public String edit() {
        playFacade.edit(currentPlay);
        return "listPlaylists";
    }

    public void orderBy(String str, String column) {
        if (playFacade != null && str.equals("ASC")) {
            listPlays = playFacade.getPlaylistOrderBy(column, user.getUser());
        } else if (playFacade != null && str.equals("DESC")) {
            listPlays = playFacade.getPlaylistOrderByDESC(column, user.getUser());
        }
    }

    public List<Playlist> orderBySize(String str) {
        List<Playlist> order = playFacade.getUserPlaylists(user.getUser());
        if (playFacade != null && str.equals("ASC")) {
            Collections.sort(order, Playlist.sizeComparator);
            listPlays = order;
        } else if (playFacade != null && str.equals("DESC")) {
            Collections.sort(order, Playlist.sizeComparatorDESC);
            listPlays = order;
        }
        return null;
    }

    public String removeMusic(Music music) {
        currentPlay = playFacade.find(idPlay);
        currentPlay.getMusics().remove(music);
        playFacade.edit(currentPlay);
        return null;
    }

    
    public String createToPlaylist() {
        try {
            Playlist p= playFacade.find(idPlay);
            String target = Upload.handleFileUpload(part);
            currentMusic.setPathMusic(target);
            currentMusic.setUserPlayID(user.getUser());
            playFacade.addNewMusic(p, currentMusic);
            JsfUtil.addSuccessMessage("Music was successfully create and add to playlist " + p.getName().toUpperCase() + ".");
            return "listPlaylists";

        } catch (FileNotFoundException e) {
            Logger.getLogger(MusicListManagedBean.class.getName()).log(Level.SEVERE, null, e);
            JsfUtil.addErrorMessage(e.getMessage());
        } catch (IOException | FileUploadException |MusicAlreadyExistException | YearException e) {
            Logger.getLogger(MusicListManagedBean.class.getName()).log(Level.SEVERE, null, e);
            JsfUtil.addErrorMessage(e.getMessage());
        }
        return null;
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
