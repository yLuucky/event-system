package model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Event implements Serializable, Comparable<Event> {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String address;
    private final Category category;
    private final LocalDateTime dateTime;
    private final String description;

    public Event(String name, String address, Category category, LocalDateTime dateTime, String description) {
        this.name = name;
        this.address = address;
        this.category = category;
        this.dateTime = dateTime;
        this.description = description;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public Category getCategory() { return category; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Nome: " + name +
                "\nEndereço: " + address +
                "\nCategoria: " + category.getName() +
                "\nHorário: " + dateTime.format(formatter) +
                "\nDescrição: " + description + "\n";
    }

    @Override
    public int compareTo(Event another) {
        return this.dateTime.compareTo(another.dateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(name, event.name) && Objects.equals(address, event.address) && Objects.equals(dateTime, event.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, dateTime);
    }
}
