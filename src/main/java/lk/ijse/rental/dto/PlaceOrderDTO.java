package lk.ijse.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderDTO {
    private RentOrderDTO rentorder;
    private List<OrderDetailDTO> odList;
}
