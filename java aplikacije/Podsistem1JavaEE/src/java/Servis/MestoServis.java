package servis;

import javax.json.JsonObject;
import javax.persistence.*;
import Entiteti.Mesto;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

@Stateless
public class MestoServis {

    @PersistenceContext(unitName = "Podsistem1PU")
    private EntityManager em;

    public void kreiraj(JsonObject data) {
        Mesto m = new Mesto();
        m.setNaziv(data.getString("naziv"));
        em.persist(m);
    }
    
    public List<Mesto> dohvatiSvaMesta() {
        return em.createQuery("SELECT m FROM Mesto m", Mesto.class).getResultList();
    }
    
    public JsonArray getSvaMestaAsJson() {
    List<Mesto> mesta = em.createQuery("SELECT m FROM Mesto m", Mesto.class).getResultList();
    JsonArrayBuilder builder = Json.createArrayBuilder();
    for (Mesto m : mesta) {
        builder.add(Json.createObjectBuilder()
            .add("id", m.getIdM())
            .add("naziv", m.getNaziv()));
    }
    return builder.build();
}
}