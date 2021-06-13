package com.example.lab71;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class FileInputOutput extends AppCompatActivity {
    EditText edtData;
    Button addToInternal, loadFromExternal, addToExternal, loadFromInternal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_input_output);
        mapping();



        addToInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String data = edtData.getText().toString();
                    FileOutputStream fileOutputStream = openFileOutput("lab7", MODE_PRIVATE);
                    fileOutputStream.write(data.getBytes());
                    fileOutputStream.close();
                } catch (Exception e) {
                    Toast.makeText(FileInputOutput.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(FileInputOutput.this, "Ghi thành công!", Toast.LENGTH_SHORT).show();
            }
        });

        loadFromInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream stream = openFileInput("lab7");
                    Scanner scanner = new Scanner(stream);
                    String s = "";
                    while (scanner.hasNext()) {
                        s+=scanner.nextLine();
                    }
                    scanner.close();
                    edtData.setText(s);
                    Toast.makeText(FileInputOutput.this, "Đọc thành công!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(FileInputOutput.this, "Lỗi đọc file!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addToExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(FileInputOutput.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    String sdCard = Environment.getExternalStorageDirectory().getAbsolutePath() + "/lab7.txt";
                    String data= edtData.getText().toString();
                    try {
                        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(sdCard));
                        writer.write(data);
                        Toast.makeText(FileInputOutput.this, data, Toast.LENGTH_SHORT).show();
                        writer.close();
                    } catch (Exception e) {

                    }
//                    Toast.makeText(FileInputOutput.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(FileInputOutput.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 999);
                }
            }
        });

        loadFromExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(FileInputOutput.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        String sdCardFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/lab7.txt";
                        Scanner scanner = new Scanner(new File(sdCardFile));

                        String duLieu = "";
                        while (scanner.hasNext()) {
                            duLieu += scanner.nextLine();
                        }
                        scanner.close();
                        edtData.setText(duLieu);
                    } catch (Exception e) {
                    }

                } else {
                    ActivityCompat.requestPermissions(FileInputOutput.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 999);
                }
            }
        });

    }

    private void mapping() {
        addToExternal = findViewById(R.id.btnAddToExternal);
        addToInternal = findViewById(R.id.btnAddToInternal);
        loadFromExternal = findViewById(R.id.btnLoadFromExternal);
        loadFromInternal = findViewById(R.id.btnLoadFromInternal);
        edtData = findViewById(R.id.edtData);
    }
}