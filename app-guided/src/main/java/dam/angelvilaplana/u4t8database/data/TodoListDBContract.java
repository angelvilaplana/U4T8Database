package dam.angelvilaplana.u4t8database.data;

/**
 * Class that defines TodoListDB schema
 */
public class TodoListDBContract {

    // Common field to all DB

    // Database Name
    public static final String DB_NAME = "TODOLIST.DB";

    // Database Version
    public static final int DB_VERSION = 1;

    /**
     * To prevent someone from accidentally instantiating the contract class:
     * make the constructor private
     */
    private TodoListDBContract() {
    }

    // Schema

    /**
     * TABLE TASKS: Inner class that defines the tanle Tasks contents
     */
    public static class Tasks {
        // Table name
        public static final String TABLE_NAME = "TASKS";

        // Columns names
        public static final String _ID = "_id";
        public static final String TODO = "todo";
        public static final String TO_ACCOMPLISH = "to_accomplish";
        public static final String DESCRIPTION = "description";

        // CREATE_TABLE SQL String
        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TODO + " TEXT NOT NULL, " +
                TO_ACCOMPLISH + " TEXT, " +
                DESCRIPTION + " TEXT" +
                ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        // Other table definition would come here
    }

}
