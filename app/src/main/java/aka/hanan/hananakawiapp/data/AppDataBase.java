package aka.hanan.hananakawiapp.data;


///3

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import aka.hanan.hananakawiapp.data.Interface.MessageQuery;
import aka.hanan.hananakawiapp.data.Interface.RmozQuery;
import aka.hanan.hananakawiapp.data.Interface.UserQuery;

@Database(entities = {User.class, Rmoz.class, Message.class},version = 5)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase db;
    public abstract UserQuery getUserQuery();
    public abstract RmozQuery getRmozQuery();
    public abstract MessageQuery getMessageQuery();
    public static AppDataBase getDB(Context context)
    {
        if(db==null)
        {
            db = Room.databaseBuilder(context,
                            AppDataBase.class, "database-name")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return db;
    }

}
