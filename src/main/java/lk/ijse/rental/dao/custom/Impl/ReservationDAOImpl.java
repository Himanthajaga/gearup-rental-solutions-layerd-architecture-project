package lk.ijse.rental.dao.custom.Impl;

import lk.ijse.rental.dao.SQLUtil;
import lk.ijse.rental.dao.custom.RentOrderDAO;
import lk.ijse.rental.dao.custom.ReservationDAO;
import lk.ijse.rental.entity.RentOrder;
import lk.ijse.rental.entity.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public ArrayList<Reservation> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Reservation> allReservations = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM reservation");
        while (rst.next()) {
            Reservation reservation = new Reservation(rst.getString("r_id"), rst.getString("r_type"), rst.getString("r_date"));
            allReservations.add(reservation);
        }
        return allReservations;
    }

    @Override
    public boolean add(Reservation entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO reservation (r_id,r_type,r_date) VALUES (?,?,?)", entity.getR_id(), entity.getR_type(), entity.getR_date());
    }

    @Override
    public boolean update(Reservation entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE reservation SET r_type = ?,r_date = ? WHERE r_id = ?", entity.getR_type(), entity.getR_date(), entity.getR_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT r_id FROM reservation WHERE r_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT r_id FROM reservation ORDER BY r_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("r_id");
            int newReservationId = Integer.parseInt(id.replace("R00-", "")) + 1;
            return String.format("R00-%03d", newReservationId);
        } else {
            return "R00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM reservation WHERE r_id=?", id);
    }

    @Override
    public Reservation search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM reservation WHERE r_id=?", id + "");
        if (rst.next()) {
            return new Reservation(rst.getString("r_id"), rst.getString("r_type"), rst.getString("r_date"));
        }
        return null;
    }

    @Override
    public <T> T searchByEmail(String name) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public <T> ArrayList<T> getEmails(String email) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public <T> T searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public <T> T searchByName(String name) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT r_id FROM reservation ORDER BY r_id DESC LIMIT 1;");
        if (rst.next()) {
            return rst.getString("r_id");
        } else {
            return null;
        }
    }
}
