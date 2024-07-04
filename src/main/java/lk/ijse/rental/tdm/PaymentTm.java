package lk.ijse.rental.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTm {
    private String colPaymentId;
    private String colPaymentType;
    private String colSEmail;
    private double colPaymentAmount;

}
