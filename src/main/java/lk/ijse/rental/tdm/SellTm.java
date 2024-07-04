package lk.ijse.rental.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellTm {
    private String colSellId;
    private Date colSellDate;
    private String colCEmail;
    private double colSellTotal;

}