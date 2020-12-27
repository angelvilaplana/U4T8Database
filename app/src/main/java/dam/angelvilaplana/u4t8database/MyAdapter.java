package dam.angelvilaplana.u4t8database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import dam.angelvilaplana.u4t8database.data.TodoListDBManager;
import dam.angelvilaplana.u4t8database.model.Task;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    // TODO 6.2 - OnClick Item RecyclerView
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    private TodoListDBManager todoListDBManager;

    private ArrayList<Task> myTaskList;

    /**
     * Class for each item
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout itemRecyclerView;
        TextView tvId;
        TextView tvTodo;
        TextView tvToAccomplish;
        TextView tvDescription;
        TextView tvPriorityValue;
        TextView tvStatus;

        public MyViewHolder(View view) {
            super(view);

            this.itemRecyclerView = view.findViewById(R.id.itemRecyclerView);
            this.tvId = view.findViewById(R.id.tvId);
            this.tvTodo = view.findViewById(R.id.tvTodo);
            this.tvToAccomplish = view.findViewById(R.id.tvToAccomplish);
            this.tvDescription = view.findViewById(R.id.tvDescription);
            this.tvPriorityValue = view.findViewById(R.id.tvPriorityValue);
            this.tvStatus = view.findViewById(R.id.tvStatus);
        }

        /**
         * Sets viewHolder views with data
         * TODO 6.1 - Set text new fields
         */
        public void bind(final Task task, final OnItemClickListener listener) {
            this.tvId.setText(String.valueOf(task.getId()));
            this.tvTodo.setText(task.getToDo());
            this.tvToAccomplish.setText(task.getToAccomplish());
            this.tvDescription.setText(task.getDescription());
            this.tvPriorityValue.setText(task.getPriority());
            this.tvStatus.setText(task.getStatus());

            // TODO 6.2 - OnClick item RecyclerView
            this.itemRecyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(task);
                }
            });
        }
    }

    /**
     * Constructor: todoListDBManager gets DB data
     */
    public MyAdapter(TodoListDBManager todoListDBManager, OnItemClickListener listener) {
        this.todoListDBManager = todoListDBManager;
        this.listener = listener;
    }

    /**
     * Get data from DB
     */
    public void getData(String selectionFilter) {
        myTaskList = todoListDBManager.getTasks(selectionFilter);
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
        viewHolder.bind(myTaskList.get(position), listener);
    }

    /**
     * Returns the size of dataSet: Layout Manager calls this method
     */
    @Override
    public int getItemCount() {
        return myTaskList.size();
    }

}
