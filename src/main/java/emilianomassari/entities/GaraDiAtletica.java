package emilianomassari.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("ATLETICA")
public class GaraDiAtletica extends Event {
    @ManyToMany
    @JoinTable(name = "gara_atleti",
            joinColumns = @JoinColumn(name = "gara_id"),
            inverseJoinColumns = @JoinColumn(name = "persona_id"))
    private Set<Person> atleti;

    @ManyToOne
    @JoinColumn(name = "vincitore_id")
    private Person vincitore;

    public GaraDiAtletica() {}

    public GaraDiAtletica(String titolo, java.time.LocalDate dataEvento, String descrizione, TipoEvento tipoEvento, int numeroMassimoPartecipanti, Location location,
                           Set<Person> atleti, Person vincitore) {
        super(titolo, dataEvento, descrizione, tipoEvento, numeroMassimoPartecipanti, location);
        this.atleti = atleti;
        this.vincitore = vincitore;
    }

    public Set<Person> getAtleti() { return atleti; }
    public void setAtleti(Set<Person> atleti) { this.atleti = atleti; }
    public Person getVincitore() { return vincitore; }
    public void setVincitore(Person vincitore) { this.vincitore = vincitore; }
}


