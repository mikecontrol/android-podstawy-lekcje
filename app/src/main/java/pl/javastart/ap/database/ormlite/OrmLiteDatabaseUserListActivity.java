package pl.javastart.ap.database.ormlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.javastart.ap.R;
import pl.javastart.ap.database.ormlite.model.OrmLiteUserRepository;
import pl.javastart.ap.database.ormlite.model.User;

public class OrmLiteDatabaseUserListActivity extends Activity {

    private Button addUserButton;
    private EditText nameEditText;
    private EditText surnameEditText;
    private ListView userListView;
    private ManualUserAdapter userListAdapter;

    private List<User> userList = new ArrayList<>();

    private OrmLiteDatabaseHelper databaseHelper;

    public OrmLiteDatabaseUserListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_user_list);
        databaseHelper = OrmLiteDatabaseHelper.getInstance(getApplicationContext());


        nameEditText = (EditText) findViewById(R.id.name);
        surnameEditText = (EditText) findViewById(R.id.surname);
        addUserButton = (Button) findViewById(R.id.add_new_user_button);
        userListView = (ListView) findViewById(R.id.book_list);

        addActionForUserAddButton(addUserButton);

        userListAdapter = new ManualUserAdapter();
        updateUserList();
        userListView.setAdapter(userListAdapter);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OrmLiteDatabaseUserListActivity.this, OrmLiteDatabaseUserActivity.class);
                intent.putExtra(OrmLiteDatabaseUserActivity.PARAM_USER_ID, id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserList();
    }

    private void addActionForUserAddButton(Button addUserButton) {
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText.getText().toString().isEmpty() || surnameEditText.getText().toString().isEmpty()) {
                    Toast.makeText(OrmLiteDatabaseUserListActivity.this, "Uzupełnij obydwa pola!", Toast.LENGTH_SHORT).show();
                } else {
                    addUser();
                    nameEditText.setText("");
                    surnameEditText.setText("");
                }
            }
        });
    }

    private void addUser() {
        User user = new User();
        user.setName(nameEditText.getText().toString());
        user.setSurname(surnameEditText.getText().toString());
        OrmLiteUserRepository.addUser(OrmLiteDatabaseUserListActivity.this, user);

        updateUserList();
    }

    private void updateUserList() {
        userList = OrmLiteUserRepository.findAll(this);
        userListAdapter.setUserList(userList);
        userListAdapter.notifyDataSetChanged();
    }

    public class ManualUserAdapter extends BaseAdapter {

        private List<User> userList;

        @Override
        public int getCount() {
            return userList.size();
        }

        @Override
        public User getItem(int position) {
            return userList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(OrmLiteDatabaseUserListActivity.this);
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            TextView text = (TextView) convertView.findViewById(android.R.id.text1);
            User user = getItem(position);
            text.setText(user.getId() + ". " + user.getName() + " " + user.getSurname());

            return convertView;
        }

        public void setUserList(List<User> userList) {
            this.userList = userList;
        }
    }

}
