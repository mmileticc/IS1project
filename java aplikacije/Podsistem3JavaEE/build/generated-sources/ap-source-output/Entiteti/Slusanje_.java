package Entiteti;

import Entiteti.AudioSnimak;
import Entiteti.Korisnik;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-07-28T18:03:12")
@StaticMetamodel(Slusanje.class)
public class Slusanje_ { 

    public static volatile SingularAttribute<Slusanje, Date> pocetakVreme;
    public static volatile SingularAttribute<Slusanje, Integer> sekundiTrajanje;
    public static volatile SingularAttribute<Slusanje, Integer> id;
    public static volatile SingularAttribute<Slusanje, AudioSnimak> audio;
    public static volatile SingularAttribute<Slusanje, Korisnik> korisnik;
    public static volatile SingularAttribute<Slusanje, Integer> sekundiOd;

}