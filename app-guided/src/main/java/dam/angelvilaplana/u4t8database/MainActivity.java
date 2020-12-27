package dam.angelvilaplana.u4t8database;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import dam.angelvilaplana.u4t8database.data.TodoListDBManager;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvTodoList;

    private TodoListDBManager todoListDBManager;

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get instance to our DB Manager
        todoListDBManager = new TodoListDBManager(this);
        myAdapter = new MyAdapter(todoListDBManager);

        setUI();
    }

    private void setUI() {
        // ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set Fab: Opens and activity to ADD A NEW TASK to the DB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start activity to add a new record to our table
                startActivity(new Intent(getApplicationContext(), AddTaskActivity.class));
            }
        });

        // Set recyclerView
        rvTodoList = findViewById(R.id.rvTodoList);
        rvTodoList.setHasFixedSize(true);
        rvTodoList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvTodoList.setAdapter(myAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myAdapter.getData();
    }

    @Override
    protected void onDestroy() {
        todoListDBManager.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item_recycler_view clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}