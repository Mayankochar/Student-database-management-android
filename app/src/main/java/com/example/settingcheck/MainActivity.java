package com.example.settingcheck;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText e1, e2, e3, e4;
    Button b1, b2, b3, b4, b5, b6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        e1 = findViewById(R.id.editText_id);
        e2 = findViewById(R.id.editText_name);
        e3 = findViewById(R.id.editText_email);
        e4 = findViewById(R.id.editText_CC);
        b1 = findViewById(R.id.button_add);
        b2 = findViewById(R.id.button_view);
        b3 = findViewById(R.id.button_update);
        b4 = findViewById(R.id.button_delete);
        b5 = findViewById(R.id.button_viewAll);
        b6 = findViewById(R.id.button_deleteAll);
        add();
        view();
        update();
        delete();
        viewAll();
        deleteAll();
    }

    public void add() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isinserted = db.InsertData(e1.getText().toString(), e2.getText().toString(), e3.getText().toString(), e4.getText().toString());
                if (isinserted == true)
                    Toast.makeText(MainActivity.this, "DATA INSERTED", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void view() {
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = e1.getText().toString();
                if (id.equals(String.valueOf("")))
                    e1.setError("ENTER ID");
                Cursor cr = db.ViewData(id);
                if (cr.getCount() == 0) {
                    ShowMessage("ERROR!!!", "No Data Found");
                    return;
                }
                String data = null;
                if (cr.moveToNext())
                    data = "ID= " + cr.getString(0) + "\nName= " + cr.getString(1) + "\nEmail= " + cr.getString(2) + "\nCourse Count= " + cr.getString(3);
//                Toast.makeText(MainActivity.this, "bbbb", Toast.LENGTH_SHORT).show();
                ShowMessage("Viewing Data", data);
            }
        });
    }

    public void update() {
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e1.getText().toString().equals(String.valueOf(""))) {
                    e1.setError("ENTER THE ID");
                }
                Boolean isupdate = db.UpdateData(e1.getText().toString(), e2.getText().toString(), e3.getText().toString(), e4.getText().toString());
                if (isupdate == true)
                    Toast.makeText(MainActivity.this, "SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void delete() {
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e1.getText().toString().equals(String.valueOf(""))) {
                    e1.setError("ENTER THE ID");
                }
                Integer isdeleted = db.DeleteData(e1.getText().toString());
                if (isdeleted > 0)
                    Toast.makeText(MainActivity.this, "DELETION SUCCESSFUL", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void viewAll() {
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cr = db.ViewAllData();
                if (cr.getCount() == 0) {
                    ShowMessage("ERROR!!!", "No Data Found");
                    return;
                }
                StringBuffer data = new StringBuffer();
                while (cr.moveToNext()) {
                    data.append("ID= " + cr.getString(0) + "\nName= " + cr.getString(1) + "\nEmail= " + cr.getString(2) + "\nCourse Count= " + cr.getString(3) + "\n\n");
                }
                ShowMessage("Viewing Data", data.toString());
            }
        });
    }

    public void deleteAll() {
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.DeleteAllData();
                Toast.makeText(MainActivity.this, "DELETION SUCCESSFUL", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowMessage(String title, String message) {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.create();
        ab.setCancelable(true);
        ab.setTitle(title);
        ab.setMessage(message);
        ab.show();
    }
}
