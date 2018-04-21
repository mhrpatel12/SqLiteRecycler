package com.sqlite.recycler.view;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.sqlite.recycler.R;
import com.sqlite.recycler.database.DatabaseHelper;
import com.sqlite.recycler.database.model.User;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextInputLayout layoutUserName;
    private TextInputLayout layoutUserDescription;
    private TextInputEditText edtUserName;
    private TextInputEditText edtUserDescription;
    private List<User> userList;
    private UserListAdapter adapter;
    private RecyclerView rv;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rv = (RecyclerView) findViewById(R.id.list_users);
        layoutUserName = (TextInputLayout) findViewById(R.id.layoutUserName);
        layoutUserDescription = (TextInputLayout) findViewById(R.id.layoutUserDescription);
        AppCompatButton btnCreateUser = (AppCompatButton) findViewById(R.id.btnCreateUser);
        edtUserName = (TextInputEditText) findViewById(R.id.edtUserName);
        edtUserDescription = (TextInputEditText) findViewById(R.id.edtUserDescription);
        db = new DatabaseHelper(this);
        userList = new ArrayList<>();
        if (db.getAllNotes().size() > 0) {
            userList = db.getAllNotes();
        }
        adapter = new UserListAdapter(this, userList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAndAddUser();
            }
        });
        edtUserDescription.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    createAndAddUser();
                    handled = true;
                    // Check if no view has focus:
                    View view = HomeActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                return handled;
            }
        });

    }

    private void createAndAddUser() {
        if (TextUtils.isEmpty(edtUserName.getText())) {
            layoutUserName.setError(getString(R.string.error_empty));
            return;
        }
        if (TextUtils.isEmpty(edtUserDescription.getText())) {
            layoutUserName.setError(null);
            layoutUserDescription.setError(getString(R.string.error_empty));
            return;
        }
        layoutUserName.setError(null);
        layoutUserDescription.setError(null);
        long id = db.insertUser(new User(edtUserName.getText() + "", edtUserDescription.getText() + ""));
        User n = db.getNote(id);
        if (n != null) {
            edtUserName.setText("");
            edtUserDescription.setText("");
            edtUserName.requestFocus();
            // adding new user to array list at 0 position
            userList.add(0, n);
            // refreshing the list
            adapter.notifyDataSetChanged();
            rv.smoothScrollToPosition(0);
        }
    }
}
