package dam.angelvilaplana.u4t8database;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import dam.angelvilaplana.u4t8database.data.TodoListDBContract;
import dam.angelvilaplana.u4t8database.data.TodoListDBManager;
import dam.angelvilaplana.u4t8database.model.Task;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    private RecyclerView rvTodoList;

    private TodoListDBManager todoListDBManager;

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get instance to our DB Manager
        todoListDBManager = new TodoListDBManager(this);
        myAdapter = new MyAdapter(todoListDBManager, this);

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
                startActivity(new Intent(getApplicationContext(), TaskActivity.class));
            }
        });

        // Set recyclerView
        rvTodoList = findViewById(R.id.rvTodoList);
        rvTodoList.setHasFixedSize(true);
        rvTodoList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvTodoList.setAdapter(myAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));
        rvTodoList.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        String selectionFilter = getIntent().getStringExtra("selectionFilter");
        myAdapter.getData(selectionFilter);
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

        // TODO 6.3 Menu options
        if (id == R.id.showNotStarted) {
            getIntent().putExtra("selectionFilter", TodoListDBContract.Tasks.STATUS + " = " +  0);
        } else if (id == R.id.showInProgress) {
            getIntent().putExtra("selectionFilter", TodoListDBContract.Tasks.STATUS + " = " +  1);
        } else if (id == R.id.showCompleted) {
            getIntent().putExtra("selectionFilter", TodoListDBContract.Tasks.STATUS + " = " +  2);
        } else if (id == R.id.showAll) {
            getIntent().putExtra("selectionFilter", "");
        } else  if (id == R.id.deleteCompletedTasks) {
            int rowsDelete = todoListDBManager.deleteCompleted();
            Toast.makeText(this, rowsDelete + " tasks removed", Toast.LENGTH_LONG).show();
        } else if (id == R.id.deleteAllTasks) {
            int rowsDelete = todoListDBManager.deleteAll();
            Toast.makeText(this, rowsDelete + " tasks removed", Toast.LENGTH_LONG).show();
        }

        getData();
        return super.onOptionsItemSelected(item);
    }

    /**
     * TODO 6.2 - Click listener item RecyclerView
     */
    @Override
    public void onItemClick(Task task) {
        Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
        intent.putExtra("task", task);
        startActivity(intent);
    }

}