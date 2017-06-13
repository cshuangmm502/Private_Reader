package com.example_imagine.terryblade.privatereader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Login extends AppCompatActivity {

    private Handler mHandler=null;
    private List<UserList>userLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.AddActivity(this);
        setContentView(R.layout.activity_login);

        final AutoCompleteTextView autoCompleteTextView_user=(AutoCompleteTextView)findViewById(R.id.user_id);      //账号输入框
        final EditText editText_password=(EditText)findViewById(R.id.password_id);                                  //密码输入框
        final Button button_login=(Button)findViewById(R.id.login_button);                                          //登录按键
        final TextView textView_regist=(TextView)findViewById(R.id.regist_textview);                                //注册文字
        sendUserlistRequestWithHttpClient();
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                userLists=(List<UserList>)msg.obj;
                Toast.makeText(Login.this,userLists.get(0).getUserId(),Toast.LENGTH_SHORT);
                autoCompleteTextView_user.setText(userLists.get(0).getUserId());
            }
        };
//        textView_regist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(Login.this,UserSetting.class);
//                startActivity(intent);
//            }
//        });
//
//        button_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String saveduser_string;
//                String savedpassword_string;
//                String user_string=autoCompleteTextView_user.getText().toString();
//                String password_string=editText_password.getText().toString();
//                SharedPreferences GetDateShardePreferences=getSharedPreferences("userlist",MODE_PRIVATE);
//                saveduser_string=GetDateShardePreferences.getString("USER","####");
//                savedpassword_string=GetDateShardePreferences.getString("PASSWORD","####");
//                if(saveduser_string.equals(user_string)&&savedpassword_string.equals(password_string)){
// //                   Intent intent=new Intent(Login.this,Main.class);
//                    Intent intent=new Intent(Login.this,MainLayout.class);
//                    startActivity(intent);
//                }else{
//                    Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_string=autoCompleteTextView_user.getText().toString();
                String password_string=editText_password.getText().toString();
                int userToken=0;
                int passwordToken=0;
                for(UserList user:userLists){
                    if(user.getUserId().equals(user_string)){
                        userToken=1;
                        if(user.getPassword().equals(password_string)){
                            passwordToken=1;
                        }
                    }
                }
                if(userToken==1&&passwordToken==1){
                    Intent intent=new Intent(Login.this,MainLayout.class);
                    startActivity(intent);
                    finish();
                    TastyToast.makeText(Login.this,"欢迎.",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                }else if(userToken==1&&passwordToken==0){
                    TastyToast.makeText(Login.this,"很抱歉，密码输错了哦...",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                }else{
                    TastyToast.makeText(Login.this,"很抱歉，不存在此用户哦...",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                }
            }
        });
    }


    private void sendUserlistRequestWithHttpClient(){
        new Thread(){
            @Override
            public void run(){
                HttpURLConnection connection =null;
                try{
                    String str="http://47.93.13.87:80/userlist/getUserJSON.php";
                    URL url=new URL(str);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream in=connection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder result=new StringBuilder();
                    String line;
                    while((line=bufferedReader.readLine())!=null){
                        result.append(line);
                    }
                    parseJSONWithGSON(result.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }.start();
    }

    private void parseJSONWithGSON(String jsondata){
        Gson gson=new Gson();
        List<UserList> userList=gson.fromJson(jsondata,new TypeToken<List<UserList>>(){}.getType());
        Message message=Message.obtain();
        message.obj=userList;
        mHandler.sendMessage(message);
    }

    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.RemoveActivity(this);
    }
}
