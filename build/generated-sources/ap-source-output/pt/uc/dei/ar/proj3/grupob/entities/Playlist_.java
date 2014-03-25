package pt.uc.dei.ar.proj3.grupob.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pt.uc.dei.ar.proj3.grupob.entities.Music;
import pt.uc.dei.ar.proj3.grupob.entities.Userplay;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-03-23T23:37:14")
@StaticMetamodel(Playlist.class)
public class Playlist_ { 

    public static volatile ListAttribute<Playlist, Music> musics;
    public static volatile SingularAttribute<Playlist, Userplay> userPlayID;
    public static volatile SingularAttribute<Playlist, String> name;
    public static volatile SingularAttribute<Playlist, Long> play_id;
    public static volatile SingularAttribute<Playlist, Date> datePlay;

}