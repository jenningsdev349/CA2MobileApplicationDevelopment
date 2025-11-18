package com.jenningsdev.ca2mobileapplicationdevelopment.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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
        displayItems.add(new Book("1984", "George Orwell", "Dystopian", new ArrayList<>(), "2025-02-12", true));
        displayItems.add(new Book("To Kill a Mockingbird", "Harper Lee", "Classic", new ArrayList<>(), "2025-03-01", false));
        displayItems.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Classic", new ArrayList<>(), "2025-03-10", true));
        displayItems.add(new Book("The Catcher in the Rye", "J.D. Salinger", "Literature", new ArrayList<>(), "2025-04-20", false));

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
            adapter.filter(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
