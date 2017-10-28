package dataLayer;

import lombok.Data;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Review")
public @Data class Review {

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(canBeNull = false, foreign = true)
    Book book;

    @DatabaseField(canBeNull = false)
    String username;

    @DatabaseField(canBeNull = false)
    String review;
}
