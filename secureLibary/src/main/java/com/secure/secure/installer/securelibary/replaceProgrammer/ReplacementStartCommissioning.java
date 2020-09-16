package com.secure.secure.installer.securelibary.replaceProgrammer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.secure.secure.installer.securelibary.R;
import com.secure.secure.installer.securelibary.database.GlobalClass;

public class ReplacementStartCommissioning extends AppCompatActivity {

    TextView instructions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replace_start_commissioning_layout);

        instructions = findViewById(R.id.instructions);

        // setup toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        GlobalClass.setToolbar(this, mToolbar, getString(R.string.replace_a_programmer));

        String details = "- at a distance of 1.5m above the floor \n- not in direct in sun light, or near a heat source, window or door" +
                "\n- within the line of sight range, up to 60m from the receiver";
        instructions.setText(details);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            GlobalClass.navigateToHomePage(ReplacementStartCommissioning.this);
        }
        return super.onOptionsItemSelected(item);
    }
}
