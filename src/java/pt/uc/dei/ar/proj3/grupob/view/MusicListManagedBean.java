package pt.uc.dei.ar.proj3.grupob.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import pt.uc.dei.ar.proj3.grupob.jsf.util.MusicAlreadyExistException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.YearException;
import pt.uc.dei.ar.proj3.grupob.ejb.UserBean;
import pt.uc.dei.ar.proj3.grupob.entities.Music;
import pt.uc.dei.ar.proj3.grupob.entities.Playlist;
import pt.uc.dei.ar.proj3.grupob.jsf.util.JsfUtil;
import pt.uc.dei.ar.proj3.grupob.facades.MusicFacade;
import pt.uc.dei.ar.proj3.grupob.facades.PlaylistFacade;
import pt.uc.dei.ar.proj3.grupob.jsf.util.FileUploadException;
import pt.uc.dei.ar.proj3.grupob.jsf.util.Upload;

/**
 *
 * @author Sofia Susana
 */
@Named
@RequestScoped
public class MusicListManagedBean {

    private String option;
    private String search;
    private Part part;
    private List<Music> items = null;
    private List<Playlist> itemsPlays = null;
    private Music m;
    private Long id;
    private Playlist playTarget;
    private Long idPlay;
    private String url;

    @Inject
    private MusicFacade musicFacade;
    private Upload upload;
    @Inject
    private PlaylistFacade playFacade;
    @Inject
    private UserBean user;

    private String erro;
    private boolean viewP;

    /**
     * Creates a new instance of musicTableManagedBean
     */
    public MusicListManagedBean() {
    }

    @PostConstruct
    public void initializeMusicList() {
        allMusicList();
        this.m = new Music();
    }
    
    @PreDestroy
    public void destroyMusicList() {
        System.out.println("FOI DESTRUIDO " + m);
    }

    public String getServiceURL() {
        //definição da url do servlet
        FacesContext ctxt = FacesContext.getCurrentInstance();
        ExternalContext ext = ctxt.getExternalContext();
        return "http://" + ext.getRequestServerName() + ":" + ext.getRequestServerPort() 
                + ext.getApplicationContextPath() + "/popularservlet?teste=all&" +getRandomNumber();
    }

    public static int getRandomNumber() {
        return new Random().nextInt();
    }

    public String prepareCreate() {
        this.m = new Music();
        return "createMusic";
    }

    public PlaylistFacade getPlayFacade() {
        return playFacade;
    }

    public void setPlayFacade(PlaylistFacade playFacade) {
        this.playFacade = playFacade;
    }

    public String create() {
        try {
            Upload.validateFile(part);
            String target = Upload.handleFileUpload(part);
            m.setPathMusic(target);
            m.setUserPlayID(user.getUser());
            musicFacade.createMusic(m);
            JsfUtil.addSuccessMessage("Music was successfully created.");
            allMusicList();
            return "listMusics";
        } catch (FileNotFoundException e) {
            Logger.getLogger(MusicListManagedBean.class.getName()).log(Level.SEVERE, null, e);
            JsfUtil.addErrorMessage(e.getMessage());
        } catch (IOException | MusicAlreadyExistException | FileUploadException | YearException e) {
            Logger.getLogger(MusicListManagedBean.class.getName()).log(Level.SEVERE, null, e);
            JsfUtil.addErrorMessage(e.getMessage());
        }
        return null;//volta à mesma página
    }

    public String prepareCreateMusicFromPlaylist(Playlist p) {
        idPlay = p.getId();
        return "createMusicToPlay";
    }

    public String createToPlaylist() {
        try {
            Playlist p = playFacade.find(idPlay);
            String target = Upload.handleFileUpload(part);
            m.setPathMusic(target);
            m.setUserPlayID(user.getUser());
            playFacade.addNewMusic(p, m);
            JsfUtil.addSuccessMessage("Music was successfully created and added to playlist.");
            return "listPlaylists";

        } catch (FileNotFoundException e) {
            Logger.getLogger(MusicListManagedBean.class.getName()).log(Level.SEVERE, null, e);
            JsfUtil.addErrorMessage(e.getMessage());
        } catch (IOException | FileUploadException | MusicAlreadyExistException | YearException e) {
            Logger.getLogger(MusicListManagedBean.class.getName()).log(Level.SEVERE, null, e);
            JsfUtil.addErrorMessage(e.getMessage());
        }
        return null;//volta à mesma página
    }

    public String prepareEdit(Music m) {
        if (m.getUserPlayid().equals(user.getUser())) {
            this.m = m;
            id = m.getId();
            return "editMusic";
        } else {
            JsfUtil.addErrorMessage("You don´t have permission to change this music");
            return null;
        }
    }

    public String edit() {
        Music musicEdit = (Music) musicFacade.find(id);
        musicEdit.setTitle(m.getTitle());
        musicEdit.setArtist(m.getArtist());
        musicEdit.setAlbum(m.getAlbum());
        musicEdit.setYearAlbum(m.getYearAlbum());
        musicFacade.edit(musicEdit);
        JsfUtil.addSuccessMessage("Music was successfully updated.");
        allMusicList();
        return "listMusics";
    }

    public String remove(Music m) {
        if (m.getUserPlayid().equals(user.getUser())) {
            playFacade.removeTotalMusic(m);
            Upload.deleteFile(m.getPathMusic());
            JsfUtil.addSuccessMessage("Music was successfully deleted.");
            allMusicList();
            return "listMusics";
        } else {
            JsfUtil.addErrorMessage("You don´t have permission to delete this music");
            return null;
        }

    }

    public String allMusicList() {
        if (musicFacade != null) {
            items = new ArrayList(musicFacade.findAll());
            return "listMusics";
        }
        return null;
    }

    public String popularList() {
        if (musicFacade != null) {
            items = musicFacade.mostPopular();
            return "listPopular";
        }
        return null;
    }
    public List<Music> top10List() {
        List<Music> popular=null;
        if (musicFacade != null) {
            popular = musicFacade.top10List();     
        }
        return popular;
    }

    
    public List<Music> searchByTitle() {
        items = new ArrayList(musicFacade.getSearchByTitle(search));
        return items;
    }

    public List<Music> searchByArtist() {
        items = new ArrayList(musicFacade.getSearchByArtist(search));
        return items;
    }

    public List<Music> searchByBoth() {
        items = new ArrayList(musicFacade.getSearchByBoth(search));
        return items;
    }

    public String optionChosed() {
        switch (option) {
            case "1":
                searchByBoth();
                break;
            case "2":
                searchByTitle();
                break;
            case "3":
                searchByArtist();
                break;
        }
        return "listMusics";
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public boolean isViewP() {
        return viewP;
    }

    public void setViewP(boolean viewP) {
        this.viewP = viewP;
    }

    public String getErro() {
        return erro;
    }

    public List<Music> getItems() {
        return items;
    }

    public void setItems(List<Music> items) {
        this.items = items;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public List<Playlist> getItemsPlays() {
        return itemsPlays;
    }

    public void setItemsPlays(List<Playlist> itemsPlays) {
        this.itemsPlays = itemsPlays;
    }

    public MusicFacade getMusicFacade() {
        return musicFacade;
    }

    public void setMusicFacade(MusicFacade musicFacade) {
        this.musicFacade = musicFacade;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPlay() {
        return idPlay;
    }

    public void setIdPlay(Long idPlay) {
        this.idPlay = idPlay;
    }

    public Upload getUpload() {
        return upload;
    }

    public void setUpload(Upload upload) {
        this.upload = upload;
    }
    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public Music getM() {
        return m;
    }

    public void setM(Music m) {
        this.m = m;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
    public Playlist getPlayTarget() {
        return playTarget;
    }

    public void setPlayTarget(Playlist playTarget) {
        this.playTarget = playTarget;
    }

}
