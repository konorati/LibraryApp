package dataLayer;

import lombok.Data;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "User")
public @Data class User {
    @DatabaseField(canBeNull = false)
    String name;

    @DatabaseField(id = true)
    String username;

    @DatabaseField(canBeNull = false)
    String password;

    @DatabaseField(defaultValue = "PATRON")
    USER_TYPE userType;
}
