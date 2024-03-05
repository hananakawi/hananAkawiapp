package aka.hanan.hananakawiapp.data.Tables;

import androidx.appcompat.app.AppCompatActivity;
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
    public String ImageHand;//صورة اليد الخاصة بالحرف او بالنص

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getImageHand() {
        return ImageHand;
    }

    public void setImageHand(String imageHand) {
        ImageHand = imageHand;
    }

    @Override
    public String toString() {
        return "Rmoz{" +
                "text='" + text + '\'' +
                ", letter='" + letter + '\'' +
                ", ImageHand='" + ImageHand + '\'' +
                '}';
    }

}
