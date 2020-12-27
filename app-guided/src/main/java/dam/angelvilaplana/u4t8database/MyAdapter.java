package dam.angelvilaplana.u4t8database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dam.angelvilaplana.u4t8database.data.TodoListDBManager;
import dam.angelvilaplana.u4t8database.model.Task;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private TodoListDBManager todoListDBManager;

    private ArrayList<Task> myTaskList;

    /**
     * Class for each item
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        TextView tvTodo;
        TextView tvToAccomplish;
        TextView tvDescription;

        public MyViewHolder(View view) {
            super(view);

            this.tvId = view.findViewById(R.id.tvId);
            this.tvTodo = view.findViewById(R.id.tvTodo);
            this.tvToAccomplish = view.findViewById(R.id.tvToAccomplish);
            this.tvDescription = view.findViewById(R.id.tvDescription);
        }

        /**
         * Sets viewHolder views with data
         */
        public void bind(Task task) {
            this.tvId.setText(String.valueOf(task.getId()));
            this.tvTodo.setText(task.getToDo());
            this.tvToAccomplish.setText(task.getToAccomplish());
            this.tvDescription.setText(task.getDescription());
        }
    }

    /**
     * Constructor: todoListDBManager gets DB data
     */
    public MyAdapter(TodoListDBManager todoListDBManager) {
        this.todoListDBManager = todoListDBManager;
    }

    /**
     * Get data from DB
     */
    public void getData() {
        myTaskList = todoListDBManager.getTasks();
        notifyDataSetChanged();
    }

    /**
     * Create new view item: Layout Manager calls this method
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create item View
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);

        return new MyViewHolder(itemLayout);
    }

    /**
     * Replaces the data content of a viewholder (recycles old viewholder):
     * Layout Manager calls this method
     */
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder viewHolder, int position) {
        // Bind viewHolder with data at: position
        viewHolder.bind(myTaskList.get(position));
    }

    /**
     * Returns the size of dataSet: Layout Manager calls this method
     */
    @Override
    public int getItemCount() {
        return myTaskList.size();
    }

}
