package com.jenningsdev.ca2mobileapplicationdevelopment.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.jenningsdev.ca2mobileapplicationdevelopment.Data.Adapters.BookAdapter;
import com.jenningsdev.ca2mobileapplicationdevelopment.Data.Database.DBHandler;
import com.jenningsdev.ca2mobileapplicationdevelopment.Data.Model.Book;
import com.jenningsdev.ca2mobileapplicationdevelopment.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    List<Book> displayItems = new ArrayList<>();
    BookAdapter adapter;
    DBHandler db;

    private static final int REQUEST_ADD_BOOK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboard), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DBHandler(this);
        displayItems = db.getAllBooks();

        TextChangeHandler tch = new TextChangeHandler();

        MaterialToolbar toolbar = findViewById(R.id.material_toolbar);
        setSupportActionBar(toolbar);

        EditText searchEditText = findViewById(R.id.search_editText);
        searchEditText.addTextChangedListener(tch);
        Spinner categoryFilter = findViewById(R.id.category_filter_spinner);
        Spinner statusFilter = findViewById(R.id.status_filter_spinner);

        List<String> categories = Arrays.asList("All", "Fantasy", "Sci-fi", "Technology", "Lifestyle", "Romance");
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryFilter.setAdapter(catAdapter);

        List<String> statuses = Arrays.asList("All", "In Progress", "Finished");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                statuses
        );
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusFilter.setAdapter(statusAdapter);

        categoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                adapter.filterByCategory(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        statusFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = parent.getItemAtPosition(position).toString();
                adapter.filterByStatus(selectedStatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        createDisplayList(displayItems);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_item) {
            Intent intent = new Intent(DashboardActivity.this, BookActivity.class);
            startActivityForResult(intent, REQUEST_ADD_BOOK);
        }

        if (id == R.id.view_profile) {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_BOOK && resultCode == RESULT_OK) {
            List<Book> books = db.getAllBooks();
            createDisplayList(books);
        }
    }
    public void createDisplayList(List<Book> displayItems) {
        adapter = new BookAdapter(this, displayItems, new BookAdapter.OnBookClickListener() {
        });

        RecyclerView recyclerView = findViewById(R.id.book_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public class TextChangeHandler implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            adapter.filterByName(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
