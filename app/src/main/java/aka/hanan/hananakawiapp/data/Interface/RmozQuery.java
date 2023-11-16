package aka.hanan.hananakawiapp.data.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao//لتحديد ان الواجهة تحتوي استعلامات على قاعدة بيانات
public interface RmozQuery {


        @Query("SELECT * FROM Rmoz")
        List<RmozQuery> getAll();

        @Query("SELECT * FROM Rmoz WHERE keyid IN (:userIds)")
        List<RmozQuery> loadAllByIds(int[] userIds);




        @Insert
        void insertAll(RmozQuery...rmoz);

        @Delete
        void delete(RmozQuery rmoz);

        @Query("Delete From Rmoz WHERE keyid=:id ")
        void delete(int id);

        @Insert
        void insert(RmozQuery rmoz);
        @Update
        void update(RmozQuery...rmoz);

    }
