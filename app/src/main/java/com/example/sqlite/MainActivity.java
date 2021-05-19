package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnInsertData, btnRetrieveData, btnUpdateData, btnDeleteData;
    TextInputLayout tilInsert, tilDataToBeUpdated, tilUpdatedData, tilDataToBeDeleted;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsertData = findViewById(R.id.btnInsertData);
        btnRetrieveData = findViewById(R.id.btnRetrieveData);
        btnUpdateData = findViewById(R.id.btnUpdateData);
        btnDeleteData = findViewById(R.id.btnDeleteData);

        tilInsert = findViewById(R.id.tilInsert);
        tilDataToBeUpdated = findViewById(R.id.tilDataToBeUpdated);
        tilUpdatedData = findViewById(R.id.tilUpdatedData);
        tilDataToBeDeleted = findViewById(R.id.tilDataToBeDeleted);

        btnInsertData.setOnClickListener(this);
        btnRetrieveData.setOnClickListener(this);
        btnUpdateData.setOnClickListener(this);
        btnDeleteData.setOnClickListener(this);

        databaseManager = new DatabaseManager(this, DatabaseManager.DATABASE_NAME, null,
                DatabaseManager.VERSION);
    }

    @Override public void onClick(View v) {
        int rows;
        int id = -1;

        switch (v.getId()) {
            case R.id.btnInsertData:
                String nameToInsert = tilInsert.getEditText().getText().toString();
                if (databaseManager.insert(nameToInsert)) {
                    Toast.makeText(this, "Data inserted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Operation Failed!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnRetrieveData:
                List<UsersModel> users = databaseManager.getAllUsers();
                Toast.makeText(this, users.toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnUpdateData:
                String oldName = tilDataToBeUpdated.getEditText().getText().toString();
                rows = databaseManager.getNumberOfRows();

                for (int i = 0; i < rows; i++) {
                    if (oldName.equals(databaseManager.getAllUsers().get(i).getName())) {
                        id = databaseManager.getAllUsers().get(i).getID();
                        break;
                    }
                }
                String newName = tilUpdatedData.getEditText().getText().toString();

                if (id != -1) {
                    if (databaseManager.update(id, newName)) {
                        Toast.makeText(this, "Data updated.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Operation Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.btnDeleteData:
                String nameToDelete = tilDataToBeDeleted.getEditText().getText().toString();
                rows = databaseManager.getNumberOfRows();

                for (int i = 0; i < rows; i++) {
                    if (nameToDelete.equals(databaseManager.getAllUsers().get(i).getName())) {
                        id = databaseManager.getAllUsers().get(i).getID();
                        break;
                    }
                }

                if (id != -1) {
                    if (databaseManager.delete(id) == 1) {
                        Toast.makeText(this, "Data deleted", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Operation Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}