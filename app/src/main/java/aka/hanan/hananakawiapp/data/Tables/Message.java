package aka.hanan.hananakawiapp.data.Tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

/**
 * فئة الرسائل  تحوي صفتين
 */
@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي ينتج بسكل تلقائي
    public long keyid;
    @ColumnInfo(name = "full_Name")
    public  String sentence;//
    public String ImageHand;//

    public String getSentence() {
        return sentence;
    }

    public String getImageHand() {
        return ImageHand;
    }

    public long getKeyid() {
        return keyid;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void setImageHand(String imageHand) {
        ImageHand = imageHand;
    }

    public void setKeyid(long keyid) {
        this.keyid = keyid;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sentence='" + sentence + '\'' +
                ", ImageHand=" + ImageHand +
                '}';
    }

}
