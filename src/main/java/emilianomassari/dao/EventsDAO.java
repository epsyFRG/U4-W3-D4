package epicode.dao;

import epicode.entities.Event;

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
