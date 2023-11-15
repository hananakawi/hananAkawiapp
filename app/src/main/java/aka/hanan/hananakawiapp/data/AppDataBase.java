package aka.hanan.hananakawiapp.data;


///3

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Rmoz.class, Message.class},version = 5)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase db;
    public abstract User getUserQuery();
    public abstract Rmoz getRmozQuery();
    public abstract Message getMessageQuery();
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
