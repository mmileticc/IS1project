package Entiteti;

import Entiteti.Ocena;
import Entiteti.Omiljeni;
import Entiteti.Slusanje;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-12T11:18:57")
@StaticMetamodel(AudioSnimak.class)
public class AudioSnimak_ { 

    public static volatile SingularAttribute<AudioSnimak, Integer> vlasnikId;
    public static volatile ListAttribute<AudioSnimak, Ocena> ocene;
    public static volatile SingularAttribute<AudioSnimak, Date> datumPostavljanja;
    public static volatile SingularAttribute<AudioSnimak, Integer> trajanje;
    public static volatile SingularAttribute<AudioSnimak, String> naziv;
    public static volatile ListAttribute<AudioSnimak, Slusanje> slusanja;
    public static volatile SingularAttribute<AudioSnimak, Integer> idSnimka;
    public static volatile ListAttribute<AudioSnimak, Omiljeni> omiljeni;

}