package com.example.apartment_finder2.ChatHelper;

import android.app.AlertDialog;
import android.content.Context;

import com.example.apartment_finder2.R;

import java.util.Random;

public class ChatHelpers {
    private static Random randomAvatarGenerate = new Random();
    private static final int num_of_avatar = 4;

    public static int generateRandomAvatar(){
        return randomAvatarGenerate.nextInt(num_of_avatar);
    }

    public static int getDrawableAvatar(int givenRandomAvatar){
        switch (givenRandomAvatar){
            case 0:
                return R.mipmap.user_avatar_1;
            case 1:
                return R.mipmap.user_avatar_2;
            case 2:
                return R.mipmap.user_avatar_3;
            default:
                return R.mipmap.user_avatar_1;
        }
    }

    public static AlertDialog builderAlertDialog (String title, String message, boolean isCancelable, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle(title);

        if (isCancelable){
            builder.setPositiveButton(android.R.string.ok,null);
        }else {
            builder.setCancelable(false);
        }
        return builder.create();
    }

}
