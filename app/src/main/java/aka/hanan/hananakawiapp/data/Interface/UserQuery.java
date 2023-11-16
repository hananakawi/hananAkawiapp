package aka.hanan.hananakawiapp.data.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao//لتحديد ان الواجهة تحتوي استعلامات على قاعدة بيانات
public interface UserQuery {

        @Query("SELECT * FROM user")
        List<UserQuery> getAll();

        @Query("SELECT * FROM User WHERE keyid IN (:userIds)")
        List<UserQuery> loadAllByIds(int[] userIds);


       UserQuery checkEmailPassw(String myEmail, String myPassw);

        @Insert
        void insertAll(UserQuery... users);

        @Delete
        void delete(UserQuery user);

        @Query("Delete From user WHERE keyid=:id ")
        void delete(int id);

        @Insert
        void insert(UserQuery myUser);
        @Update
        void update(UserQuery...values);

    }
