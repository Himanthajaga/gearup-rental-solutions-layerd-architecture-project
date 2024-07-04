package lk.ijse.rental.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellMaterial {
   private String sellId;
   private String bmId;
    private double bm_unit_price;
   private double bm_qty;

}
