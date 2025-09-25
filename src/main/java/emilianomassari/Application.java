package emilianomassari;

import com.github.javafaker.Faker;
import emilianomassari.dao.AttendancesDAO;
import emilianomassari.dao.EventsDAO;
import emilianomassari.dao.LocationsDAO;
import emilianomassari.dao.PeopleDAO;
import emilianomassari.entities.*;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("u4d13");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        Faker faker = new Faker(Locale.ITALY);
        EventsDAO eventsDAO = new EventsDAO(em);
        LocationsDAO locationsDAO = new LocationsDAO(em);
        PeopleDAO peopleDAO = new PeopleDAO(em);
        AttendancesDAO attendancesDAO = new AttendancesDAO(em);
        Random rndm = new Random();

        // ******************** SALVATAGGIO LOCATIONS, UTENTI E EVENTI ************************

        Location location1 = new Location(faker.address().city(), faker.address().cityName());
        locationsDAO.save(location1);

        Location location2 = new Location(faker.address().city(), faker.address().cityName());
        locationsDAO.save(location2);

        Person person1 = new Person(faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(),  LocalDate.now(), rndm.nextInt(0, 2) == 0 ? 'M' : 'F');
        peopleDAO.save(person1);
        // ******************** SEED EVENTI DERIVATI ************************
        // Concerto
        Concerto concerto1 = new Concerto(
                "Rock Night", LocalDate.now().plusDays(10), faker.lorem().fixedString(40),
                TipoEvento.PUBBLICO, 500, rndm.nextInt(0, 2) == 0 ? location1 : location2,
                Genere.ROCK, true);
        Concerto concerto2 = new Concerto(
                "Classica in Teatro", LocalDate.now().plusDays(20), faker.lorem().fixedString(40),
                TipoEvento.PRIVATO, 200, rndm.nextInt(0, 2) == 0 ? location1 : location2,
                Genere.CLASSICO, false);
        eventsDAO.save(concerto1);
        eventsDAO.save(concerto2);

        // Partita di Calcio (una vinta in casa, una in trasferta)
        PartitaDiCalcio partitaCasa = new PartitaDiCalcio(
                "Derby", LocalDate.now().plusDays(5), faker.lorem().fixedString(30),
                TipoEvento.PUBBLICO, 30000, rndm.nextInt(0, 2) == 0 ? location1 : location2,
                "SquadraA", "SquadraB", "SquadraA", 2, 1);
        PartitaDiCalcio partitaTrasferta = new PartitaDiCalcio(
                "Big Match", LocalDate.now().plusDays(7), faker.lorem().fixedString(30),
                TipoEvento.PUBBLICO, 30000, rndm.nextInt(0, 2) == 0 ? location1 : location2,
                "SquadraC", "SquadraD", "SquadraD", 0, 1);
        eventsDAO.save(partitaCasa);
        eventsDAO.save(partitaTrasferta);

        // Gara di Atletica
        HashSet<Person> atleti = new HashSet<>();
        atleti.add(person1);
        GaraDiAtletica gara = new GaraDiAtletica(
                "Meeting Cittadino", LocalDate.now().plusDays(15), faker.lorem().fixedString(30),
                TipoEvento.PUBBLICO, 1000, rndm.nextInt(0, 2) == 0 ? location1 : location2,
                atleti, person1);
        eventsDAO.save(gara);

        // ******************** ESECUZIONE JPQL / NAMED QUERIES ************************
        System.out.println("Concerti in streaming:");
        eventsDAO.getConcertiInStreaming(true).forEach(System.out::println);

        System.out.println("Concerti ROCK:");
        eventsDAO.getConcertiPerGenere(Genere.ROCK).forEach(System.out::println);

        System.out.println("Partite vinte in casa:");
        eventsDAO.getPartiteVinteInCasa().forEach(System.out::println);

        System.out.println("Partite vinte in trasferta:");
        eventsDAO.getPartiteVinteInTrasferta().forEach(System.out::println);


/*        for (int i = 0; i < 20; i++) {
            eventsDAO.save(new Event(
                    faker.chuckNorris().fact(),
                    LocalDate.of(rndm.nextInt(2023, 2025),
                            rndm.nextInt(1, 13),
                            rndm.nextInt(1, 29)),
                    faker.lorem().fixedString(50),
                    rndm.nextInt(1, 3) == 1 ? TipoEvento.PRIVATO : TipoEvento.PUBBLICO,
                    rndm.nextInt(1, 1000),rndm.nextInt(0, 2) == 0 ? location1: location2));
        }*/

        // ******************** PARTECIPAZIONE AD UN EVENTO ************************

        Person person = peopleDAO.findById(23);
        Event event = eventsDAO.findById(24);

        if (person == null) {
            System.out.println("Persona con id 23 non trovata");
        }
        if (event == null) {
            System.out.println("Evento con id 24 non trovato");
        }

        if (person != null && event != null) {
            Attendance partecipazione = new Attendance(person, event);
            attendancesDAO.save(partecipazione);

            // Stampo eventi a cui partecipa la persona 23
            if (person.getListaPartecipazioni() != null) {
                person.getListaPartecipazioni().forEach(System.out::println);
            }

            // Stampo elenco partecipanti evento 24
            if (event.getListaPartecipazioni() != null) {
                event.getListaPartecipazioni().forEach(System.out::println);
            }

            // ******************** CASCADING ************************

            // Eliminando un evento dovrebbe eliminare anche le partecipazioni ad esso collegate
            eventsDAO.findByIdAndDelete(24);
        }


        em.close();
        emf.close();
   }
}
