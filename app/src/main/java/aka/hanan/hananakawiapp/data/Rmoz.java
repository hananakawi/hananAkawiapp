package aka.hanan.hananakawiapp.data;

public class Rmoz {
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
