package aka.hanan.hananakawiapp.data.Interface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao//لتحديد ان الواجهة تحتوي استعلامات على قاعدة بيانات
public interface Rmoz  {


        @Query("SELECT * FROM Rmoz")
        List<Rmoz> getAll();

        @Query("SELECT * FROM Rmoz WHERE keyid IN (:userIds)")
        List<Rmoz> loadAllByIds(int[] userIds);




        @Insert
        void insertAll(Rmoz...rmoz);

        @Delete
        void delete(Rmoz rmoz);

        @Query("Delete From Rmoz WHERE keyid=:id ")
        void delete(int id);

        @Insert
        void insert(Rmoz rmoz);
        @Update
        void update(Rmoz...rmoz);

    }
