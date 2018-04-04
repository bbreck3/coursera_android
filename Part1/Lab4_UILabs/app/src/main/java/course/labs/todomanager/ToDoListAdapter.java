package course.labs.todomanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ToDoListAdapter extends BaseAdapter {

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";

	public ToDoListAdapter(Context context) {

		mContext = context;

	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// Create a View for the ToDoItem at specified position
	// Remember to check whether convertView holds an already allocated View
	// before created a new View.
	// Consider using the ViewHolder pattern to make scrolling more efficient
	// See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO - Get the current ToDoItem
		//final ToDoItem toDoItem = null;
		ToDoItem toDoItem = null;
		toDoItem =(ToDoItem) getItem(position);
		String title =toDoItem.getTitle();
		ToDoItem.Priority priority = toDoItem.getPriority();
		ToDoItem.Status status = toDoItem.getStatus();
		Date date = toDoItem.getDate();
		//toDoItem = (ToDoItem)getView(position,convertView,parent);

		// TODO - Inflate the View for this ToDoItem
		// from todo_item.xml

		//Context context = convertView.getContext();
		//RelativeLayout relativeLayout = (RelativeLayout)convertView;
		//R.layout.todo_item;
		//ToDoItem toDoItem = new ToDoItem(mContext);
		//LayoutInflater inflater = getView()
		//mContext = convertView.getContext();
		LayoutInflater inflater = LayoutInflater.from(convertView.getContext());
		View view = inflater.inflate(R.layout.todo_item,parent);


		// Fill in specific ToDoItem data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined
		// in the layout file

		// TODO - Display Title in TextView
		final TextView titleView = (TextView)view.findViewById(R.id.titleView);//view.findViewById(position);//(TextView)parent.getChildAt(position)
		titleView.setText(title);
		/// // /titleView =(TextView)


		// TODO - Set up Status CheckBox
		final CheckBox statusView = (CheckBox)view.findViewById(R.id.statusCheckBox); //(CheckBox)convertView.getContext(mContext.getApplicationContext());

		// TODO - Must also set up an OnCheckedChangeListener,
		// which is called when the user toggles the status checkbox

		statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

							if(isChecked){
								buttonView.setChecked(isChecked);
							}

                        
                        
                        
					}
				});

		// TODO - Display Priority in a TextView
		final TextView priorityView = (TextView)view.findViewById(R.id.priorityView);
		priorityView.setText(priority.ordinal());
		//priorityView.setText(toDoItem.getPriority());



		// TODO - Display Time and Date.
		// Hint - use ToDoItem.FORMAT.format(toDoItem.getDate()) to get date and
		// time String
		final TextView dateView =(TextView)view.findViewById(R.id.dateView);
		dateView.setText(toDoItem.FORMAT.format(toDoItem.getDate()));

		// Return the View you just created
		return view;

	}
}
