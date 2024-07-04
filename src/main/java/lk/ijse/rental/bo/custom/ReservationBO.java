package lk.ijse.rental.bo.custom;

import lk.ijse.rental.bo.SuperBO;
import lk.ijse.rental.dto.PaymentDTO;
import lk.ijse.rental.dto.ReservationDTO;
import lk.ijse.rental.entity.Bokking;
import lk.ijse.rental.entity.Reservation;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationBO extends SuperBO {
    public ArrayList<ReservationDTO> getAllReservations() throws SQLException, ClassNotFoundException;
    public boolean addReservation(ReservationDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateReservation(ReservationDTO dto) throws SQLException, ClassNotFoundException;
    public boolean existReservation(String id) throws SQLException, ClassNotFoundException;
    public String generateNewReservationID() throws SQLException, ClassNotFoundException;
    public boolean deleteReservation(String id) throws SQLException, ClassNotFoundException;
    String getLastReservationId() throws SQLException, ClassNotFoundException;
    public Reservation search(String id) throws SQLException, ClassNotFoundException;
}
