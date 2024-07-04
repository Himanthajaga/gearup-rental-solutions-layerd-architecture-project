package lk.ijse.rental.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingMaterial {
    private String bm_id;
    private String bm_desc;
    private String bm_type;
    private String bm_price;
    private int bm_qty;
    private String s_email;
}
