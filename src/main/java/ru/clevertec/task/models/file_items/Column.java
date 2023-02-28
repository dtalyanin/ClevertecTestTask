package ru.clevertec.task.models.file_items;

import ru.clevertec.task.utils.AlignFormatter;

public record Column(String value, int maxLength, AlignFormatter alignFormatter) {
    @Override
    public String toString() {
        return alignFormatter.getAlignedString(value, maxLength);
    }
}
