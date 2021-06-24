package Gsoft.project;

public class ShopHelperClass {

 private String ShopName,ShopNumber,ShopKey,Address,GroupName;

 public ShopHelperClass(){}

    public ShopHelperClass(String ShopName, String address, String PhoneNo, String GroupName){
        this.ShopName = ShopName;
        this.Address = address;
        this.ShopNumber = PhoneNo;
        this.GroupName = GroupName;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        this.ShopName= shopName;
    }

    public String getShopNumber() {
        return ShopNumber;
    }

    public void setShopNumber(String shopNumber) {
        this.ShopNumber = shopNumber;
    }

    public String getShopKey() {
        return ShopKey;
    }

    public void setShopKey(String shopKey) {
        ShopKey = shopKey;
    }

}
