package trackExpense.model;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "expenses")
public class Expense {

    @Id
    private ObjectId _id;
    private String idExpense;
    private String username;
    private String description;
    private double amount;
    private String category;
    private Date expenseDate;
}
