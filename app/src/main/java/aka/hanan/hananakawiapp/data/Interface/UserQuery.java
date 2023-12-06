package aka.hanan.hananakawiapp.data.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import aka.hanan.hananakawiapp.data.User;

@Dao//لتحديد ان الواجهة تحتوي استعلامات على قاعدة بيانات
public interface UserQuery {

        @Query("SELECT * FROM User")
        List<User> getAll();

        @Query("SELECT * FROM User WHERE keyid IN (:userIds)")
        List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM User Where email = :myEmail")
    User checkEmailPassw(String myEmail, String myPassw);

        @Insert
        void insertAll(User... users);

        @Delete
        void delete(User user);

        @Query("Delete From user WHERE keyid=:id ")
        void delete(int id);

        @Insert
        void insert(User myUser);
        @Update
        void update(User...values);

    }
