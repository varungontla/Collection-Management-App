package Gsoft.project;

public class ShopBillHelperClass {

    private String BillNo,BillKey,ShopName,CollectionDate,GroupName;
    private Integer Amount,DueAmount,CollectedAmount;
    public ShopBillHelperClass(){}


    public ShopBillHelperClass(Integer Amount, String BillNo){
        this.Amount = Amount;
        this.BillNo = BillNo;
    }

    public ShopBillHelperClass(Integer DueAmount, Integer CollectedAmount, String CollectionDate){
        this.DueAmount = DueAmount;
        this.CollectedAmount = CollectedAmount;
        this.CollectionDate = CollectionDate;
    }

    public ShopBillHelperClass(String ShopName, String BillNo, Integer CollectedAmount, String CollectionDate){
        this.ShopName = ShopName;
        this.BillNo = BillNo;
        this.CollectedAmount = CollectedAmount;
        this.CollectionDate = CollectionDate;
    }

    public ShopBillHelperClass(Integer DueAmount){
        this.DueAmount = DueAmount;
    }


    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getCollectionDate() {
        return CollectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        CollectionDate = collectionDate;
    }

    public Integer getCollectedAmount() {
        return CollectedAmount;
    }

    public void setCollectedAmount(Integer collectedAmount) {
        CollectedAmount = collectedAmount;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }


    public String getBillKey() {
        return BillKey;
    }

    public void setBillKey(String billKey) {
        BillKey = billKey;
    }


    public Integer getDueAmount() {
        return DueAmount;
    }

    public void setDueAmount(Integer dueAmount) {
        DueAmount = dueAmount;
    }
}
