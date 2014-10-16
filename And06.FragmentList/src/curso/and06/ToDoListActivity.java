package curso.and06;

import java.util.ArrayList;

import curso.and06.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class ToDoListActivity extends Activity implements NewItemFragment.OnNewItemAddedListener {
  
  private ArrayList<ToDoItem> todoItems;
  private ToDoItemAdapter aa;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
      
    FragmentManager fm = getFragmentManager();
    ToDoListFragment tdListFragment = (ToDoListFragment)fm.findFragmentById(R.id.TodoListFragment);
    
    // Instaciamos el Array
    todoItems = new ArrayList<ToDoItem>();
    
    
    aa = new ToDoItemAdapter(this, R.layout.todolist_item, todoItems);
    tdListFragment.setListAdapter(aa);
  }
  
  // Callback esperado por la impentaci—n de la interfaz OnNewItemAddedListener
  public void onNewItemAdded(String newItem) {
    ToDoItem td = new ToDoItem(newItem);
    todoItems.add(0, td);
    aa.notifyDataSetChanged();
  }

}