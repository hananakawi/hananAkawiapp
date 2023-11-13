package aka.hanan.hananakawiapp.data.Interface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

public interface Rmoz  {

    @Insert
    default Void insertrmoz(Rmoz... t) {
        return null;
    }

    @Update
    Void updatermoz(Rmoz... t);

    @Delete
    Void deletermoz(Rmoz... t);

    @Query("Delete From Rmoz WHERE keyid=:id ")
    void delete(int id);
}
