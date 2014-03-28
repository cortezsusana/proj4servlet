/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.ar.proj3.grupob.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author sofia
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Userplay.findAll", query = "SELECT u FROM Userplay u"),
    @NamedQuery(name = "Userplay.findByName", query = "SELECT u FROM Userplay u WHERE u.name = :name"),
    @NamedQuery(name = "Userplay.findByEmail", query = "SELECT u FROM Userplay u WHERE u.email = :email")
})
public class Userplay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "userPlay_id")
    private Long userPlay_id;

    @Basic(optional = false)
    @NotNull
    @Size(max = 150)
    @Column(length = 150, nullable = false, updatable = true)
    private String name;

    @NotNull
    @Basic(optional = false)
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "Invalid email")
    @Column(nullable = false, unique = true)
    private String email;

    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userPlayID")
    private List<Music> musics;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userPlayID")
    private List<Playlist> playlists;

    public Userplay() {
    }

   
    public Long getUserPlay_id() {
        return userPlay_id;
    }

    public void setUserPlay_id(Long userPlay_id) {
        this.userPlay_id = userPlay_id;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return userPlay_id;
    }

    public void setId(Long id) {
        this.userPlay_id = id;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userPlay_id != null ? userPlay_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the userPlay_id fields are not set
        if (!(object instanceof Userplay)) {
            return false;
        }
        Userplay other = (Userplay) object;
        if ((this.userPlay_id == null && other.userPlay_id != null) || (this.userPlay_id != null && !this.userPlay_id.equals(other.userPlay_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Userplay[ id=" + userPlay_id + " ]";
    }

}
