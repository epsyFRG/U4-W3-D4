package emilianomassari.dao;

import emilianomassari.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EventsDAO {
    private EntityManager em;

    public EventsDAO(EntityManager em) {
        this.em = em;
    }


    public void save(Event evento) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(evento);
            t.commit();
            System.out.println("Evento - " + evento.getTitolo() + " - creato!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Event findById(long id) {
        return em.find(Event.class, id);
    }

    public java.util.List<Concerto> getConcertiInStreaming(boolean inStreaming) {
        return em.createQuery("SELECT c FROM Concerto c WHERE c.inStreaming = :inStreaming", Concerto.class)
                .setParameter("inStreaming", inStreaming)
                .getResultList();
    }

    public java.util.List<Concerto> getConcertiPerGenere(Genere genere) {
        return em.createQuery("SELECT c FROM Concerto c WHERE c.genere = :genere", Concerto.class)
                .setParameter("genere", genere)
                .getResultList();
    }

    public java.util.List<PartitaDiCalcio> getPartiteVinteInCasa() {
        return em.createNamedQuery("PartitaDiCalcio.getPartiteVinteInCasa", PartitaDiCalcio.class)
                .getResultList();
    }

    public java.util.List<PartitaDiCalcio> getPartiteVinteInTrasferta() {
        return em.createNamedQuery("PartitaDiCalcio.getPartiteVinteInTrasferta", PartitaDiCalcio.class)
                .getResultList();
    }

    public void findByIdAndDelete(long id) {
        try {
            EntityTransaction t = em.getTransaction();
            Event found = em.find(Event.class, id);
            if (found != null) {
                t.begin();
                em.remove(found);
                t.commit();
                System.out.println("Evento eliminato");
            } else System.out.println("Evento non trovato");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
