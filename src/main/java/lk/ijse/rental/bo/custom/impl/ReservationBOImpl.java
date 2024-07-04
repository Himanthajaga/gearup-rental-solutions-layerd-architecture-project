package lk.ijse.rental.bo.custom.impl;

import lk.ijse.rental.bo.custom.ReservationBO;
import lk.ijse.rental.dao.DAOFactory;
import lk.ijse.rental.dao.custom.ReservationDAO;
import lk.ijse.rental.dto.ReservationDTO;
import lk.ijse.rental.entity.Reservation;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationBOImpl implements ReservationBO {
    ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Reservation);

    @Override
    public ArrayList<ReservationDTO> getAllReservations() throws SQLException, ClassNotFoundException {
        ArrayList<ReservationDTO> allReservationss = new ArrayList<>();
        ArrayList<Reservation> all = reservationDAO.getAll();
        for (Reservation c : all) {
            allReservationss.add(new ReservationDTO(c.getR_id(),c.getR_type(),c.getR_date()));
        }
        return allReservationss;
    }

    @Override
    public boolean addReservation(ReservationDTO dto) throws SQLException, ClassNotFoundException {
        return reservationDAO.add(new Reservation(dto.getR_id(),dto.getR_type(),dto.getR_date()));
    }

    @Override
    public boolean updateReservation(ReservationDTO dto) throws SQLException, ClassNotFoundException {
        return reservationDAO.update(new Reservation(dto.getR_id(),dto.getR_type(),dto.getR_date()));
    }

    @Override
    public boolean existReservation(String id) throws SQLException, ClassNotFoundException {
        return reservationDAO.exist(id);
    }

    @Override
    public String generateNewReservationID() throws SQLException, ClassNotFoundException {
        return reservationDAO.generateNewID();
    }

    @Override
    public boolean deleteReservation(String id) throws SQLException, ClassNotFoundException {
        return reservationDAO.delete(id);
    }

    @Override
    public String getLastReservationId() throws SQLException, ClassNotFoundException {
        return reservationDAO.getLastId();
    }

    @Override
    public Reservation search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
