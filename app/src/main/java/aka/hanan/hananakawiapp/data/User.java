package aka.hanan.hananakawiapp.data;

public class User {
    public String name;
    public boolean Isdeaf;
    public  boolean Isdumb;

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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", Isdeaf=" + Isdeaf +
                ", Isdumb=" + Isdumb +
                '}';
    }
}
