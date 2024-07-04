package lk.ijse.rental.dto;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentOrderDTO {
    private String orderId;
    private Date date;
    private Date returnDate;
    private String customerEmail;
    private double total;

    @Getter
    List<OrderDetailDTO> orderDetails;

    public RentOrderDTO(String orderId, Date date, Date returnDate, String customerEmail, double total) {
        this.orderId = orderId;
        this.date = date;
        this.returnDate = Date.valueOf(String.valueOf(returnDate));
        this.customerEmail = customerEmail;
        this.total = total;
    }
}