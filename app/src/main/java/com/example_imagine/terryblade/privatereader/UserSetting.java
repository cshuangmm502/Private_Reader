package com.example_imagine.terryblade.privatereader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Terryblade on 2017/4/10.
 */
public class UserSetting extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityCollector.AddActivity(this);
        setContentView(R.layout.activity_usersetting);
        final EditText editText_UserSetting=(EditText)findViewById(R.id.usersetting_edittext);
        final EditText editText_PassWord=(EditText)findViewById(R.id.passwordsetting_edittext);
        final Button button_UserSetting=(Button)findViewById(R.id.usersetting_button);

        button_UserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String saveduser_string;
                String savedpaseeword_string;
                String user_string=editText_UserSetting.getText().toString();
                String password_string=editText_PassWord.getText().toString();
                SharedPreferences GetDataSharedPreferences=getSharedPreferences("userlist",MODE_PRIVATE);
                saveduser_string=GetDataSharedPreferences.getString("USER","######");
                savedpaseeword_string=GetDataSharedPreferences.getString("PASSWORD","######");
                if(user_string.isEmpty()||password_string.isEmpty()){
                    Toast.makeText(UserSetting.this,"Invalid Input",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(saveduser_string=="######"&&savedpaseeword_string=="######"){
                        SharedPreferences.Editor editor=getSharedPreferences("userlist",MODE_PRIVATE).edit();
                        editor.putString("USER",user_string);
                        editor.putString("PASSWORD",password_string);
                        editor.commit();
                        Toast.makeText(UserSetting.this,"Setting Successful",Toast.LENGTH_SHORT).show();
                    }else if(!saveduser_string.equals(user_string)){
                        Toast.makeText(UserSetting.this,"UserId Input Wrong!",Toast.LENGTH_SHORT).show();
                    }else if(saveduser_string.equals(saveduser_string)){
                        SharedPreferences.Editor editor=getSharedPreferences("userlist",MODE_PRIVATE).edit();
                        editor.putString("USER",user_string);
                        editor.putString("PASSWORD",password_string);
                        editor.commit();
                        Toast.makeText(UserSetting.this,"Update Successful",Toast.LENGTH_SHORT).show();
                    }
                }
               // SharedPreferences.Editor editor=getSharedPreferences("userid",Activity.MODE_APPEND).edit();
            }
        });
    }

    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.RemoveActivity(this);
    }
}
