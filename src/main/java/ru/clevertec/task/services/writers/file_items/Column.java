package ru.clevertec.task.services.writers.file_items;

public record Column(String value, int maxLength, AlignFormatter alignFormatter) {
    @Override
    public String toString() {
        return alignFormatter.getAlignedString(value, maxLength);
    }
}
