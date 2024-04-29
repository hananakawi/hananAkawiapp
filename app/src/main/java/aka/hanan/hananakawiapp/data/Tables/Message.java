package aka.hanan.hananakawiapp.data.Tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

/**
 * فئة الرسائل  تحوي صفتين
 */
@Entity
public class Message
{
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي ينتج بسكل تلقائي
    public long keyid;//
    @ColumnInfo(name = "full_Name")
    public  String sentence;//الجملة التي سترجم من لغة الاشارة لاحرف عادية
    public String imageHand;// صفة تمثل صورة اليد حسب الرمز الخاص لكل حرف/ جملة

    public String getSentence()
    {
        return sentence;
    }

    public String getImageHand() {
        return imageHand;
    }

    public long getKeyid() {
        return keyid;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void setImageHand(String imageHand) {
        imageHand = imageHand;
    }

    public void setKeyid(long keyid) {
        this.keyid = keyid;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sentence='" + sentence + '\'' +
                ", ImageHand=" + imageHand +
                '}';
    }

}
