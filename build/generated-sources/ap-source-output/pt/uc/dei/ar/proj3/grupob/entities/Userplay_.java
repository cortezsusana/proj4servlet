package pt.uc.dei.ar.proj3.grupob.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pt.uc.dei.ar.proj3.grupob.entities.Music;
import pt.uc.dei.ar.proj3.grupob.entities.Playlist;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-03-23T23:37:14")
@StaticMetamodel(Userplay.class)
public class Userplay_ { 

    public static volatile ListAttribute<Userplay, Music> musics;
    public static volatile SingularAttribute<Userplay, String> email;
    public static volatile SingularAttribute<Userplay, String> name;
    public static volatile SingularAttribute<Userplay, Long> userPlay_id;
    public static volatile ListAttribute<Userplay, Playlist> playlists;
    public static volatile SingularAttribute<Userplay, String> password;

}