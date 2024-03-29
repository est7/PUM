package pl.edu.uwr.pum.counterappjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int count;
    private TextView showCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showCount = findViewById(R.id.show_count);

        if (savedInstanceState != null)
            count = savedInstanceState.getInt("counter_state");

        if (showCount != null)
            showCount.setText(String.format(Locale.GERMAN, "%,d", count));
    }

    public void countUpButton(View view) {
        count++;
        if (showCount != null)
            showCount.setText(String.format(Locale.GERMAN, "%,d", count));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("counter_state", count);
    }
}