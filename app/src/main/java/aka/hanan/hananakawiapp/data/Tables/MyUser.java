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
    public boolean Isdeaf;//هل الشخص اخرس
    public  boolean Isdumb;//هل هو اطرش
    public String email;//البريد الالكتروني الشخصي للشخص
    public String pass;//كلمة السر

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsdeaf() {
        return Isdeaf;
    }

    public void setIsdeaf(boolean isdeaf) {
        Isdeaf = isdeaf;
    }

    public boolean isIsdumb() {
        return Isdumb;
    }

    public void setIsdumb(boolean isdumb) {
        Isdumb = isdumb;
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", Isdeaf=" + Isdeaf +
                ", Isdumb=" + Isdumb +
                '}';
    }
}
