package lk.ijse.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellDTO {
private String sellId;
private Date sellDate;
private String c_email;
private Double total;

    List<SellMaterialDTO> sellMaterials;
}
