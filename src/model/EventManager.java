package model;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EventManager {

    private static final String ARQUIVO_DADOS = "events.data";
    private List<Event> events;
    private final List<Event> confirmedEvents;

    public EventManager() {
        this.events = new ArrayList<>();
        this.confirmedEvents = new ArrayList<>();
        loadEvents();
    }

    public void addEvent(Event event) {
        this.events.add(event);
        saveEvents();
    }

    public void loadEvents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_DADOS))) {
            this.events = (List<Event>) ois.readObject();
            System.out.println("Eventos carregados com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de eventos não encontrado. Criando um novo...");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar eventos: " + e.getMessage());
        }
    }

    public void saveEvents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(this.events);
            System.out.println("Eventos salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar eventos: " + e.getMessage());
        }
    }

    public List<Event> getEvents() {
        Collections.sort(events);
        return new ArrayList<>(events);
    }

    public List<Event> getActiveEvents() {
        return events.stream()
                .filter(e -> e.getDateTime().isAfter(LocalDateTime.now()))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Event> getEventsHappeningNow() {
        return events.stream()
                .filter(e -> e.getDateTime().isBefore(LocalDateTime.now()) &&
                        e.getDateTime().plusHours(3).isAfter(LocalDateTime.now()))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Event> getPreviousEvents() {
        return events.stream()
                .filter(e -> e.getDateTime().isBefore(LocalDateTime.now()))
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }

    public void confirmAttendance(Event event) {
        if (!confirmedEvents.contains(event)) {
            confirmedEvents.add(event);
        }
    }

    public void cancelAttendance(Event event) {
        confirmedEvents.remove(event);
    }

    public List<Event> getConfirmedEvents() {
        return new ArrayList<>(confirmedEvents);
    }
}
