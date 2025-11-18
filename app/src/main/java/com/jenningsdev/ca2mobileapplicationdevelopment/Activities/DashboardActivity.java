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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.jenningsdev.ca2mobileapplicationdevelopment.Data.Adapters.BookAdapter;
import com.jenningsdev.ca2mobileapplicationdevelopment.Data.Model.Book;
import com.jenningsdev.ca2mobileapplicationdevelopment.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    List<Book> displayItems = new ArrayList<>();
    BookAdapter adapter;

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

        TextChangeHandler tch = new TextChangeHandler();

        MaterialToolbar toolbar = findViewById(R.id.material_toolbar);
        setSupportActionBar(toolbar);

        EditText searchEditText = findViewById(R.id.search_editText);
        searchEditText.addTextChangedListener(tch);

        displayItems.add(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", new ArrayList<>(), "2025-01-05", false));
        displayItems.add(new Book("1984", "George Orwell", "Technology", new ArrayList<>(), "2025-02-12", true));
        displayItems.add(new Book("To Kill a Mockingbird", "Harper Lee", "Lifestyle", new ArrayList<>(), "2025-03-01", false));
        displayItems.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Romance", new ArrayList<>(), "2025-03-10", true));
        displayItems.add(new Book("The Catcher in the Rye", "J.D. Salinger", "Romance", new ArrayList<>(), "2025-04-20", false));

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
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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
