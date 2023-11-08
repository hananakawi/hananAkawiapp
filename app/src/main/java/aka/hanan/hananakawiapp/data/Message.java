package aka.hanan.hananakawiapp.data;

import java.util.ArrayList;

public class Message {
    public  String sentence;
    public ArrayList<String> ImageHand;

    public String getSentence() {
        return sentence;
    }

    public ArrayList<String> getImageHand() {
        return ImageHand;
    }

    public void setImageHand(ArrayList<String> imageHand) {
        ImageHand = imageHand;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;

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
