package com.example.reminder.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reminder.receiver.MyReceiver;
import com.example.reminder.fragment.PopInfo;
import com.example.reminder.R;
import com.example.reminder.database.DBManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    private static final String CHANNEL_ID = "Reminder";
    DBManager dbManager;
    Button add_update, cancel;
    String add_this;
    //接收到的title
    String title_received;
    //接收到的description
    String desc_received;
    EditText title_value,desc_value;
    //接受到的ID
    String RecordID_received1;
    Integer RecordID_received;
    //接收到的时间日期
    String Priority_received;
    String Priority;

    //接收到的优先级
    TextView RemTime, RemDate;
    String RemDate_received, RemTime_received;
    public static String time_pass, date_pass;
    RadioGroup radioGroup;
    RadioButton rbHigh, rbMid, rbLow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dbManager = new DBManager(this);
        add_update = (Button)findViewById(R.id.add_update_button1);
        cancel = (Button)findViewById(R.id.cancel_button1);
        title_value = (EditText)findViewById(R.id.title_value);
        desc_value = (EditText)findViewById(R.id.desc_value);
        RemTime = (TextView)findViewById(R.id.rem_time1);
        RemDate = (TextView)findViewById(R.id.rem_date1);
        radioGroup = findViewById(R.id.radioGroup);
        rbHigh = findViewById(R.id.high);
        rbMid = findViewById(R.id.mid);
        rbLow = findViewById(R.id.low);
        RemTime.setVisibility(View.GONE);
        RemDate.setVisibility(View.GONE);

        RemTime.setText("ignore");
        RemDate.setText("ignore");

        Priority = "1";
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                View rb = radioGroup.findViewById(id);
                int flag = 2;
                flag = radioGroup.indexOfChild(rb);
                flag = 3 - flag;
                Priority = String.valueOf(flag);
            }
        });

        Bundle b1=getIntent().getExtras();
        title_received=b1.getString("titlefrom");
        desc_received=b1.getString("descriptionfrom");
        add_this = b1.getString("add_or_update");
        RecordID_received1 = b1.getString("recordno");
        RemTime_received = b1.getString("rem_time");
        RemDate_received = b1.getString("rem_date");
        Priority_received = b1.getString("rem_pri");
        RecordID_received = Integer.parseInt(RecordID_received1);
        //将时间倒序
//        try {
//            String str = RemDate_received.substring(6, 10) + RemDate_received.substring(2, 6) + RemDate_received.substring(0, 2);
//            RemDate_received = str;
//        } finally {
            if(add_this.equals("UPDATE")){
                title_value.setText(title_received);
                desc_value.setText(desc_received);
                radioGroup.clearCheck();
                if (Priority_received.equals("3"))
                    rbHigh.setChecked(true);
                if (Priority_received.equals("2"))
                    rbMid.setChecked(true);
                if (Priority_received.equals("1"))
                    rbLow.setChecked(true);
                if(!RemTime_received.equalsIgnoreCase("notset")) {
                    RemTime.setVisibility(View.VISIBLE);
                    RemDate.setVisibility(View.VISIBLE);
                    RemTime.setText(RemTime_received);
                    RemDate.setText(RemDate_received);
//                }
                };
            }

            RemTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    androidx.fragment.app.FragmentManager fm = getSupportFragmentManager();
                    PopInfo popInfo=new PopInfo();

                    Bundle bundle1 = new Bundle();
                    bundle1.putString("time_value", RemTime.getText().toString());
                    bundle1.putString("date_value", RemDate.getText().toString());

                    popInfo.setArguments(bundle1);

                    popInfo.show(fm,"Show Fragment");
                }
            });
        RemDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.fragment.app.FragmentManager fm = getSupportFragmentManager();
                PopInfo popInfo=new PopInfo();

                Bundle bundle1 = new Bundle();
                bundle1.putString("time_value", RemTime.getText().toString());
                bundle1.putString("date_value", RemDate.getText().toString());

                popInfo.setArguments(bundle1);

                popInfo.show(fm,"Show Fragment");
            }
        });
    }

    //界面顶部嵌套menu1
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);

        return true;
    }

    //使用说明
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_reminder:
                androidx.fragment.app.FragmentManager fm = getSupportFragmentManager();
                PopInfo popInfo=new PopInfo();

                Bundle bundle1 = new Bundle();
                bundle1.putString("time_value", RemTime.getText().toString());
                bundle1.putString("date_value", RemDate.getText().toString());

                popInfo.setArguments(bundle1);

                popInfo.show(fm,"Show Fragment");
//                Toast.makeText(getApplicationContext(),"Reminder is currently under development!",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_delete:
                if(add_this.equals("UPDATE")) {
                    AlertDialog.Builder info1=new AlertDialog.Builder(this);
                    //弹出删除警告，防止误删
                    info1.setMessage("Are you sure you want to delete this note?")
                            .setTitle("Warning")
                            .setNeutralButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    delete_element(RecordID_received1);
                                }
                            })
                            .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                }
                else {
                    //若还未完全添加完title和description，则弹出警告
                    Toast.makeText(getApplicationContext(),"Note is not added yet!", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //通过时钟设定时间日期
    public void SetAlarm(String TimeAlarm,String DateAlarm, String Title_Received,String Desc_Received){

        String[] time_arr = TimeAlarm.split(":", 2);
        String[] date_arr = DateAlarm.split("-", 3);
        for (String a : time_arr)
            System.out.println("Holathis1"+a);

        for (String b : date_arr)
            System.out.println("Holathis2"+b);

        int Hour = Integer.parseInt(time_arr[0]);
        int Minute = Integer.parseInt(time_arr[1]);
        int Year = Integer.parseInt(date_arr[2]);
        int Month = Integer.parseInt(date_arr[1])-1;
        int Day = Integer.parseInt(date_arr[0]);


        System.out.println("Setting alarm for "+RecordID_received+"at "+Hour+":"+Minute+" and "+Day+"-"+String.valueOf(Integer.valueOf(Month+1))+"-"+Year);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Year);
        calendar.set(Calendar.MONTH, Month);
        calendar.set(Calendar.DAY_OF_MONTH, Day);
        calendar.set(Calendar.HOUR_OF_DAY, Hour);
        calendar.set(Calendar.MINUTE, Minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        AlarmManager am = (AlarmManager)getSystemService  (Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyReceiver.class);
        intent.setAction("com.akaxiaochang.rem");
//        String msg1=context.getResources().getString(R.string.msg_notify);
        String msg1="Hello from Keep Notes";
        intent.putExtra("AlarmMessage",msg1);
        intent.putExtra("NotiID",RecordID_received1);
        intent.putExtra("Noti_Title",Title_Received);
        intent.putExtra("Noti_Desc",Desc_Received);
        intent.putExtra("Rem_Time",TimeAlarm);
        intent.putExtra("Rem_Date",DateAlarm);
        intent.putExtra("SetNotify","SetNotification");
        intent.putExtra("Noti_Pri", Priority_received);



        PendingIntent pi = PendingIntent.getBroadcast(this, RecordID_received, intent,0);

//        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY , pi);
        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
//        SetNotification();
    }
    //撤销时钟设定
    public void CancelAlarm(){
//        Toast.makeText(this,"Alarm canceled",Toast.LENGTH_SHORT).show();
        AlarmManager am = (AlarmManager)getSystemService (Context.ALARM_SERVICE);

        Intent intent = new Intent(this, MyReceiver.class);
        intent.setAction("com.akaxiaochang.rem");
        String msg1 = "Hello from Keep Notes";
        intent.putExtra("AlarmMessage",msg1);
        intent.putExtra("NotiID",RecordID_received1);
        intent.putExtra("Noti_Title","ignore");
        intent.putExtra("Noti_Desc","ignore");
        intent.putExtra("Rem_Time","ignore");
        intent.putExtra("Rem_Date","ignore");
        intent.putExtra("SetNotify","SetNotificationNot");
        intent.putExtra("Noti_Pri", Priority_received);

        PendingIntent pi = PendingIntent.getBroadcast(this, RecordID_received, intent,0);
        assert am != null;
        am.cancel(pi);


        System.out.println("Canceling alarm for "+RecordID_received);

    }



    public void delete_element(String ID1){
        String[] SelectionArgs = {ID1};
        int count = dbManager.Delete("ID=?", SelectionArgs);
        if (count > 0) {
            finish();
            CancelAlarm();
            Toast.makeText(getApplicationContext(), "Note deleted!", Toast.LENGTH_SHORT).show();
//            getdatabaseinfo(1,"ignore");
//            RecordID=0;
        }
        else {
            Toast.makeText(getApplicationContext(), "Can't delete!", Toast.LENGTH_SHORT).show();
        }
    }

    public void save_button_add_update(View view) {
        if(!add_this.equals("UPDATE")){
            //添加数据到数据库SQLite
            push_values_database();
        }
        else{
            //更新数据到数据库SQLite
            update_database();
        }

    }

    public void push_values_database(){
        //设定title和description非空
        if (!title_value.getText().toString().equals("") && !desc_value.getText().toString().equals("")) {
            ContentValues values = new ContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());

            values.put(DBManager.ColDateTime, currentDateandTime);
            values.put(DBManager.ColTitle, title_value.getText().toString());
            values.put(DBManager.ColDescription, desc_value.getText().toString());
            values.put(DBManager.ColPri, Priority);
            if(!RemTime.getText().toString().equalsIgnoreCase("ignore")) {
                values.put(DBManager.ColRemTime, RemTime.getText().toString());
                values.put(DBManager.ColRemDate, RemDate.getText().toString());
                SetAlarm(RemTime.getText().toString(), RemDate.getText().toString(),title_value.getText().toString(),desc_value.getText().toString());
            }
            else{
                values.put(DBManager.ColRemTime, "notset");
                values.put(DBManager.ColRemDate, "notset");
                // Then also cancel the alarm
            }
            long id = dbManager.Insert(values);
            if (id > 0) {
                Toast.makeText(getApplicationContext(), "Note Taken!", Toast.LENGTH_SHORT).show();
                finish();
//                etTitle.setText("");
//                etDesc.setText("");
//                getdatabaseinfo(1, "ignore");
//                RecordID = 0;

//                MainActivity ma1=new MainActivity();
//                ma1.getdatabaseinfo(1,"ignore");

            } else
                Toast.makeText(getApplicationContext(), "Failed to take Note", Toast.LENGTH_SHORT).show();
        }
        else {
            //title和description空值警告
            Toast.makeText(getApplicationContext(), "One or more fields are empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void update_database(){
        if (!title_value.getText().toString().equals("") && !desc_value.getText().toString().equals("")) {
            ContentValues values = new ContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());

            values.put(DBManager.ColDateTime, currentDateandTime);
            values.put(DBManager.ColTitle, title_value.getText().toString());
            values.put(DBManager.ColDescription, desc_value.getText().toString());
            values.put(DBManager.ColID, RecordID_received);
            values.put(DBManager.ColPri, Priority);
//            values.put(DBManager.ColPri, radioGroup.getCheckedRadioButtonId());
            if(!RemTime.getText().toString().equalsIgnoreCase("ignore")) {
                values.put(DBManager.ColRemTime, RemTime.getText().toString());
                values.put(DBManager.ColRemDate, RemDate.getText().toString());
                SetAlarm(RemTime.getText().toString(), RemDate.getText().toString(),title_value.getText().toString(),desc_value.getText().toString());

            }
            else{
                values.put(DBManager.ColRemTime, "notset");
                values.put(DBManager.ColRemDate, "notset");
                CancelAlarm();
            }

            String[] SelectionArgs = {String.valueOf(RecordID_received)};
            int count2 = dbManager.Update(values, "ID=?", SelectionArgs);

            long id = dbManager.Insert(values);
            if (count2 > 0) {
                //note更新
                Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show();
                finish();

            } else
                //更新警告
                Toast.makeText(getApplicationContext(), "Choose one Note to update!", Toast.LENGTH_SHORT).show();
//            etTitle.setText("");
//            etDesc.setText("");


//        androidx.fragment.app.FragmentManager fm=getSupportFragmentManager();
//        PopInfo popInfo=new PopInfo();
//        popInfo.show(fm,"Show Fragment");
        }
        else{
            //title和description空值警告
            Toast.makeText(getApplicationContext(), "One or more fields are empty", Toast.LENGTH_SHORT).show();
        }
    }
    public void close_act(View view) {
        finish();
    }


    //保存时间日期
    public void setDateTime(String time_received_reminder,String date_received_reminder){
        // Will be called from save button click of Dialog Frag
        RemTime.setText(time_received_reminder);
        RemDate.setText(date_received_reminder);
        RemTime.setVisibility(View.VISIBLE);
        RemDate.setVisibility(View.VISIBLE);
    }

    //删除保存的时间日期
    public void deleteRem(){
        RemTime.setVisibility(View.GONE);
        RemDate.setVisibility(View.GONE);
        RemTime.setText("ignore");
        RemDate.setText("ignore");
    }

    public void setTime_pass(String t){
        time_pass=t;
    }
    public void setDate_pass(String d){
        date_pass=d;
    }

}
