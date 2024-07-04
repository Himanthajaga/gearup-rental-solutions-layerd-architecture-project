package lk.ijse.rental.dao;

import lk.ijse.rental.dao.custom.Impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        Customer, Admin, Machine, RentOrder, OrderDetail, Sell, SellMaterial, Reservation, SupplierBuildingMaterial, OrderDetails, QueryDAO, Supplier, BuildingMaterial, Bokking, Payment,Mechanic
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case Customer:
                return new CustomerDAOImpl();
            case Admin:
                return new AdminDAOImpl();
            case Machine:
                return new MachineDAOImpl();
            case RentOrder:
                return new RentOrderDAOImpl();
            case OrderDetail:
                return new OrderDetailDAOImpl();
            case Sell:
                return new SellDAOImpl();
            case SellMaterial:
                return new SellMaterialDAOImpl();
            case Reservation:
                return new ReservationDAOImpl();
            case SupplierBuildingMaterial:
                return new SupplierBuildingMaterialDAOImpl();
            case OrderDetails:
                return new OrderDetailDAOImpl();
            case QueryDAO:
                return new QueeryDAOImpl();
            case Supplier:
                return new SupplierDAOImpl();
            case BuildingMaterial:
                return new BuildingMaterialDAOImpl();
            case Bokking:
                return new BokkingDAOImpl();
            case Payment:
                return new PaymentDAOImpl();
            case Mechanic:
                return new MechanicDAOImpl();
            default:
                return null;
        }
    }
}
