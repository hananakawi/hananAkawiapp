package aka.hanan.hananakawiapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي ينتج بسكل تلقائي
    public long keyid;
    @ColumnInfo(name = "full_Name")
    public  String sentence;
    public String ImageHand;

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

    public static class Rmoz {
        public String text;
        public String letter;
        public String ImageHand;

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
}
