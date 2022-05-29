package com.example.reminder.activity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reminder.database.DBManager;
import com.example.reminder.R;
import com.example.reminder.database.AdapterItems;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    DBManager dbManager;
    //    EditText etTitle;
//    EditText etDesc;
    long RecordID;
    String RecordTitle;
    String RecordDesc;
    String RecordDateRem;
    String RecordTimeRem;
    String RecordPriorityRem;
//    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //进入软件界面欢迎信息
        Toast.makeText(getApplicationContext(),"Welcome to ReMinder!", Toast.LENGTH_SHORT).show();
        dbManager=new DBManager(this);
//        etTitle=(EditText)findViewById(R.id.et1);
//        etDesc=(EditText)findViewById(R.id.et2);
        //获取数据库中的信息
        getdatabaseinfo(1,"ignore");
        createNotificationChannel();
    }

    //界面顶部嵌套menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        SearchView sv=(SearchView)menu.findItem(R.id.menu_search).getActionView();
        //获取搜索权限
        SearchManager sm=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        sv.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //更新信息
                getdatabaseinfo(2,query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //更新信息
                getdatabaseinfo(2,newText);

                return true;
            }
        });

        return true;
    }

    //使用说明
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_help:
                AlertDialog.Builder info=new AlertDialog.Builder(this);
                //软件信息和使用说明
                info.setMessage("Project started on 10-05-2022\nby MMOSTER AND AKAXIAOCHANG\n\n\n\nADD, DELETE, UPDATE and SEARCH Notes along with setting ReMinder at any date and time.")
                        .setTitle("Information")
                        //点击跳转web端查看源代码
                        .setPositiveButton("ABOUT US", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uri = Uri.parse("http://mmonsterr.top/");
                                Intent it = new Intent(Intent.ACTION_VIEW,uri);
                                startActivity(it);
                            }
                        })
                        .show();

                return true;
            case R.id.menu_settings:
                Uri uri = Uri.parse("https://github.com/mmonsterr/reminder");
                Intent it = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(it);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //
    private void createNotificationChannel() {
        String CHANNEL_ID="ReminderID";
        // 判断API是否在26及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            //创建信息队列
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            channel.setLightColor(Color.RED);
            channel.setVibrationPattern(new long[]{0});
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    ArrayList<AdapterItems> listnewsData = new ArrayList<AdapterItems>();
    MyCustomAdapter myadapter;



    //获取数据库中的信息
    @SuppressLint("Range")
    public void getdatabaseinfo(int count1, String to_search) {


        //add data and view it
        listnewsData.clear();
        //String[] projection={"","",""};
        Cursor cursor;
        if (count1 == 1) {
            cursor = dbManager.query(null, null, null, DBManager.ColID);
        } else {
            String[] SelectionsArgs = {"%" + to_search + "%", "%" + to_search + "%"};
            cursor = dbManager.query(null, "Title like ? or Description like ?", SelectionsArgs, DBManager.ColID);
        }
        //如果想要选择所有的数据，然后在选择中给null
        if (cursor.moveToFirst()) {
//            String tableData="";
            do {

                listnewsData.add(new AdapterItems(cursor.getLong(cursor.getColumnIndex(DBManager.ColID)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColDateTime)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColTitle)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColDescription)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColRemTime)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColRemDate)),
                        cursor.getString(cursor.getColumnIndex(DBManager.ColPri))));
            } while (cursor.moveToNext());
//            Toast.makeText(getApplicationContext(),tableData,Toast.LENGTH_SHORT).show();

        }

        myadapter = new MyCustomAdapter(listnewsData);


        final ListView lsNews = (ListView) findViewById(R.id.lv_all);
        lsNews.setAdapter(myadapter);//intisal with data
    }

    public void update_element_new(){
        //更新新提示
        String title_received=RecordTitle;
        String description_received= RecordDesc;
        String RecordID_string=String.valueOf(RecordID);
        String time_rem_received=RecordTimeRem;
        String date_rem_received=RecordDateRem;
        String priority_rem_received=RecordPriorityRem;
        Intent add_edit_act_intent=new Intent(getApplicationContext(), MainActivity2.class);
        add_edit_act_intent.putExtra("titlefrom", title_received);
        add_edit_act_intent.putExtra("descriptionfrom", description_received);
        add_edit_act_intent.putExtra("add_or_update", "UPDATE");
        add_edit_act_intent.putExtra("recordno", RecordID_string);
        add_edit_act_intent.putExtra("rem_time", time_rem_received);
        add_edit_act_intent.putExtra("rem_date", date_rem_received);
        add_edit_act_intent.putExtra("rem_pri", priority_rem_received);
        //用Activity Result API代替
        startActivityForResult(add_edit_act_intent, 4);
    }

    public void bu_add_edit_activity(View view) {
        //添加新的提示

        //统计总行数对应增加
        RecordID=dbManager.RowCount()+1;

        String RecordID_string=String.valueOf(RecordID);
        Intent add_edit_act_intent1=new Intent(getApplicationContext(), MainActivity2.class);
        add_edit_act_intent1.putExtra("titlefrom", "ignore");
        add_edit_act_intent1.putExtra("descriptionfrom", "ignore");
        add_edit_act_intent1.putExtra("add_or_update", "ADD");
        add_edit_act_intent1.putExtra("recordno", RecordID_string);
        add_edit_act_intent1.putExtra("rem_time", "ignore");
        add_edit_act_intent1.putExtra("rem_date", "ignore");
        add_edit_act_intent1.putExtra("rem_pri","1");
        startActivityForResult(add_edit_act_intent1, 3);//用Activity Result API代替
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //检查请求代码与这里传递的是否相同
        //获取对应的信息
        if(requestCode == 3)
        {
            getdatabaseinfo(1, "ignore");
            RecordID = 0;
        }
        else if(requestCode == 4){
            getdatabaseinfo(1, "ignore");
            RecordID = 0;
        }
    }


    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<AdapterItems>  listnewsDataAdpater) {
            this.listnewsDataAdpater = listnewsDataAdpater;
        }

        //设定note界面
        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        TextView txt_datetime;
        TextView txt_datetime_rem, txt_title, txt_desc, txt_pri;
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();

            View myView = mInflater.inflate(R.layout.layout_ticket, null);

            final AdapterItems s = listnewsDataAdpater.get(position);
            //合并时间日期

            String rem_DateTime = s.Time + " " + s.Date;

            try {
                Date format1 = new SimpleDateFormat("dd-mm-yyyy").parse(s.Date);
                String DataStr = new SimpleDateFormat("yyyy-mm-dd").format(format1);
                rem_DateTime = DataStr + " " +s.Time;

            } catch (ParseException e) {
                e.printStackTrace();
            }

            txt_datetime_rem = (TextView)myView.findViewById(R.id.date_time_id_rem);
            //未设定提醒时间日期
            if(s.Time.equalsIgnoreCase("notset")) {
                txt_datetime_rem.setVisibility(View.GONE);
            }
            //设定提醒时间日期
            else {


                txt_datetime_rem.setVisibility(View.VISIBLE);
                txt_datetime_rem.setText(rem_DateTime);
            }
            //设定title
            txt_title = (TextView)myView.findViewById(R.id.title_tv2);
            txt_title.setText(s.Title);

            txt_title.setSelected(true);
            //设定description
            txt_desc = (TextView)myView.findViewById(R.id.desc_tv2);
            txt_desc.setText(s.Description);

            txt_pri = myView.findViewById(R.id.priority);
            txt_pri.setBackgroundResource(R.color.red);
            txt_pri.setText(s.Priority);
            txt_pri.setTextColor(0xffFFFFFF);
            int pri = Integer.parseInt(s.Priority);
            if (pri == 3){
                txt_pri.setText("                        ");
                txt_pri.setBackgroundResource(R.color.red);
            }
            if (pri == 2){
                txt_pri.setText("                       ");
                txt_pri.setBackgroundResource(R.color.blue);
            }
            if (pri == 1){
                txt_pri.setText("                       ");
                txt_pri.setBackgroundResource(R.color.green);
            }



            //设置监听器
            txt_desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecordID = s.ID;
                    RecordTitle = s.Title;
                    RecordDesc = s.Description;
                    RecordDateRem = s.Date;
                    RecordTimeRem = s.Time;
                    RecordPriorityRem = s.Priority;
                    update_element_new();
                }
            });

            return myView;
        }

    }

}
