package com.sqlite.recycler.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.sqlite.recycler.R;
import com.sqlite.recycler.database.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mihir on 21-04-2018.
 */

public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> listUser;
    private Context context;
    private SimpleDateFormat sdf;

    public UserListAdapter(Context context, List<User> listUser) {
        this.context = context;
        this.listUser = listUser;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_users, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User user = listUser.get(position); // UserList
        final UserVH userVH = (UserVH) holder;
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
        userVH.itemView.startAnimation(animation);
        userVH.txtUserName.setText(user.getUserName());
        userVH.txtUserDescription.setText(user.getUserDescription());
    }

    @Override
    public int getItemCount() {
        return listUser == null ? 0 : listUser.size();
    }

    protected class UserVH extends RecyclerView.ViewHolder {
        private TextView txtUserName;
        private TextView txtUserDescription;
        public UserVH(View itemView) {
            super(itemView);
            txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
            txtUserDescription = (TextView) itemView.findViewById(R.id.txtUserDescription);
        }
    }
}
