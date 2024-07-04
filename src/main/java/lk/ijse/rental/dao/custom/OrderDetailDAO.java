package lk.ijse.rental.dao.custom;

import lk.ijse.rental.dao.CrudDAO;
import lk.ijse.rental.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
    boolean saved(List<OrderDetail> odList) throws SQLException, ClassNotFoundException;
     boolean save(OrderDetail od) throws SQLException, ClassNotFoundException;
}
