package lk.ijse.rental.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Bokking {
    private String bokkingId;
    private String bokkingDate;
    private String customerEmail;
    private String machineId;
}
