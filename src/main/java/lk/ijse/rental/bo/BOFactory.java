package lk.ijse.rental.bo;

import lk.ijse.rental.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        PlaceOrder,Customer,Machine,PlaceSell,BuildingMaterial,Payment,Admin,Bokking,Mechanic,Reservation,Supplier,SupplierBuildingmaterial
            }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case PlaceOrder:
                return new PlaceOrderBOImpl();
            case Customer:
                return new CustomerBOImpl();
            case Machine:
                return new MachineBOImpl();
            case PlaceSell:
                return new PlaceSellBOImpl();
            case BuildingMaterial:
                return new BuildingMaterialBOImpl();
            case Payment:
                return new PaymentBOImpl();
            case Admin:
                return new AdminBOImpl();
            case Bokking:
                return new BokkingBOImpl();
            case Mechanic:
                return new MechanicBOImpl();
            case Reservation:
                return new ReservationBOImpl();
            case Supplier:
                return new SupplierBOIpml();
            case SupplierBuildingmaterial:
                return new SupplierBuildingMaterialBOImpl();
            default:
                return null;
        }
    }

}
