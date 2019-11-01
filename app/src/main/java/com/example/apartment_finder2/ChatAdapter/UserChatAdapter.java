package com.example.apartment_finder2.ChatAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apartment_finder2.ChatHelper.ChatHelpers;
import com.example.apartment_finder2.ChatHelper.ExtraIntent;
import com.example.apartment_finder2.R;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class UserChatAdapter {

    public static final String ONLINE = "online";
    public static final String OFFLINE = "offline";
    private List<User> mUser;
    private Context mContext;
    private String mCurrentUserEmail;
    private Long mCurrentUserCreatedAt;
    private String mCurrentUserId;



    /*public void onClick(View v) {
        User user = mUser.get(getLayoutPosition());
        String chatRef = user.createUniqueChatRef(mCurrentUserCreatedAt,mCurrentUserEmail);
        Intent chatIntent = new Intent(mContextViewHolder,ChatActivity.class);
        chatIntent.putExtra(ExtraIntent.EXTRA_CURRENT_USER_ID,mCurrentUserId);
        chatIntent.putExtra(ExtraIntent.EXTRA_RECIPIENT_ID,user.getRecipientId());
        chatIntent.putExtra(ExtraIntent.EXTRA_CHAT_REF,chatRef);
    }*/

}
