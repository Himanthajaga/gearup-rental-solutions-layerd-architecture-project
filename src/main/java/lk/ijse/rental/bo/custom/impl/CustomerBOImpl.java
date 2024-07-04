package lk.ijse.rental.bo.custom.impl;

import lk.ijse.rental.bo.custom.CustomerBO;
import lk.ijse.rental.dao.DAOFactory;
import lk.ijse.rental.dao.custom.CustomerDAO;
import lk.ijse.rental.dto.CustomerDTO;
import lk.ijse.rental.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Customer);

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
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getC_mail(),dto.getC_name(),dto.getC_address(),dto.getC_tel(),dto.getC_id()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getC_mail(),dto.getC_name(),dto.getC_address(),dto.getC_tel(),dto.getC_id()));
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public String getLastCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.getLastId();
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getIds();
    }

    @Override
    public Customer searchByEmail(String name) throws SQLException, ClassNotFoundException {
        return customerDAO.searchByEmail(name);
    }

    @Override
    public Customer searchById(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.searchById(customerId);
    }
}
