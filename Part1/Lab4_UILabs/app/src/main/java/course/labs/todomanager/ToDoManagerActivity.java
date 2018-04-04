package course.labs.todomanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import course.labs.todomanager.ToDoItem.Priority;
import course.labs.todomanager.ToDoItem.Status;

public class ToDoManagerActivity extends ListActivity {

	private static final int ADD_TODO_ITEM_REQUEST = 0;
	private static final String FILE_NAME = "TodoManagerActivityData.txt";
	private static final String TAG = "Lab-UserInterface";

	// IDs for menu items
	private static final int MENU_DELETE = Menu.FIRST;
	private static final int MENU_DUMP = Menu.FIRST + 1;

	ToDoListAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ToDoItem tdi=null;
		Priority priorityTmp=null;
		Status statusTmp=null;
		Date dateTmp=null;
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			String title = bundle.getString("title");
			String priority = bundle.getString("priority");
			if(priority.equals("LOW")){
				priorityTmp =Priority.LOW;
			} else if(priority.equals("MED")){
				priorityTmp =Priority.MED;
			} else if(priority.equals("HIGH")){
				priorityTmp =Priority.MED;
			}
			String status = bundle.getString("status");
			if(status.equals("NOTDONE")){
				statusTmp =Status.NOTDONE;
			} else if(priority.equals("DONE")) {
				statusTmp = Status.DONE;
			}
			String date = bundle.getString("date");
			try {
				dateTmp = ToDoItem.FORMAT.parse(date);
			}catch (ParseException pe){
				dateTmp=new Date();
			}
			tdi = new ToDoItem(title,priorityTmp,statusTmp,dateTmp);
		}
		// Create a new TodoListAdapter for this ListActivity's ListView
		mAdapter = new ToDoListAdapter(getApplicationContext());

		// Put divider between ToDoItems and FooterView
		 getListView().setFooterDividersEnabled(true);


		// TODO - Inflate footerView for footer_view.xml file
		TextView footerView = null;
		footerView = (TextView)getLayoutInflater().inflate(R.layout.footer_view,null);

		// NOTE: You can remove this block once you've implemented the assignment
		if (null == footerView) {
			return;
		}
		// TODO - Add footerView to ListView
		ListView listView = getListView();
		//listView.setAdapter(mAdapter);
		listView.addFooterView(footerView);

		//mAdapter.add(tdi);
		/*if(tdi!=null){
			//mAdapter.add(tdi);
			Log.d("TODOITEM DEBUG: ", tdi.toString());
			//listView.add(toDoItem.toString());
		}*/
		
        
        
		// TODO - Attach Listener to FooterView
		footerView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {


				//TODO - Implement OnClick().
				Intent intent = new Intent(getApplicationContext(),AddToDoActivity.class);
				startActivity(intent);
			}
		});

		// TODO - Attach the adapter to this ListActivity's ListView

		listView.setAdapter(mAdapter);
		if(tdi!=null){
			//mAdapter.add(tdi);
			Log.d("TODOITEM DEBUG: ", tdi.toString());
			//listView.add(toDoItem.toString());
		}
		//Log.d("DEBUG", "DEBUG");
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode,resultCode,data);

		Log.d(TAG,"Entered onActivityResult()");

		// TODO - Check result code and request code
		// if user submitted a new ToDoItem
		// Create a new ToDoItem from the data Intent
		// and then add it to the adapter
		ToDoItem toDoItem = new ToDoItem(data);
		Log.d("ToDOItemData: ", toDoItem.toString());

		Log.d("requestCode: ", Integer.toString(requestCode));
		Log.d("resultCode: ", Integer.toString(resultCode));
		if(requestCode== ADD_TODO_ITEM_REQUEST&& resultCode==RESULT_OK){
			ToDoItem item = new ToDoItem(data);
			String title = item.getTitle();
			Priority priority = item.getPriority();
			Status status = item.getStatus();
			Date date = item.getDate();
			mAdapter.add(item);
			mAdapter.notifyDataSetChanged();
		}


		/*Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			String title = bundle.getString("title");
			Log.d("TItle: sdf;jgknsdfklgn", title);
		}
		String input = data.getPackage();//data.getStringExtra("Data");
		//data.getPackage();

		Log.d("Data Intent:", data.getPackage());//toString());*/

		/*intent.putExtra(ToDoItem.TITLE, title);
		intent.putExtra(ToDoItem.PRIORITY, priority.toString());
		intent.putExtra(ToDoItem.STATUS, status.toString());
		intent.putExtra(ToDoItem.DATE, date);
		*/

	}

	// Do not modify below here

	@Override
	public void onResume() {
		super.onResume();

		// Load saved ToDoItems, if necessary

		if (mAdapter.getCount() == 0) {
			Log.d("DEBUG", "DEBUG");
			loadItems();
		}
		Log.d("Data: ", Integer.toString(mAdapter.getCount()));
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Save ToDoItems

		saveItems();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
		menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_DELETE:
			mAdapter.clear();
			return true;
		case MENU_DUMP:
			dump();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void dump() {

		for (int i = 0; i < mAdapter.getCount(); i++) {
			String data = ((ToDoItem) mAdapter.getItem(i)).toLog();
			Log.i(TAG,	"Item " + i + ": " + data.replace(ToDoItem.ITEM_SEP, ","));
		}

	}

	// Load stored ToDoItems
	private void loadItems() {
		Log.d("loadItems: ", "DEBUG");
		BufferedReader reader = null;
		try {
			FileInputStream fis = openFileInput(FILE_NAME);
			reader = new BufferedReader(new InputStreamReader(fis));

			String title = null;
			String priority = null;
			String status = null;
			Date date = null;

			while (null != (title = reader.readLine())) {
				priority = reader.readLine();
				status = reader.readLine();
				date = ToDoItem.FORMAT.parse(reader.readLine());
				mAdapter.add(new ToDoItem(title, Priority.valueOf(priority),
						Status.valueOf(status), date));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Save ToDoItems to file
	private void saveItems() {
		Log.d("saveItems: ", "DEBUG");
		PrintWriter writer = null;
		try {
			FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					fos)));

			for (int idx = 0; idx < mAdapter.getCount(); idx++) {

				writer.println(mAdapter.getItem(idx));

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				writer.close();
			}
		}
	}
}