package dam.angelvilaplana.u4t8database.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
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
    public void insert(String todo, String when, String description, int priority, int status) {
        // Open database to read and write
        SQLiteDatabase sqLiteDatabase = todoListDBHelper.getWritableDatabase(TodoListDBContract.DB_PASSWD);

        if (sqLiteDatabase != null) {
            ContentValues contentValues = getContentValues(todo, when, description, priority, status);
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
     * TODO 6.2 UPDATE ROW
     */
    public void update(String todo, String when, String description, int priority, int status, int id) {
        // Open database to read and write
        SQLiteDatabase sqLiteDatabase = todoListDBHelper.getWritableDatabase(TodoListDBContract.DB_PASSWD);
        String whereClause = TodoListDBContract.Tasks._ID + " = " + id;

        if (sqLiteDatabase != null) {
            ContentValues contentValues = getContentValues(todo, when, description, priority, status);
            sqLiteDatabase.update(TodoListDBContract.Tasks.TABLE_NAME, contentValues, whereClause, null);
        }
    }

    /**
     * TODO 6.2 DELETE ROW
     */
    public void delete(int id) {
        // Open database to read and write
        SQLiteDatabase sqLiteDatabase = todoListDBHelper.getWritableDatabase(TodoListDBContract.DB_PASSWD);
        String whereClause = TodoListDBContract.Tasks._ID + " = " + id;

        if (sqLiteDatabase != null) {
            sqLiteDatabase.delete(TodoListDBContract.Tasks.TABLE_NAME, whereClause, null);
        }
    }

    /**
     * TODO 6.3 DELETE COMPLETED TASKS
     */
    public int deleteCompleted() {
        // Open database to read and write
        SQLiteDatabase sqLiteDatabase = todoListDBHelper.getWritableDatabase(TodoListDBContract.DB_PASSWD);
        String whereClause = TodoListDBContract.Tasks.STATUS + " = " + 2;

        if (sqLiteDatabase != null) {
            return sqLiteDatabase.delete(TodoListDBContract.Tasks.TABLE_NAME, whereClause, null);
        }

        return 0;
    }

    /**
     * DELETE ALL TASKS
     */
    public int deleteAll() {
        // Open database to read and write
        SQLiteDatabase sqLiteDatabase = todoListDBHelper.getWritableDatabase(TodoListDBContract.DB_PASSWD);

        if (sqLiteDatabase != null) {
            return sqLiteDatabase.delete(TodoListDBContract.Tasks.TABLE_NAME, null, null);
        }

        return 0;
    }

    /**
     * Set and get the default content
     */
    private ContentValues getContentValues(String todo, String when, String description, int priority, int status) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(TodoListDBContract.Tasks.TODO, todo);
        contentValues.put(TodoListDBContract.Tasks.TO_ACCOMPLISH, when);
        contentValues.put(TodoListDBContract.Tasks.DESCRIPTION, description);
        contentValues.put(TodoListDBContract.Tasks.PRIORITY, priority);
        contentValues.put(TodoListDBContract.Tasks.STATUS, status);

        return contentValues;
    }

    /**
     * Get all data from Tasks table
     */
    public ArrayList<Task> getTasks(String selectionFilter) {
        ArrayList<Task> taskList = new ArrayList<>();

        // Open database to read
        SQLiteDatabase sqLiteDatabase = todoListDBHelper.getReadableDatabase(TodoListDBContract.DB_PASSWD);

        if (sqLiteDatabase != null) {
            String[] projection = new String[] {
                    TodoListDBContract.Tasks._ID,
                    TodoListDBContract.Tasks.TODO,
                    TodoListDBContract.Tasks.TO_ACCOMPLISH,
                    TodoListDBContract.Tasks.DESCRIPTION,
                    TodoListDBContract.Tasks.PRIORITY,
                    TodoListDBContract.Tasks.STATUS
            };

            Cursor cursorTodoList = sqLiteDatabase.query(
                    TodoListDBContract.Tasks.TABLE_NAME,
                    projection,          // The columns toView return
                    selectionFilter,     // WHERE clause
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

                // TODO 6.1 Add two new fields
                int priorityIndex = cursorTodoList.getColumnIndex(TodoListDBContract.Tasks.PRIORITY);
                int statusIndex = cursorTodoList.getColumnIndex(TodoListDBContract.Tasks.STATUS);

                // Read data and add to ArrayList
                while (cursorTodoList.moveToNext()) {
                    Task task = new Task(
                            cursorTodoList.getInt(_idIndex),
                            cursorTodoList.getString(todoIndex),
                            cursorTodoList.getString(to_AccomplishIndex),
                            cursorTodoList.getInt(priorityIndex),
                            cursorTodoList.getInt(statusIndex),
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
