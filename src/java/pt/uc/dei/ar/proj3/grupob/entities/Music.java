
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.uc.dei.ar.proj3.grupob.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author sofia susana
 */
@Entity
@NamedQueries({
   @NamedQuery(name = "Music.findAll", query = "SELECT m FROM Music m"),
   @NamedQuery(name = "Music.findByUserplay", query = "SELECT m FROM Music m WHERE m.userPlayID = :userPlayID"),
   @NamedQuery(name = "Music.findByTitle", query = "SELECT m FROM Music m WHERE m.title = :title"),
   @NamedQuery(name = "Music.findByArtist", query = "SELECT m FROM Music m WHERE m.artist = :artist"),
   @NamedQuery(name = "Music.findByPath", query = "SELECT m FROM Music m WHERE m.pathMusic = :pathMusic"),
   @NamedQuery(name = "Music.OrderByArtist", query ="SELECT m FROM Music m ORDER BY m.artist"),
   @NamedQuery(name = "Music.OrderByDESCArtist", query ="SELECT m FROM Music m ORDER BY m.artist DESC"),
   @NamedQuery(name = "Music.OrderByTitle", query ="SELECT m FROM Music m ORDER BY m.title"),
   @NamedQuery(name = "Music.OrderByDESCTitle", query ="SELECT m FROM Music m ORDER BY m.title DESC"),
   @NamedQuery(name = "Music.SearchByTitle", query = "SELECT m FROM Music m WHERE m.title LIKE :title"),
   @NamedQuery(name = "Music.SearchByArtist", query = "SELECT m FROM Music m WHERE m.artist LIKE :artist"),
   @NamedQuery(name = "Music.SearchByBoth", query = "SELECT m FROM Music m WHERE m.artist LIKE :artist or m.title LIKE :title"),
    @NamedQuery(name = "Music.FindPopular", query = "SELECT m FROM Music m WHERE SIZE(m.playlistCollection) > 0 ORDER BY SIZE(m.playlistCollection) DESC")     
})
//@Table(name="tbl_music") // se definido atribui o nome dado à tabela, senão aparece como o nome da classe
public class Music implements Serializable, Comparable<Music>{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false) 
    @Column(name = "music_id")
    private Long music_id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min=1, max=150)
    @Column(length = 150, nullable= false)
    private String title;
    
    @Basic(optional = false)
    @NotNull
    @Size(min=1, max=150)
    @Column(length = 150, nullable= false)
    private String artist;
    
    @Basic(optional = false)
    @NotNull
    @Size(min=1, max=150)
    @Column(length = 150, nullable= false)
    private String album;
    
    @Basic(optional = false)
    @NotNull
    @Min(1900)
    @Digits(integer=4, fraction=0)
    @Column(name= "YEAR_ALBUM", nullable= false) 
    private int yearAlbum;
    
    @Size(max=150)
    @NotNull
    @Column(length = 150, nullable=false, unique = true, updatable = false)
    private String pathMusic;
    
    @ManyToMany(mappedBy = "musics", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Playlist> playlistCollection;
    
    @ManyToOne 
    @JoinColumn( name="userPlay_id", referencedColumnName = "userPlay_id")
    private Userplay userPlayID;
    

    public Music() {
    }

    public Long getMusic_id() {
        return music_id;
    }

    public void setMusic_id(Long music_id) {
        this.music_id = music_id;
    }

    public Userplay getUserPlayID() {
        return userPlayID;
    }

    public void setUserPlayID(Userplay userPlayID) {
        this.userPlayID = userPlayID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getYearAlbum() {
        return yearAlbum;
    }

    public void setYearAlbum(int yearAlbum) {
        this.yearAlbum = yearAlbum;
    }

    public String getPathMusic() {
        return pathMusic;
    }

    public void setPathMusic(String pathMusic) {
        this.pathMusic = pathMusic;
    }

    public Userplay getUserPlayid() {
        return userPlayID;
    }
    
    public Long getId() {
        return music_id;
    }

    public void setId(Long id) {
        this.music_id = id;
    }

    public List<Playlist> getPlaylistCollection() {
        return playlistCollection;
    }

    public void setPlaylistCollection(List<Playlist> playlistCollection) {
        this.playlistCollection = playlistCollection;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (music_id != null ? music_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the music_id fields are not set
        if (!(object instanceof Music)) {
            return false;
        }
        Music other = (Music) object;
        if ((this.music_id == null && other.music_id != null) || (this.music_id != null && !this.music_id.equals(other.music_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Music[ id=" + music_id + ", title: "+ title + ", userPLay: " + userPlayID + ", nºutilizações: " + this.getPlaylistCollection().size()+" ]";
    }

    @Override
    public int compareTo(Music o) {
        //ordena a lista de musicas mais utilizadas por ordem decrescente
        return o.getPlaylistCollection().size()-this.getPlaylistCollection().size();
    }
    
}
