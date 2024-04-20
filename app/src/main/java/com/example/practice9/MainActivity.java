package com.example.practice9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity
{
    private static final String KEY_FILE_NAME = "file_name";
    private static final String KEY_FILE_CONTENT = "file_content";
    private static final String KEY_FILE_CONTENT_VIEW = "file_content_view";
    private EditText fileNameEditText, fileContentEditText;
    private TextView fileContentTextView;
    private String fileName, fileContent;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов разметки
        fileNameEditText = findViewById(R.id.fileNameEditText);
        fileContentEditText = findViewById(R.id.fileContentEditText);
        fileContentTextView = findViewById(R.id.fileContentTextView);

        // присвоение текущего контекста переменной context
        context = this;

        // Установка слушателей для кнопок
        findViewById(R.id.saveButton).setOnClickListener(v -> saveFile());
        findViewById(R.id.readButton).setOnClickListener(v -> readFile());
        findViewById(R.id.deleteButton).setOnClickListener(v -> deleteFile());
        findViewById(R.id.appendButton).setOnClickListener(v -> appendToFile());

    }


    // Метод сохранения файла
    private void saveFile() {
        fileName = fileNameEditText.getText().toString(); // получение текста из поля ввода имени файла
        fileContent = fileContentEditText.getText().toString(); // получение текста из поля ввода содержимого файла

        try
        {
            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE); // открытие потока для записи в файл
            fos.write(fileContent.getBytes()); // запись содержимого файла в поток
            fos.close(); // закрытие потока
            Toast.makeText(context, "Файл успешно сохранён!", Toast.LENGTH_SHORT).show(); // вывод сообщения об успешном сохранении файла
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    // Метод чтения файла
    private void readFile() {
        fileName = fileNameEditText.getText().toString(); // получение текста из поля ввода имени файла

        try
        {
            FileInputStream fis = openFileInput(fileName); // открытие потока для чтения файла
            InputStreamReader isr = new InputStreamReader(fis);  // инициализация InputStreamReader для чтения байтов из FileInputStream

            BufferedReader br = new BufferedReader(isr); // инициализация BufferedReader для чтения текста из InputStreamReader

            StringBuilder sb = new StringBuilder(); // создание объекта StringBuilder для объединения строк
            String line;
            while ((line = br.readLine()) != null)  // чтение строк из файла пока не достигнут конец файла
            {
                sb.append(line).append("\n");  // добавление строки в StringBuilder с переводом строки
            }
            fileContentTextView.setText(sb.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    // Удалеие файла
    private void deleteFile() {
        fileName = fileNameEditText.getText().toString();

        new AlertDialog.Builder(this) // создание нового AlertDialog
                .setTitle("Удаление файла") // заголовок
                .setMessage("Вы точно хотите удалить " + fileName + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteFile(fileName); // удаление файла
                    Toast.makeText(context, "Файл успешно удалён", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }


    // Добавление
    private void appendToFile() {
        fileName = fileNameEditText.getText().toString();
        String appendContent = fileContentEditText.getText().toString(); // получение текста из поля ввода для добавления в файл

        try {
            FileOutputStream fos = openFileOutput(fileName, MODE_APPEND); // открытие потока для добавления контента в файл т.к. MODE_APPEND
            fos.write(appendContent.getBytes()); // запись
            fos.close(); // закрытие потока
            Toast.makeText(context, "Добавление прошло успешно", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_FILE_NAME, fileNameEditText.getText().toString()); // сохранение имени файла
        outState.putString(KEY_FILE_CONTENT, fileContentEditText.getText().toString()); // сохранение содержимого файла
        outState.putString(KEY_FILE_CONTENT_VIEW, fileContentTextView.getText().toString()); // сохранение текста из TextView
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String fileName = savedInstanceState.getString(KEY_FILE_NAME, ""); // восстановление имени файла из сохраненного состояния
        String fileContent = savedInstanceState.getString(KEY_FILE_CONTENT, ""); // восстановление содержимого файла из сохраненного состояния
        String fileContentView = savedInstanceState.getString(KEY_FILE_CONTENT_VIEW, ""); // восстановление текста из TextView из сохраненного состояния
        fileNameEditText.setText(fileName); // установка восстановленного имени файла в поле ввода имени файла
        fileContentEditText.setText(fileContent); // установка восстановленного содержимого файла в поле ввода содержимого файла
        fileContentTextView.setText(fileContentView); // установка восстановленного текста из TextView
    }
}