public class MyModel {

    String serviceCharges;
    String netAmount;


    public MyModel (String serviceCharges, String netAmount){
        this.serviceCharges= serviceCharges;
        this.netAmount= netAmount;

    }

    public String getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(String serviceCharges) {
        this.serviceCharges= serviceCharges;
    }

    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount= netAmount;
    }
};
ArrayList<MyModel> myModelArray = new ArrayList<MyModel>();
myModelArray.add(new MyModel("serviceCharge1","netAmount1"));
myModelArray.add(new MyModel("serviceCharge2","netAmount2"));..............;
myModelArray.get(position).getServiceCharges();
myModelArray.get(position).getNetAmount();;
