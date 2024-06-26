package aka.hanan.hananakawiapp.data.Tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyUser {
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي ينتج بسكل تلقائي
    public long keyid;
    @ColumnInfo(name = "full_Name")
    public String name;//اسم الشخص
    public boolean cliant;//هل الشخص مستخدم عادي
    public  boolean princepal;//هل المستخدم مدير
    public String email;//البريد الالكتروني الشخصي للشخص
    public String pass;//كلمة السر
    String image;
    String phone;

    public boolean isPrincepal() {
        return princepal;
    }

    public void setPrincepal(boolean princepal) {
        this.princepal = princepal;
    }

    public boolean isCliant() {
        return cliant;
    }

    public void setCliant(boolean cliant) {
        this.cliant = cliant;
    }

    public String getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getKeyid() {
        return keyid;
    }

    public void setKeyid(long keyid) {
        this.keyid = keyid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    }

