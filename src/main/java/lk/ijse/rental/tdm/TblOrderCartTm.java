package lk.ijse.rental.tdm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TblOrderCartTm {
    private String colMachineID;
    private String colDescription;
    private double colUnitPrice;
    private double colTotal;
    private JFXButton colAction;
}
