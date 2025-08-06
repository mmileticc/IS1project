package Entiteti;

import Entiteti.AudioSnimak;
import Entiteti.Korisnik;
import Entiteti.OcenaPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-07-28T18:03:12")
@StaticMetamodel(Ocena.class)
public class Ocena_ { 

    public static volatile SingularAttribute<Ocena, Date> datumVreme;
    public static volatile SingularAttribute<Ocena, OcenaPK> id;
    public static volatile SingularAttribute<Ocena, AudioSnimak> audio;
    public static volatile SingularAttribute<Ocena, Integer> ocena;
    public static volatile SingularAttribute<Ocena, Korisnik> korisnik;

}