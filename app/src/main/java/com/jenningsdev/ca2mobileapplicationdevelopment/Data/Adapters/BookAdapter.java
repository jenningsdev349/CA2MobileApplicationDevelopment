package com.jenningsdev.ca2mobileapplicationdevelopment.Data.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jenningsdev.ca2mobileapplicationdevelopment.Data.Model.Book;
import com.jenningsdev.ca2mobileapplicationdevelopment.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private Context context;
    private List<Book> itemList;
    private List<Book> fullList;
    private OnBookClickListener listener;

    public BookAdapter(Context context, List<Book> itemList, OnBookClickListener listener) {
        this.context = context;
        this.itemList = new ArrayList<>(itemList);
        this.fullList = new ArrayList<>(itemList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_view, parent, false);
        return new BookViewHolder(view);
    }

    public interface OnBookClickListener {
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = itemList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filter(String search) {
        List<Book> filteredList = new ArrayList<>();

        if (search == null || search.trim().isEmpty()) {
            filteredList.addAll(fullList);
        } else {
            for (Book book : itemList) {
                if (book.getTitle().contains(search)) {
                    filteredList.add(book);
                }
            }
        }

        itemList.clear();
        itemList.addAll(filteredList);
        notifyDataSetChanged();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, authorTextView, genreTextView, dateTextView, statusTextView;
        Spinner statusSpinner;
        Button reviewsButton;

        BookViewHolder(@NonNull View bookView) {
            super(bookView);
            titleTextView = bookView.findViewById(R.id.book_title_textView);
            authorTextView = bookView.findViewById(R.id.book_author_textView);
            genreTextView = bookView.findViewById(R.id.genre_textView);
            dateTextView = bookView.findViewById(R.id.date_textView);
            statusTextView = bookView.findViewById(R.id.status_textView);
            statusSpinner = bookView.findViewById(R.id.status_spinner);
            reviewsButton = bookView.findViewById(R.id.reviews_button);
        }

        void bind(final Book book) {
            titleTextView.setText(book.getTitle());
            authorTextView.setText(String.format("By " + book.getAuthor()));
            genreTextView.setText(String.format("Genre: " + book.getCategory()));
            dateTextView.setText(String.format("Start date: " + book.getStartDate()));

            List<String> statusOptions = Arrays.asList("In Progress", "Finished");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    itemView.getContext(),
                    android.R.layout.simple_spinner_item,
                    statusOptions
            );

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            statusSpinner.setAdapter(adapter);

        }
    }
}
