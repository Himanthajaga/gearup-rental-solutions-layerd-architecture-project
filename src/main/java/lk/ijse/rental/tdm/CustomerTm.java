package lk.ijse.rental.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTm {
    private String colCustomerEmail;
    private String colName;
    private String colAddress;
    private String colTelephone;
    private String colCustomerId;

}
