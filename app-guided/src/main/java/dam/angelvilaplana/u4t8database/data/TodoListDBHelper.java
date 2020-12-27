package dam.angelvilaplana.u4t8database.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Create the database (tables, indexes, initial data, etc.)
 * the first time it is accessed by following
 *
 * Alter the DB schema when there is a change in its structure (version)
 */
public class TodoListDBHelper extends SQLiteOpenHelper {

    // Instance to SQLiteOpenHelper
    private static TodoListDBHelper instanceDBHelper;

    /**
     * This method assures only one instance of TodoListDBHelper for all the application.
     * https://www.androiddesignpatterns.com/2012/05/correctly-managing-your-sqlite-database.html
     * Use the application context, to not leak Activity context
     */
    public static synchronized TodoListDBHelper getInstance(Context context) {
        // Instance must be unique
        if (instanceDBHelper == null) {
            instanceDBHelper = new TodoListDBHelper(context.getApplicationContext());
        }
        return instanceDBHelper;
    }

    /**
     * Constructor should be private to prevent direct instantiation
     */
    public TodoListDBHelper(Context context) {
        super(context, TodoListDBContract.DB_NAME, null, TodoListDBContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sql) {
        sql.execSQL(TodoListDBContract.Tasks.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sql, int oldVersion, int newVersion) {
        // The upgrade policy is simply discard the table and start over
        sql.execSQL(TodoListDBContract.Tasks.DELETE_TABLE);
        // Create again the DB
        onCreate(sql);
    }

}
