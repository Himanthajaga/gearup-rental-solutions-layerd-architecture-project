package lk.ijse.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BokkingDTO {
    private String bokkingId;
    private String bokkingDate;
    private String customerEmail;
    private String machineId;
}
