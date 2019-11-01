package com.example.apartment_finder2;

import android.content.res.AssetManager;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apartment_finder2.Adapter.ChatMessageAdapter;
import com.example.apartment_finder2.Model.ChatMessage2;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ChatBot extends AppCompatActivity {

    ListView listView;
    FloatingActionButton btnSend;
    EditText editText;
    ImageView imageView;

    private Bot bot;
    private  static Chat chat;
    private ChatMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        listView=findViewById(R.id.ListView);
        btnSend=findViewById(R.id.btnSend);
        editText=findViewById(R.id.editTextMsg);
        imageView=findViewById(R.id.iView);

        adapter=new ChatMessageAdapter(this,new ArrayList<ChatMessage2>());
        listView.setAdapter(adapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=editText.getText().toString();
                String response=chat.multisentenceRespond(editText.getText().toString());
                if(TextUtils.isEmpty(message))
                {
                    Toast.makeText(ChatBot.this,"Please enter a query",Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMessage(message);
                botReply(response);
                editText.setText("");
                listView.setSelection(adapter.getCount()-1);
            }
        });
        boolean available=isSDcardavailable();
        AssetManager assetManager=getResources().getAssets();
        File file = new File(Environment.getExternalStorageDirectory().toString()+"/apartmentfinder/bots/apartmentfinder");
        boolean makefile=file.mkdirs();

        if(file.exists())
        {
            try {

                for(String dir:assetManager.list("apartmentfinder"))
                {
                    File subDir = new File(file.getPath()+"/"+dir);
                    boolean subdir_Check=subDir.mkdirs();

                    for(String fle:assetManager.list("apartmentfinder/"+dir))
                    {
                        File NewFile=new File(file.getPath()+"/"+dir+"/"+fle);
                        if(NewFile.exists())
                        {
                            continue;
                        }
                        InputStream in;
                        OutputStream out;
                        String str;
                        in=assetManager.open("apartmentfinder/"+dir+"/"+fle);
                        out=new FileOutputStream(file.getPath()+"/"+dir+"/"+fle);
                        copyFile(in,out);
                        in.close();
                        out.flush();
                        out.close();
                    }
                }

            }
            catch (Exception e)
            {

            }
        }
        MagicStrings.root_path=Environment.getExternalStorageDirectory().toString()+"/apartmentfinder";
        AIMLProcessor.extension=new PCAIMLProcessorExtension();
        bot=new Bot("apartmentfinder",MagicStrings.root_path,"chat");
        chat=new Chat(bot);
    }

    public void copyFile(InputStream in, OutputStream out) throws IOException {

        byte[] buffer=new byte[1024];
        int read;
        while((read=in.read(buffer)) != -1)
        {
            out.write(buffer,0,read);
        }
    }

    public static boolean isSDcardavailable() {

        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)? true : false;
    }

    public void botReply(String response) {
        ChatMessage2 chatMessage2=new ChatMessage2(false,false,response);
        adapter.add(chatMessage2);
    }

    public void sendMessage(String msg)
    {
        ChatMessage2 chatMessage2=new ChatMessage2(false,true,msg);
        adapter.add(chatMessage2);
    }
}
