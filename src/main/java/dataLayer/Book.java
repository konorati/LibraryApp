package dataLayer;

import lombok.Data;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.dao.ForeignCollection;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DatabaseTable(tableName = "Book")
public @Data class Book {

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(canBeNull = false)
    String title;

    @ForeignCollectionField
    ForeignCollection<Review> reviews;

    @DatabaseField(defaultValue = "true", canBeNull = false)
    Boolean available;
}
