package com.example.apartment_finder2.ChatAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apartment_finder2.ChatMessage;
import com.example.apartment_finder2.R;

import java.util.List;

public class MessageChatAdapter {

    /*private List<ChatMessage> mChatList;
    public static final int SENDER = 0;
    public static final int RECIPIENT = 1;

    public MessageChatAdapter(List<ChatMessage> listOfFireChats){
        mChatList = listOfFireChats;
    }

    @Override
    public int getItemViewType(int position) {
        if (mChatList.get(position).getmRecipientOrSenderStatus()==SENDER){
            return SENDER;
        }else {
            return RECIPIENT;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case SENDER:
                View viewSender = inflater.inflate(R.layout.layout_sender_message, parent, false);
                viewHolder = new ViewHolderSender(viewSender);
                break;
            case RECIPIENT:
                View viewRecipient = inflater.inflate(R.layout.layout_reciepient_message, parent, false);
                viewHolder = new ViewHolderRecipient(viewRecipient);
                break;
            default:
                View viewSenderDefault = inflater.inflate(R.layout.layout_sender_message,parent,false);
                viewHolder = new ViewHolderSender(viewSenderDefault);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case SENDER:
                ViewHolderSender viewHolderSender = (ViewHolderSender)holder;
                configureSenderView(viewHolderSender,position);
                break;
            case RECIPIENT:
                ViewHolderRecipient viewHolderRecipient = (ViewHolderRecipient)holder;
                configureRecipentView(viewHolderRecipient,position);
                break;
        }
    }

    private void configureSenderView(ViewHolderSender viewHolderSender, int position){
        ChatMessage senderFireMessage = mChatList.get(position);
        viewHolderSender.getmSenderMessageTextView().setText(senderFireMessage.getMessage());
    }

    private void configureRecipentView(ViewHolderRecipient viewHolderRecipient, int position){
        ChatMessage senderFireMessage = mChatList.get(position);
        viewHolderRecipient.getmRecipientMessageTextView().setText(senderFireMessage.getMessage());
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    public void refillAdapter(ChatMessage newFireChatMessage){
        mChatList.add(newFireChatMessage);
        notifyItemInserted(getItemCount()-1);
    }
    public void cleanUp(){
        mChatList.clear();
    }

    public class ViewHolderSender extends RecyclerView.ViewHolder{
        private TextView mSenderMessage;
        public ViewHolderSender(View itemView) {
            super(itemView);
            mSenderMessage = (TextView)itemView.findViewById(R.id.text_view_sender_message);
        }
        public TextView getmSenderMessageTextView(){return mSenderMessage;}
    }
    public class ViewHolderRecipient extends RecyclerView.ViewHolder{
        private TextView mRecipientMessage;
        public ViewHolderRecipient(View itemView) {
            super(itemView);
            mRecipientMessage = (TextView)itemView.findViewById(R.id.text_view_recipient_message);

        }
        public TextView getmRecipientMessageTextView(){return mRecipientMessage;}
    }*/

}
