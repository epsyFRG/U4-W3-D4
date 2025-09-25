package emilianomassari.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CALCIO")
@NamedQueries({
        @NamedQuery(name = "PartitaDiCalcio.getPartiteVinteInCasa",
                query = "SELECT p FROM PartitaDiCalcio p WHERE p.squadraVincente = p.squadraDiCasa"),
        @NamedQuery(name = "PartitaDiCalcio.getPartiteVinteInTrasferta",
                query = "SELECT p FROM PartitaDiCalcio p WHERE p.squadraVincente = p.squadraOspite")
})
public class PartitaDiCalcio extends Event {
    private String squadraDiCasa;
    private String squadraOspite;
    private String squadraVincente; // null se pareggio
    private int numeroGolSquadraDiCasa;
    private int numeroGolSquadraOspite;

    public PartitaDiCalcio() {}

    public PartitaDiCalcio(String titolo, java.time.LocalDate dataEvento, String descrizione, TipoEvento tipoEvento, int numeroMassimoPartecipanti, Location location,
                           String squadraDiCasa, String squadraOspite, String squadraVincente, int golCasa, int golOspite) {
        super(titolo, dataEvento, descrizione, tipoEvento, numeroMassimoPartecipanti, location);
        this.squadraDiCasa = squadraDiCasa;
        this.squadraOspite = squadraOspite;
        this.squadraVincente = squadraVincente;
        this.numeroGolSquadraDiCasa = golCasa;
        this.numeroGolSquadraOspite = golOspite;
    }

    public String getSquadraDiCasa() { return squadraDiCasa; }
    public void setSquadraDiCasa(String squadraDiCasa) { this.squadraDiCasa = squadraDiCasa; }
    public String getSquadraOspite() { return squadraOspite; }
    public void setSquadraOspite(String squadraOspite) { this.squadraOspite = squadraOspite; }
    public String getSquadraVincente() { return squadraVincente; }
    public void setSquadraVincente(String squadraVincente) { this.squadraVincente = squadraVincente; }
    public int getNumeroGolSquadraDiCasa() { return numeroGolSquadraDiCasa; }
    public void setNumeroGolSquadraDiCasa(int numeroGolSquadraDiCasa) { this.numeroGolSquadraDiCasa = numeroGolSquadraDiCasa; }
    public int getNumeroGolSquadraOspite() { return numeroGolSquadraOspite; }
    public void setNumeroGolSquadraOspite(int numeroGolSquadraOspite) { this.numeroGolSquadraOspite = numeroGolSquadraOspite; }
}


