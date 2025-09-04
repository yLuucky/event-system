package controller;

import model.Category;
import model.Event;
import model.EventManager;
import model.User;
import view.ConsoleView;

import java.time.LocalDateTime;
import java.util.List;

public class EventsSystem {
    private EventManager manager;
    private ConsoleView view;
    private User user;

    public EventsSystem(EventManager manager, ConsoleView view) {
        this.manager = manager;
        this.view = view;
    }

    public void start() {
        view.displayMessage("--- Bem-vindo ao Sistema de Eventos ---");
        registerUser();
        int option;
        do {
            view.showMainMenu();
            String input = view.readInput("");
            try {
                option = Integer.parseInt(input);
                switch (option) {
                    case 1:
                        registerEvent();
                        break;
                    case 2:
                        viewEvents();
                        break;
                    case 3:
                        manageParticipation();
                        break;
                    case 4:
                        view.displayMessage("Saindo do sistema...");
                        break;
                    default:
                        view.displayMessage("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                option = 0;
                view.displayMessage("Entrada inválida. Digite um número.");
            }
        } while (option != 4);
        view.closeScanner();
    }

    private void registerUser() {
        view.displayMessage("\n--- Cadastro de Usuário ---");
        String name = view.readInput("Digite seu nome: ");
        String email = view.readInput("Digite seu email: ");
        String city = view.readInput("Digite sua cidade: ");
        this.user = new User(name, email, city);
        view.displayMessage("Cadastro de usuário concluído!");
    }

    private void registerEvent() {
        view.displayMessage("\n--- Cadastro de Novo Evento ---");
        String name = view.readInput("Nome do evento: ");
        String address = view.readInput("Endereço: ");
        Category category = view.readCategory();
        LocalDateTime dateTime = view.readSchedule();
        String description = view.readInput("Descrição: ");

        Event newEvent = new Event(name, address, category, dateTime, description);
        manager.addEvent(newEvent);
        view.displayMessage("Evento cadastrado com sucesso!");
    }

    private void viewEvents() {
        int option;
        do {
            view.showMenuView();
            String input = view.readInput("");
            try {
                option = Integer.parseInt(input);
                List<Event> eventos;
                switch (option) {
                    case 1:
                        eventos = manager.getActiveEvents();
                        view.showEvents("Eventos Futuros", eventos);
                        manageEventParticipation(eventos);
                        break;
                    case 2:
                        eventos = manager.getEventsHappeningNow();
                        view.showEvents("Eventos Ocorrendo Agora", eventos);
                        manageEventParticipation(eventos);
                        break;
                    case 3:
                        eventos = manager.getPreviousEvents();
                        view.showEvents("Eventos Anteriores", eventos);
                        break;
                    case 4:
                        break;
                    default:
                        view.displayMessage("Opção inválida. Tente novamente.");
                        break;
                }
            } catch (NumberFormatException e) {
                option = 0;
                view.displayMessage("Entrada inválida. Digite um número.");
            }
        } while (option != 4);
    }

    private void manageParticipation() {
        view.displayMessage("\n--- Meus Eventos ---");
        List<Event> confirmedEvents = manager.getConfirmedEvents();
        if (confirmedEvents.isEmpty()) {
            view.displayMessage("Você não confirmou presença em nenhum evento.");
            return;
        }

        view.showEvents("Eventos em que confirmei presença", confirmedEvents);

        String acao = view.readInput("Deseja cancelar participação em um evento? (sim/nao): ");
        if (acao.equalsIgnoreCase("sim")) {
            try {
                int id = Integer.parseInt(view.readInput("Digite o ID do evento para cancelar: "));
                if (id > 0 && id <= confirmedEvents.size()) {
                    Event eventoParaRemover = confirmedEvents.get(id - 1);
                    manager.cancelAttendance(eventoParaRemover);
                    view.displayMessage("Participação cancelada com sucesso!");
                } else {
                    view.displayMessage("ID inválido.");
                }
            } catch (NumberFormatException e) {
                view.displayMessage("Entrada inválida. Digite um número.");
            }
        }
    }

    private void manageEventParticipation(List<Event> eventList) {
        if (eventList.isEmpty()) {
            return;
        }

        String action = view.readInput("Deseja confirmar presença em um evento? (sim/nao): ");
        if (action.equalsIgnoreCase("sim")) {
            try {
                int id = Integer.parseInt(view.readInput("Digite o ID do evento para participar: "));
                if (id > 0 && id <= eventList.size()) {
                    Event eventToBeConfirmed = eventList.get(id - 1);
                    manager.confirmAttendance(eventToBeConfirmed);
                    view.displayMessage("Presença confirmada! Adicionado à lista de 'Meus Eventos'.");
                } else {
                    view.displayMessage("ID inválido.");
                }
            } catch (NumberFormatException e) {
                view.displayMessage("Entrada inválida. Digite um número.");
            }
        }
    }

    public static void main(String[] args) {
        EventManager eventManager = new EventManager();
        ConsoleView consoleView = new ConsoleView();
        EventsSystem system = new EventsSystem(eventManager, consoleView);
        system.start();
    }
}