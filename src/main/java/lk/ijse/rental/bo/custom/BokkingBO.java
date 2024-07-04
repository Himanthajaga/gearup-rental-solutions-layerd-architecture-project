package lk.ijse.rental.bo.custom;

import lk.ijse.rental.bo.SuperBO;
import lk.ijse.rental.dto.BokkingDTO;
import lk.ijse.rental.dto.CustomerDTO;
import lk.ijse.rental.entity.Bokking;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BokkingBO extends SuperBO {
    public ArrayList<BokkingDTO> getAllBokkings() throws SQLException, ClassNotFoundException;
    public boolean addBokking(BokkingDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateBokking(BokkingDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existBokking(String id) throws SQLException, ClassNotFoundException;
    public String generateNewBokkingID() throws SQLException, ClassNotFoundException;
    public boolean deleteBokking(String id) throws SQLException, ClassNotFoundException;
    String getLastBokkingId() throws SQLException, ClassNotFoundException;
    public Bokking search(String id) throws SQLException, ClassNotFoundException;
}
