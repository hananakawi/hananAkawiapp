package aka.hanan.hananakawiapp.data.Tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Rmoz  {
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي ينتج بسكل تلقائي
    public long keyid;
    @ColumnInfo(name = "full_Name")
    public String text;//نص من كتابة عادي ل لغة الاشارة
    public String letter;//حرف نحوله من حرف الى لغة اشارة
    public String imageHand;//صورة اليد الخاصة بالحرف او بالنص
    public  String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageHand() {
        return imageHand;
    }

    public void setImageHand(String imageHand) {
        this.imageHand = imageHand;
    }

    public long getKeyid() {
        return keyid;
    }

    public void setKeyid(long keyid) {
        this.keyid = keyid;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
