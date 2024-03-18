package com.siam.crud_roomdatabase.Room.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {userEntity.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract userDAO dao();
}
