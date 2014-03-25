package pt.uc.dei.ar.proj3.grupob.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pt.uc.dei.ar.proj3.grupob.entities.Userplay;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-03-23T23:37:14")
@StaticMetamodel(Music.class)
public class Music_ { 

    public static volatile SingularAttribute<Music, String> title;
    public static volatile SingularAttribute<Music, Userplay> userPlayID;
    public static volatile SingularAttribute<Music, Long> music_id;
    public static volatile SingularAttribute<Music, String> album;
    public static volatile SingularAttribute<Music, Integer> yearAlbum;
    public static volatile SingularAttribute<Music, String> pathMusic;
    public static volatile SingularAttribute<Music, String> artist;

}