package lk.ijse.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
   private String a_id;
    private String a_name;
    private String a_password;
    private String a_confirmPassword;
    private String a_email;

 public AdminDTO(String userId, String pw) {
 }
}
