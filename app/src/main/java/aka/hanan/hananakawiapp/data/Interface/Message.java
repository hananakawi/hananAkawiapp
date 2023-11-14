package aka.hanan.hananakawiapp.data.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao//لتحديد ان الواجهة تحتوي استعلامات على قاعدة بيانات

public interface Message {


        @Query("SELECT * FROM message")
    List<Message> getAll();

        @Query("SELECT * FROM message WHERE keyid IN (:userIds)")
        List<Message> loadAllByIds(int[] userIds);



        @Insert
        void insertAll(Message... messages);

        @Delete
        void delete(Message message);

        @Query("Delete From Message WHERE keyid=:id ")
        void delete(int id);

        @Insert
        void insert(Message message);
        @Update
        void update(Message...values);

}
