package com.siam.crud_roomdatabase.Room.User;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface userDAO {

    @Insert
    void addUser(userEntity user);

    @Query("SELECT * FROM user")
    List<userEntity> getUser();

    @Query("SELECT * FROM user WHERE id = :ID")
    userEntity getUser_ByID(int ID);

    @Query("UPDATE user SET name =:name, number = :phone WHERE id =:id ")
    void updateUser(String name, String phone, int id);

    @Query("DELETE FROM user WHERE id =:ID")
    void deleteUser (int ID);
}
