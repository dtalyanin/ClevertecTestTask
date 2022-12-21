package ru.clevertec.task.utils.writers.file_items;

public record Column(String value, int maxLength, AlignFormatter alignFormatter) {
    @Override
    public String toString() {
        return alignFormatter.getAlignedString(value, maxLength);
    }
}
