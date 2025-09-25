package emilianomassari.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CONCERTO")
public class Concerto extends Event {
    @Enumerated(EnumType.STRING)
    private Genere genere;
    private boolean inStreaming;

    public Concerto() {}

    public Concerto(String titolo, java.time.LocalDate dataEvento, String descrizione, TipoEvento tipoEvento, int numeroMassimoPartecipanti, Location location, Genere genere, boolean inStreaming) {
        super(titolo, dataEvento, descrizione, tipoEvento, numeroMassimoPartecipanti, location);
        this.genere = genere;
        this.inStreaming = inStreaming;
    }

    public Genere getGenere() {
        return genere;
    }

    public void setGenere(Genere genere) {
        this.genere = genere;
    }

    public boolean isInStreaming() {
        return inStreaming;
    }

    public void setInStreaming(boolean inStreaming) {
        this.inStreaming = inStreaming;
    }
}


