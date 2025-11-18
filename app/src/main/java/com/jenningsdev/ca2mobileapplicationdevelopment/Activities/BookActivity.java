package com.jenningsdev.ca2mobileapplicationdevelopment.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jenningsdev.ca2mobileapplicationdevelopment.Data.Database.DBHandler;
import com.jenningsdev.ca2mobileapplicationdevelopment.R;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    EditText titleEditText, authorEditText, dateEditText;
    Spinner categorySpinner;
    String title, author, category, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        categorySpinner = findViewById(R.id.category_spinner);

        List<String> categories = new ArrayList<>();
        categories.add("Fantasy");
        categories.add("Sci-fi");
        categories.add("Technology");
        categories.add("Lifestyle");
        categories.add("Romance");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    public void addBook(View view) {
        titleEditText = findViewById(R.id.book_title_editText);
        authorEditText = findViewById(R.id.book_author_editText);
        dateEditText = findViewById(R.id.date_editText);
        category = categorySpinner.getSelectedItem().toString();

        title = titleEditText.getText().toString();
        author = authorEditText.getText().toString();
        date = dateEditText.getText().toString();

        DBHandler db = new DBHandler(this);
        db.addBook(title, author, category, date, false);
        db.close();

        setResult(RESULT_OK);
        finish();
    }
}
