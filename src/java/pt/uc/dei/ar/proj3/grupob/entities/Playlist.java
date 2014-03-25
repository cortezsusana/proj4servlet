/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.ar.proj3.grupob.entities;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author sofia
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Playlist.findAll", query = "SELECT p FROM Playlist p"),
    @NamedQuery(name = "Playlist.findByName", query = "SELECT p FROM Playlist p WHERE p.name = :name"),
    @NamedQuery(name = "Playlist.findByUser", query = "SELECT p FROM Playlist p WHERE p.userPlayID = :userPlayID"),
    @NamedQuery(name = "Playlist.findMusics", query = "SELECT musics m FROM Playlist p WHERE p.play_id = :play_id"),
   // @NamedQuery(name = "Playlist.OrderBySize", query = "SELECT p FROM Playlist p WHERE p.userPlayID = :userPlayID ORDER BY"),
    @NamedQuery(name = "Playlist.OrderByName", query = "SELECT p FROM Playlist p WHERE p.userPlayID = :userPlayID ORDER BY p.name"),
    @NamedQuery(name = "Playlist.OrderByDESCName", query = "SELECT p FROM Playlist p WHERE p.userPlayID = :userPlayID ORDER BY p.name DESC"),
    @NamedQuery(name = "Playlist.OrderByDate", query = "SELECT p FROM Playlist p WHERE p.userPlayID = :userPlayID ORDER BY p.datePlay"), 
    @NamedQuery(name = "Playlist.OrderByDESCDate", query = "SELECT p FROM Playlist p WHERE p.userPlayID = :userPlayID ORDER BY p.datePlay DESC") 
//@NamedQuery(name = "Playlist.OrderBySize", query = "SELECT COUNT(p.musics) FROM Playlist p ORDER BY COUNT(p.musics)"),
//@NamedQuery(name = "Playlist.OrderByDESCSize", query = "SELECT p.musics,COUNT(1) FROM Playlist p GROUP BY p.musics ORDER BY COUNT(1) DESC")
//@NamedQuery(name = "Playlist.OrderBySize", query = "SELECT p, COUNT(pm.musics_MUSIC_ID) FROM Playlist p, playlist_music pm WHERE p.play_id = :music_id ORDER BY COUNT(pm.musics_MUSIC_ID)"),
})
public class Playlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long play_id;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 150)
    @Column(length = 150, nullable = false)
    private String name;

    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date datePlay;

    @ManyToOne
    private Userplay userPlayID;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Music> musics;
    
    public Playlist() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDatePlay() {
        return datePlay;
    }

    public void setDatePlay(Date datePlay) {
        this.datePlay = datePlay;
    }

    public Userplay getUserPlayid() {
        return userPlayID;
    }

    public void setUserPlayid(Userplay userPlayid) {
        this.userPlayID = userPlayid;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }

    public Long getId() {
        return play_id;
    }

    public void setId(Long id) {
        this.play_id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (play_id != null ? play_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the play_id fields are not set
        if (!(object instanceof Playlist)) {
            return false;
        }
        Playlist other = (Playlist) object;
        if ((this.play_id == null && other.play_id != null) || (this.play_id != null && !this.play_id.equals(other.play_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Comparator<Playlist> sizeComparator = new Comparator<Playlist>() {
        @Override
        public int compare(Playlist o1, Playlist o2) {
            return o1.musics.size() - o2.musics.size();
        }
    };
    
    public static Comparator<Playlist> sizeComparatorDESC = new Comparator<Playlist>() {
        @Override
        public int compare(Playlist o1, Playlist o2) {
            return o2.musics.size() - o1.musics.size();
        }
    };
}
