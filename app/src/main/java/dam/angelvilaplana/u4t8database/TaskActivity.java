package dam.angelvilaplana.u4t8database;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import dam.angelvilaplana.u4t8database.data.TodoListDBManager;
import dam.angelvilaplana.u4t8database.model.Task;

public class TaskActivity extends AppCompatActivity {

    private EditText etTodo;

    private EditText etToAccomplish;

    private EditText etDescription;

    private Spinner spinPriority;

    private Spinner spinStatus;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");

        setUI();
        loadData();
    }

    private void setUI() {
        etTodo = findViewById(R.id.etTodo);
        etToAccomplish = findViewById(R.id.etToAccomplish);
        etDescription = findViewById(R.id.etDescription);

        // TODO 6.1 Add two new camps in the table TASKS
        spinPriority = findViewById(R.id.spinPriority);
        spinPriority.setAdapter(spinnerAdapter(R.array.priority));

        spinStatus = findViewById(R.id.spinStatus);
        spinStatus.setAdapter(spinnerAdapter(R.array.status));

        // Hide button delete if task is being inserted
        // and change title
        if (task == null) {
            setTitle(R.string.add_task_activity_name);
            Button btDelete = findViewById(R.id.btDelete);
            btDelete.setVisibility(View.INVISIBLE);
        } else {
            setTitle(R.string.edit_task_activity_name);
        }
    }

    /**
     * TODO 6.2 get data if intent has data
     */
    private void loadData() {
        if (task != null) {
            etTodo.setText(task.getToDo());
            etToAccomplish.setText(task.getToAccomplish());
            etDescription.setText(task.getDescription());
            spinPriority.setSelection(task.getSelectionPriority());
            spinStatus.setSelection(task.getSelectionStatus());
        }
    }

    /**
     * Get the default spinner adapter from an array in resources
     */
    private ArrayAdapter<CharSequence> spinnerAdapter(int textArrayResId) {
        return ArrayAdapter.createFromResource(
                this,
                textArrayResId,
                android.R.layout.simple_spinner_item
        );
    }

    /**
     * TODO 6.2 Save, Update & Delete Buttons
     */
    public void onClick(View view) {
        if (view.getId() == R.id.btSave) {
            if (etTodo.getText().toString().isEmpty()) {
                Toast.makeText(this, "Task name is empty", Toast.LENGTH_LONG).show();
            } else if (task == null) {
                TodoListDBManager todoListDBManager = new TodoListDBManager(this);
                todoListDBManager.insert(
                        etTodo.getText().toString(),
                        etToAccomplish.getText().toString(),
                        etDescription.getText().toString(),
                        spinPriority.getSelectedItemPosition(),
                        spinStatus.getSelectedItemPosition()
                );
                Toast.makeText(this, String.format("Added \"%s\" task",  etTodo.getText().toString()),
                        Toast.LENGTH_LONG).show();
            } else {
                TodoListDBManager todoListDBManager = new TodoListDBManager(this);
                todoListDBManager.update(
                        etTodo.getText().toString(),
                        etToAccomplish.getText().toString(),
                        etDescription.getText().toString(),
                        spinPriority.getSelectedItemPosition(),
                        spinStatus.getSelectedItemPosition(),
                        task.getId()
                );
                Toast.makeText(this, String.format("Updated \"%s\" task",  etTodo.getText().toString()),
                        Toast.LENGTH_LONG).show();
            }
        } else if (view.getId() == R.id.btDelete) {
            TodoListDBManager todoListDBManager = new TodoListDBManager(this);
            todoListDBManager.delete(task.getId());
            Toast.makeText(this, String.format("Task \"%s\" deleted", task.getToDo()),
                    Toast.LENGTH_LONG).show();
        }

        finish();
    }

}