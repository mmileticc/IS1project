package Entiteti;

import Entiteti.AudioSnimak;
import Entiteti.Korisnik;
import Entiteti.OmiljeniPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-07-28T18:03:12")
@StaticMetamodel(Omiljeni.class)
public class Omiljeni_ { 

    public static volatile SingularAttribute<Omiljeni, OmiljeniPK> id;
    public static volatile SingularAttribute<Omiljeni, AudioSnimak> audio;
    public static volatile SingularAttribute<Omiljeni, Korisnik> korisnik;

}