package com.siam.crud_roomdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.siam.crud_roomdatabase.Room.User.UserDatabase;
import com.siam.crud_roomdatabase.Room.User.userDAO;
import com.siam.crud_roomdatabase.Room.User.userEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    Button submit;
    EditText edit_Name, edit_number;
    userDAO dao;
    int ID = 0;
    boolean update = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_view = findViewById(R.id.recycler_view);
        submit = findViewById(R.id.submit);
        edit_Name = findViewById(R.id.edit_Name);
        edit_number = findViewById(R.id.edit_number);

        UserDatabase database = Room.databaseBuilder(this, UserDatabase.class, "user")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        dao = database.dao();

        submit.setOnClickListener(v -> {
            if (update) {
                String name = edit_Name.getText().toString();
                String number = edit_number.getText().toString();

                dao.updateUser(name, number, ID);

                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            } else {
                String name = edit_Name.getText().toString();
                String number = edit_number.getText().toString();

                dao.addUser(new userEntity(name, number));

                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }

        });

        userAdapter adapter = new userAdapter();
        recycler_view.setAdapter(adapter);
    }

    public class userAdapter extends RecyclerView.Adapter {
        /*UserDatabase database = Room.databaseBuilder(MainActivity.this, UserDatabase.class, "user")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        userDAO dao = database.dao();*/

        List<userEntity> userList = dao.getUser();

        public class userViewHolder extends RecyclerView.ViewHolder {
            TextView name, phone;
            Button update, delete;

            public userViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                phone = itemView.findViewById(R.id.phone);
                update = itemView.findViewById(R.id.update);
                delete = itemView.findViewById(R.id.delete);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View view = inflater.inflate(R.layout.user_item, viewGroup, false);

            return new userViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
            userViewHolder holder = (userViewHolder) viewHolder;

            holder.name.setText(userList.get(i).getName());
            holder.phone.setText(userList.get(i).getNumber());

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dao.deleteUser(userList.get(i).getId());

                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });

            holder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edit_Name.setText(userList.get(i).getName());
                    edit_number.setText(userList.get(i).getNumber());
                    ID = userList.get(i).getId();
                    update = true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }
    }
}