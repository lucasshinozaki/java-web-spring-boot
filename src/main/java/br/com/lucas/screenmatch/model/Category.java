package br.com.lucas.screenmatch.model;

public enum Category {
        ACAO("Action"),
        ROMANCE("romance"),
        COMEDIA("Comedy"),
        CRIME("Crime");

        private String categoryOmdb;

        Category(String categoryOmdb) {
            this.categoryOmdb = categoryOmdb;
        }
    public static Category fromString(String text) {
    for (Category category : Category.values()) {
        if (category.categoryOmdb.equalsIgnoreCase(text)) {
            return category;
        }
    }
    throw new IllegalArgumentException("No categories found for the given string: " + text);
}
}
