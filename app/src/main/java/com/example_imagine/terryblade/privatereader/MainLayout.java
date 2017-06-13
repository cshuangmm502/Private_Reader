package com.example_imagine.terryblade.privatereader;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terryblade on 2017/4/20.
 */
public class MainLayout extends AppCompatActivity {
    //drawerlayout控件id
    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] mMenuTitles;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;        //状态图标
    //viewpager控件id
    private ViewPager viewPager;
    private ImageView imageView;
    private TextView textView1,textView2,textView3;
    private List<View> views;
    private int offset=0;               //动画图片偏移量
    private int currIndex=0;            //当前选中项
    private int bmpw;                   //动画图片宽度
    private View view1,view2,view3;

    private Handler mHandler = null;
    private List<News.NewslistBean> newslist,historylist;
    private List<ChildMenuPage> menuPageList=new ArrayList<>();
    private ListView listViewSocial,history,other;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        ActivityCollector.AddActivity(this);
        setContentView(R.layout.activity_main);

        historylist=new ArrayList<News.NewslistBean>();
        InitToolBar();
        InitSlidingMenu();
        InitImageView();
        InitTextView();
        InitViewPager();
;
        sendSocialNewsRequestWithHttpClient();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                newslist=(List<News.NewslistBean>)msg.obj;
                for(News.NewslistBean news:newslist){
                    news.setImageId(R.drawable.newspic);
                }
                NewsAdapter newsAdapter=new NewsAdapter(MainLayout.this,R.layout.activity_news_item,newslist);
                listViewSocial.setAdapter(newsAdapter);
                listViewSocial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        News.NewslistBean bean=newslist.get(i);
                        Intent intent=new Intent(MainLayout.this,NewsView.class);
                        String url=bean.getUrl();
                        intent.putExtra("url",url);
                        NewsAdapter newsAdapter=new NewsAdapter(MainLayout.this,R.layout.activity_news_item,historylist);
                        historylist.add(bean);
                        history.setAdapter(newsAdapter);
                        startActivity(intent);

                        Toast.makeText(MainLayout.this,url,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News.NewslistBean bean=historylist.get(i);
                Intent intent=new Intent(MainLayout.this,NewsView.class);
                String url=bean.getUrl();
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
    }

    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.RemoveActivity(this);
    }

    private void sendSocialNewsRequestWithHttpClient(){
        new Thread(){
            @Override
            public void run(){
                HttpURLConnection connection =null;
                try{
                    String str="https://api.tianapi.com/social/?key=45bb4371e105171e8626a96b4998fe62&num=10";
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
        News news=gson.fromJson(jsondata, News.class);
        List<News.NewslistBean> newslistBeanList=news.getNewslist();
        Message message=Message.obtain();
        message.obj=newslistBeanList;
        mHandler.sendMessage(message);
    }

//    private void InitHistory(){
//        NewsAdapter history=new NewsAdapter(MainLayout.this,R.layout.activity_news_item,historylist);
//        history=(ListView)findViewById()
//    }

    private void InitSlidingMenu(){
        listView=(ListView)findViewById(R.id.left_drawer);
        ChildMenuPage social=new ChildMenuPage("社会新闻",R.mipmap.social);
        menuPageList.add(social);
        ChildMenuPage sports=new ChildMenuPage("体育新闻",R.mipmap.sports);
        menuPageList.add(sports);
        ChileMenuPageAdapter chileMenuPageAdapter=new ChileMenuPageAdapter(MainLayout.this,R.layout.activity_childmenupage,menuPageList);
        listView.setAdapter(chileMenuPageAdapter);
       // listView.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mMenuTitles));
    }

    private void InitToolBar(){
        //drawerlayout控件findview
        mMenuTitles=getResources().getStringArray(R.array.menu_array);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);

        toolbar=(Toolbar)findViewById(R.id.toolbar);


        toolbar.setTitle("智阅");
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.mipmap.navigationicon);

        //toolbar 设置与返回监听
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle=new ActionBarDrawerToggle(MainLayout.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerview){
                super.onDrawerOpened(drawerview);
            }
            @Override
            public void onDrawerClosed(View drawerview){
                super.onDrawerClosed(drawerview);
            }
        };
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

    }


    private void InitViewPager(){
        //viewpager控件findview
        viewPager=(ViewPager)findViewById(R.id.vPager);
        views=new ArrayList<View>();
        LayoutInflater layoutInflater=getLayoutInflater();
        view1=layoutInflater.inflate(R.layout.activity_viewpager_view1,null);
        listViewSocial=(ListView)view1.findViewById(R.id.list_view_item1);
        view2=layoutInflater.inflate(R.layout.activity_viewpager_view2,null);
        history=(ListView)view2.findViewById(R.id.list_view_item2);
        //NewsAdapter newsAdapter=new NewsAdapter(MainLayout.this,R.layout.activity_news_item,historylist);
        //history.setAdapter(newsAdapter);
        //history.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.news_history)));
        view3=layoutInflater.inflate(R.layout.activity_viewpager_view3,null);
        other=(ListView)view3.findViewById(R.id.list_view_item3);
        other.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.news_others)));
        views.add(view1);
        views.add(view2);
        views.add(view3);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void InitTextView(){
        textView1=(TextView)findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);

        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));
        textView3.setOnClickListener(new MyOnClickListener(2));
    }

    private void InitImageView() {
        imageView= (ImageView) findViewById(R.id.cursor);
        bmpw = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpw) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
//动画
//    private void loadSlidingMenuAnimation(){
//        AnimationSet set=new AnimationSet(false);
//        Animation animation;
//        animation=new RotateAnimation(180,10);
//        animation.setDuration(1000);
//        LayoutAnimationController controller=new LayoutAnimationController(set,1);
//        controller.setInterpolator(this, android.R.anim.accelerate_interpolator);
//        controller.setAnimation(set);
//
//
//    }

    private class MyOnClickListener implements View.OnClickListener {
        private int index=0;
        public MyOnClickListener(int i){
            index=i;
        }
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }

    }

    class MyViewPagerAdapter extends PagerAdapter{
        private List<View> mListViews;
        public MyViewPagerAdapter(List<View> mListViews){
            this.mListViews=mListViews;
        }

        @Override
        public void destroyItem(ViewGroup contain,int position,Object object){
            contain.removeView(mListViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup contain,int position){
            contain.addView(mListViews.get(position),0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return  mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpw;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量
        public void onPageScrollStateChanged(int arg0) {


        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {


        }

        public void onPageSelected(int arg0) {
            Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);//显然这个比较简洁，只有一行代码。
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            imageView.startAnimation(animation);
            Toast.makeText(MainLayout.this, "您选择了"+ (viewPager.getCurrentItem()+1)+"页卡", Toast.LENGTH_SHORT).show();
        }

    }
}
