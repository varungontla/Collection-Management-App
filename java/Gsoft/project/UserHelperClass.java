package Gsoft.project;

public class UserHelperClass
{
   private String Name,Email,PhoneNo,GroupName,NoOfBills,CategoryKey;

    public UserHelperClass() {
    }

    public UserHelperClass(String GroupName, String NoOfBills){
        this.GroupName = GroupName;
        this.NoOfBills = NoOfBills;
    }

    public UserHelperClass(String name, String email, String phoneNo) {
        Name = name;
        Email = email;
        PhoneNo = phoneNo;
    }

    public String getCategoryKey() {
        return CategoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        CategoryKey = categoryKey;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getNoOfBills() {
        return NoOfBills;
    }

    public void setNoOfBills(String noOfBills) {
        NoOfBills = noOfBills;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

}
