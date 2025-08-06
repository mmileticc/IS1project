package Entiteti;

import Entiteti.Korisnik;
import Entiteti.Paket;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-07-28T18:03:12")
@StaticMetamodel(Pretplata.class)
public class Pretplata_ { 

    public static volatile SingularAttribute<Pretplata, Date> datumPocetka;
    public static volatile SingularAttribute<Pretplata, Integer> id;
    public static volatile SingularAttribute<Pretplata, Double> cena;
    public static volatile SingularAttribute<Pretplata, Paket> paket;
    public static volatile SingularAttribute<Pretplata, Korisnik> korisnik;

}