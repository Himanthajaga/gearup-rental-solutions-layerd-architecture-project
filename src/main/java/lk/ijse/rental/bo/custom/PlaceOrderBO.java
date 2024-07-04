package lk.ijse.rental.bo.custom;

import lk.ijse.rental.bo.SuperBO;
import lk.ijse.rental.dto.CustomerDTO;
import lk.ijse.rental.dto.MachineDTO;
import lk.ijse.rental.dto.RentOrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBO {
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException ;

    public MachineDTO searchMachine(String code) throws SQLException, ClassNotFoundException ;

    public boolean existMachine(String code) throws SQLException, ClassNotFoundException;

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;

    public String generateOrderID() throws SQLException, ClassNotFoundException ;

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    public ArrayList<MachineDTO> getAllMachines() throws SQLException, ClassNotFoundException;

    public boolean placeOrder(RentOrderDTO dto)throws SQLException, ClassNotFoundException;

    public MachineDTO findItem(String code)throws SQLException, ClassNotFoundException;


    public String getLastId() throws SQLException, ClassNotFoundException;
}
