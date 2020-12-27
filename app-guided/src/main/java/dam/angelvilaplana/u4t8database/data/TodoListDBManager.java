package dam.angelvilaplana.u4t8database.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import dam.angelvilaplana.u4t8database.model.Task;

import java.util.ArrayList;

public class TodoListDBManager {

    private TodoListDBHelper todoListDBHelper;

    public TodoListDBManager(Context context) {
        todoListDBHelper = TodoListDBHelper.getInstance(context);
    }

    // OPERATIONS

    /**
     * CREATE NEW ROW
     */
    public void insert(String todo, String when, String description) {
        // Open database to read and write
        SQLiteDatabase sqLiteDatabase = todoListDBHelper.getWritableDatabase();

        if (sqLiteDatabase != null) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(TodoListDBContract.Tasks.TODO, todo);
            contentValues.put(TodoListDBContract.Tasks.TO_ACCOMPLISH, when);
            contentValues.put(TodoListDBContract.Tasks.DESCRIPTION, description);

            sqLiteDatabase.insert(TodoListDBContract.Tasks.TABLE_NAME, null, contentValues);

            /*
             * https://developer.android.com/training/data-storage/sqlite#PersistingDbConnection:
             * getWritableDatabase & getReadableDatabase() are expensive, you should leave your database
             * connection open for as long as you possibility need to access it
             * sqLiteDatabase.close()
             */
        }
    }

    /**
     * Get all data from Tasks table
     */
    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskList = new ArrayList<>();

        // Open database to read
        SQLiteDatabase sqLiteDatabase = todoListDBHelper.getReadableDatabase();

        if (sqLiteDatabase != null) {
            String[] projection = new String[] {
                    TodoListDBContract.Tasks._ID,
                    TodoListDBContract.Tasks.TODO,
                    TodoListDBContract.Tasks.TO_ACCOMPLISH,
                    TodoListDBContract.Tasks.DESCRIPTION
            };

            Cursor cursorTodoList = sqLiteDatabase.query(
                    TodoListDBContract.Tasks.TABLE_NAME,
                    projection,          // The columns toView return
                    null,       // no WHERE clause
                    null,    // no values for the WHERE clause
                    null,       // don't group the rows
                    null,        // don't filter by row groups
                    null);      // don't sort rows

            if (cursorTodoList != null) {
                // Get the column indexes for required columns
                int _idIndex = cursorTodoList.getColumnIndexOrThrow(TodoListDBContract.Tasks._ID);
                int todoIndex = cursorTodoList.getColumnIndexOrThrow(TodoListDBContract.Tasks.TODO);
                int to_AccomplishIndex = cursorTodoList.getColumnIndexOrThrow(TodoListDBContract.Tasks.TO_ACCOMPLISH);
                int descriptionIndex = cursorTodoList.getColumnIndexOrThrow(TodoListDBContract.Tasks.DESCRIPTION);

                // Read data and add to ArrayList
                while (cursorTodoList.moveToNext()) {
                    Task task = new Task(
                            cursorTodoList.getInt(_idIndex),
                            cursorTodoList.getString(todoIndex),
                            cursorTodoList.getString(to_AccomplishIndex),
                            cursorTodoList.getString(descriptionIndex)
                    );
                    taskList.add(task);
                }

                // Close cursor to free resources
                cursorTodoList.close();
            }
        }

        return taskList;
    }

    /**
     * Closes any opened database object
     */
    public void close() {
        todoListDBHelper.close();
    }

}
