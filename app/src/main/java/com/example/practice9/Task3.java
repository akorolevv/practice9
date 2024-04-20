package com.example.practice9;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Task3 extends AppCompatActivity {

    private EditText editTextFileName, editTextFileContent;
    private Button buttonSave, buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task3);

        // Инициализации элементов разметки
        editTextFileName = findViewById(R.id.editTextFileName);
        editTextFileContent = findViewById(R.id.editTextFileContent);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDelete);


        // установка слушателя нажатий на кнопку сохранения файла
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile();
            }
        });


        // установка слушателя нажатий на кнопку удаления файла
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFile();
            }
        });
    }

    private void saveFile() {
        String fileName = editTextFileName.getText().toString();
        String fileContent = editTextFileContent.getText().toString();

        if (!fileName.isEmpty() && !fileContent.isEmpty()) // если имя файла и содержимое не пустые то
        {
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS); // получение директории для сохранения файла
            File file = new File(directory, fileName + ".txt"); // создание объекта File для файла

            try {
                FileOutputStream outputStream = new FileOutputStream(file); // открытие потока для записи в файл
                outputStream.write(fileContent.getBytes()); // запись содержимого файла в поток
                outputStream.close();  // закрытие потока
                Toast.makeText(this, "Файл успешно сохранен", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка при сохранении файла", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Пожалуйста, введите название и содержимое файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFile() {
        String fileName = editTextFileName.getText().toString();

        if (!fileName.isEmpty()) // если имя файла не пустое
        {
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS); // получение директории для удаления файла
            File file = new File(directory, fileName + ".txt"); // создание объекта File для файла


            if (file.exists())
            {
                file.delete(); // удаление
                Toast.makeText(this, "Файл успешно удален", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Файл не найден", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Пожалуйста, введите название файла", Toast.LENGTH_SHORT).show();
        }
    }
}