package aka.hanan.hananakawiapp.data.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao//لتحديد ان الواجهة تحتوي استعلامات على قاعدة بيانات

public interface MessageQuery {


        @Query("SELECT * FROM message")
    List<MessageQuery> getAll();

        @Query("SELECT * FROM message WHERE keyid IN (:userIds)")
        List<MessageQuery> loadAllByIds(int[] userIds);



        @Insert
        void insertAll(MessageQuery... messages);

        @Delete
        void delete(MessageQuery message);

        @Query("Delete From Message WHERE keyid=:id ")
        void delete(int id);

        @Insert
        void insert(MessageQuery message);
        @Update
        void update(MessageQuery...values);

}
