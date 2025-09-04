package view;

import model.Category;
import model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleView {
    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void showMainMenu() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Cadastrar Evento");
        System.out.println("2. Visualizar Eventos");
        System.out.println("3. Meus Eventos");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public void showMenuView() {
        System.out.println("\n--- Visualizar Eventos ---");
        System.out.println("1. Todos os Eventos Futuros");
        System.out.println("2. Eventos Ocorrendo Agora");
        System.out.println("3. Eventos Anteriores");
        System.out.println("4. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    public void showEvents(String title, List<Event> events) {
        System.out.println("\n--- " + title + " ---");
        if (events.isEmpty()) {
            System.out.println("Nenhum evento encontrado.");
            return;
        }
        for (int i = 0; i < events.size(); i++) {
            System.out.println("ID: " + (i + 1));
            System.out.println(events.get(i).toString());
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public String readInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public Category readCategory() {
        System.out.println("\nCategorias disponíveis:");
        String categoriasStr = Arrays.stream(Category.values())
                .map(c -> c.getName())
                .collect(Collectors.joining(", "));
        System.out.println(categoriasStr);

        while (true) {
            String input = readInput("Digite a categoria do evento: ");
            try {
                return Category.fromString(input);
            } catch (IllegalArgumentException e) {
                displayMessage("Categoria inválida. Tente novamente.");
            }
        }
    }

    public LocalDateTime readSchedule() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        while (true) {
            String dataHoraStr = readInput("Digite o horário do evento (dd/MM/yyyy HH:mm): ");
            try {
                return LocalDateTime.parse(dataHoraStr, formatter);
            } catch (DateTimeParseException e) {
                displayMessage("Formato de data/hora inválido. Use dd/MM/yyyy HH:mm.");
            }
        }
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
