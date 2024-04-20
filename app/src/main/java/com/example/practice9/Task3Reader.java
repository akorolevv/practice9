package com.example.practice9;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Task3Reader extends AppCompatActivity {

    private EditText editTextFileName;
    private Button buttonRead;
    private TextView textViewFileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task3reader);

        editTextFileName = findViewById(R.id.editTextFileName);
        buttonRead = findViewById(R.id.buttonRead);
        textViewFileContent = findViewById(R.id.textViewFileContent);

        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile();
            }
        });
    }

    private void readFile() {
        String fileName = editTextFileName.getText().toString();

        if (!fileName.isEmpty()) {
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(directory, fileName + ".txt");

            if (file.exists()) // если файл существует
            {
                StringBuilder stringBuilder = new StringBuilder(); // создание объекта StringBuilder для объединения строк
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file)); // инициализация BufferedReader для чтения файла
                    String line;
                    while ((line = reader.readLine()) != null) // чтение строк из файла пока не достигнут конец файла
                    {
                        stringBuilder.append(line).append("\n");
                    }
                    reader.close();
                    textViewFileContent.setText(stringBuilder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Task3Reader.this, "Ошибка при чтении файла", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Task3Reader.this, "Файл не найден", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Task3Reader.this, "Пожалуйста, введите название файла", Toast.LENGTH_SHORT).show();
        }
    }
}