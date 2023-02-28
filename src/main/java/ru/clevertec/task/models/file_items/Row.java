package ru.clevertec.task.models.file_items;

import java.util.List;
import java.util.stream.Collectors;

public record Row(List<Column> columns, String delimiter) {
    @Override
    public String toString() {
        return columns.stream().map(Column::toString).collect(Collectors.joining(delimiter));
    }
}
