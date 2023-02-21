package ru.clevertec.task.generators.builders;

import lombok.NoArgsConstructor;
import ru.clevertec.task.models.file_items.Column;
import ru.clevertec.task.models.file_items.Row;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(staticName = "aRow")
public class TestRowBuilder {
    private List<Column> columns = new ArrayList<>();
    private String delimiter = " ";

    public TestRowBuilder columns(List<Column> columns) {
        this.columns = columns;
        return this;
    }

    public TestRowBuilder column(Column column) {
        this.columns.add(column);
        return this;
    }

    public TestRowBuilder delimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public Row build() {
        return new Row(columns, delimiter);
    }
}
