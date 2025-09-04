package model;

public enum Category {

    FESTA("Festa"),
    EVENTO_ESPORTIVO("Evento Esportivo"),
    SHOW("Show"),
    PALESTRA("Palestra"),
    EXPOSICAO("Exposição"),
    FEIRA("Feira"),
    WORKSHOP("Workshop");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.name.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada com o nome: " + text);
    }
}
