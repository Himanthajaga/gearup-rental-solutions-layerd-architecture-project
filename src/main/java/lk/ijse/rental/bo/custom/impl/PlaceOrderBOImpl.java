package lk.ijse.rental.bo.custom.impl;

import lk.ijse.rental.bo.custom.PlaceOrderBO;
import lk.ijse.rental.dao.CrudDAO;
import lk.ijse.rental.dao.DAOFactory;
import lk.ijse.rental.dao.custom.*;
import lk.ijse.rental.db.DBConnection;
import lk.ijse.rental.dto.CustomerDTO;
import lk.ijse.rental.dto.MachineDTO;
import lk.ijse.rental.dto.OrderDetailDTO;
import lk.ijse.rental.dto.RentOrderDTO;
import lk.ijse.rental.entity.Customer;
import lk.ijse.rental.entity.Machine;
import lk.ijse.rental.entity.OrderDetail;
import lk.ijse.rental.entity.RentOrder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Customer);
    MachineDAO machineDAO = (MachineDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Machine);
    RentOrderDAO rentOrderDAO = (RentOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RentOrder);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.OrderDetail);


    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(id);
        return new CustomerDTO(c.getC_mail(),c.getC_name(),c.getC_address(),c.getC_tel(),c.getC_id());
    }

    @Override
    public MachineDTO searchMachine(String code) throws SQLException, ClassNotFoundException {
        Machine m = machineDAO.search(code);
        return new MachineDTO(m.getM_Id(),m.getM_Name(),m.getM_desc(),m.getM_rental_price(),m.getIsAvaiable(),m.getC_email());
    }

    @Override
    public boolean existMachine(String code) throws SQLException, ClassNotFoundException {
        return machineDAO.exist(code);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return rentOrderDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getC_mail(),c.getC_name(),c.getC_address(),c.getC_tel(),c.getC_id()));
        }
        return allCustomers;
    }

    @Override
    public ArrayList<MachineDTO> getAllMachines() throws SQLException, ClassNotFoundException {
        ArrayList<MachineDTO> allMachines = new ArrayList<>();
        ArrayList<Machine> all = machineDAO.getAll();
        for (Machine c : all) {
            allMachines.add(new MachineDTO(c.getM_Id(),c.getM_Name(),c.getM_desc(),c.getM_rental_price(),c.getIsAvaiable(),c.getC_email()));
        }
        return allMachines;
    }

    @Override
    public boolean placeOrder(RentOrderDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            boolean b1 = rentOrderDAO.exist(dto.getOrderId());
            //if order id already exist
            if (b1) {
                return false;
            }

            connection.setAutoCommit(false);
            //Save the Order to the order table
            boolean b2 = rentOrderDAO.add(new RentOrder(dto.getOrderId(), dto.getDate(), dto.getReturnDate(), dto.getCustomerEmail(), dto.getTotal()));
            if (!b2) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO d : dto.getOrderDetails()) {
                OrderDetail orderDetails = new OrderDetail(d.getOrderId(),d.getMachineId());
                boolean b3 = orderDetailDAO.add(orderDetails);
                if (!b3) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                //Search & Update Item
                Machine machine = machineDAO.search(d.getMachineId());
                machine.setIsAvaiable("0");
                boolean b = machineDAO.updateAvailable(machine.getM_Id());


                if (!b) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public MachineDTO findItem(String code) throws SQLException, ClassNotFoundException {
        try {
            Machine i = machineDAO.search(code);
            return new MachineDTO(i.getM_Id(),i.getM_Name(),i.getM_desc(),i.getM_rental_price(),i.getIsAvaiable(),i.getC_email());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        return rentOrderDAO.getLastId();
    }

}
