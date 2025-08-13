package Entiteti;

import Entiteti.AudioSnimak;
import Entiteti.AudioSnimakKategorijaPK;
import Entiteti.Kategorija;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-12T11:06:14")
@StaticMetamodel(AudioSnimakKategorija.class)
public class AudioSnimakKategorija_ { 

    public static volatile SingularAttribute<AudioSnimakKategorija, AudioSnimakKategorijaPK> id;
    public static volatile SingularAttribute<AudioSnimakKategorija, AudioSnimak> audioSnimak;
    public static volatile SingularAttribute<AudioSnimakKategorija, Kategorija> kategorija;

}