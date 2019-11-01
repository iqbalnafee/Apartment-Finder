package com.example.apartment_finder2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apartment_finder2.Model.ChatMessage2;
import com.example.apartment_finder2.R;

import java.util.List;

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage2> {

    private  static final int MY_MESSAGE=0;
    private  static final int BOT_MESSAGE=1;

    public ChatMessageAdapter(@NonNull Context context, List<ChatMessage2> data) {
        super(context, R.layout.user_query_layout,data);
    }

    @Override
    public int getItemViewType(int position) {

        ChatMessage2 item=getItem(position);

        if(item.isMine() && !item.isImage())
        {
            return MY_MESSAGE;
        }
        else
        {
            return BOT_MESSAGE;
        }

    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        int viewType=getItemViewType(position);
        if(viewType==MY_MESSAGE)
        {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.user_query_layout,parent,false);
            TextView textView=convertView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());
        }

        else if(viewType==BOT_MESSAGE)
        {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.bots_reply_layout,parent,false);
            TextView textView=convertView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());
        }

        convertView.findViewById(R.id.chatMessageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"clicked",Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
