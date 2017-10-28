package dataLayer;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.j256.ormlite.dao.BaseForeignCollection;
import lombok.Data;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.ForeignCollectionField;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@DatabaseTable(tableName = "Book")
public class Book {

    @DatabaseField(generatedId = true)
    @Getter @Setter  int id;

    @DatabaseField(canBeNull = false)
    @Getter @Setter String title;

    @ForeignCollectionField
    Collection<Review> reviews;

    public List<Review> getReviews(){
        if(reviews == null) { return new ArrayList<Review>(); }
        return new ArrayList<Review>(reviews);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @DatabaseField(defaultValue = "true", canBeNull = false)
    @Getter @Setter Boolean available = true;
}
