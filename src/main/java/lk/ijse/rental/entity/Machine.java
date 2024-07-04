package lk.ijse.rental.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Machine {
   private String m_Id;
    private String m_Name;
    private String m_desc;
    private String m_rental_price;
    private String isAvaiable;
    private String c_email;

}
