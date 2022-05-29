package com.example.reminder.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.reminder.R;
import com.example.reminder.activity.MainActivity2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PopInfo extends DialogFragment{
    View view;
    TextView time_df,date_df;
    ImageButton back_rem, time_pick, date_pic;
    Button delete_rem, save_rem;
    String time_get, date_get;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //Fragment界面对应pop_info
        view=inflater.inflate(R.layout.pop_info, container, false);
        back_rem=(ImageButton)view.findViewById(R.id.back_rem);
        time_pick=(ImageButton)view.findViewById(R.id.time_pick);
        date_pic=(ImageButton)view.findViewById(R.id.date_pic);
        delete_rem=(Button) view.findViewById(R.id.delete_rem);
        save_rem=(Button) view.findViewById(R.id.save_rem);
        time_df=(TextView)view.findViewById(R.id.time_df);
        date_df=(TextView)view.findViewById(R.id.date_df);


        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("time_value")))
            time_get=getArguments().getString("time_value");
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("date_value")))
            date_get=getArguments().getString("date_value");


        //获取当前时间
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        String currentTime1 = sdf1.format(new Date());
        String currentDate1 = sdf.format(new Date());

        if(time_get.equalsIgnoreCase("ignore")) {
            time_df.setText(currentTime1);
        }
        else{
            time_df.setText(time_get);
        }


        if(date_get.equalsIgnoreCase("ignore")) {
            date_df.setText(currentDate1);
        }
        else{

//            String[] date_array = date_get.split("-",3);
//
//            for (String a : date_array)
//                System.out.println("Holathis13"+a);
//
//            System.out.println("Holathis123"+date_array[0]);
//            System.out.println("Holathis123"+date_array[1]);
//            System.out.println("Holathis123"+date_array[2]);
//            Integer month_correction=Integer.parseInt(date_array[1])+1;
//            String s1=date_array[2]+"-"+month_correction+"-"+date_array[0];
            date_df.setText(date_get);
        }


        back_rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),"back_rem",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        //点击time_df跳转修改时间
        time_df.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Time Picker",Toast.LENGTH_SHORT).show();
                //使用getSupportFragmentManager()进行替代
                androidx.fragment.app.FragmentManager fragmentManager1=getFragmentManager();
                PopTime poptime = new PopTime();
                Bundle bundle1_time = new Bundle();
                bundle1_time.putString("time_value1", time_get);
                bundle1_time.putString("date_value1", date_get);

                poptime.setArguments(bundle1_time);

                poptime.show(fragmentManager1,"Time Picker Fragment Show");
                dismiss();
            }
        });

        //点击time_pick跳转修改时间
        time_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Time Picker",Toast.LENGTH_SHORT).show();
                //使用getSupportFragmentManager()进行替代
                androidx.fragment.app.FragmentManager fragmentManager1=getFragmentManager();
                PopTime poptime = new PopTime();
                Bundle bundle1_time = new Bundle();
                bundle1_time.putString("time_value1", time_get);
                bundle1_time.putString("date_value1", date_get);

                poptime.setArguments(bundle1_time);

                poptime.show(fragmentManager1,"Time Picker Fragment Show");
                dismiss();
            }
        });

        //点击date_df跳转修改日期
        date_df.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Date Picker",Toast.LENGTH_SHORT).show();
                //使用getSupportFragmentManager()进行替代
                androidx.fragment.app.FragmentManager fragmentManager2=getFragmentManager();
                PopDate popdate = new PopDate();
                Bundle bundle1_date = new Bundle();
                bundle1_date.putString("time_value2", time_get);
                bundle1_date.putString("date_value2", date_get);

                popdate.setArguments(bundle1_date);

                popdate.show(fragmentManager2,"Date Picker Fragment Show");
                dismiss();
            }
        });

        //点击date_pic跳转修改日期
        date_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Date Picker",Toast.LENGTH_SHORT).show();
                //使用getSupportFragmentManager()进行替代
                androidx.fragment.app.FragmentManager fragmentManager2=getFragmentManager();
                PopDate popdate = new PopDate();
                Bundle bundle1_date = new Bundle();
                bundle1_date.putString("time_value2", time_get);
                bundle1_date.putString("date_value2", date_get);

                popdate.setArguments(bundle1_date);

                popdate.show(fragmentManager2,"Date Picker Fragment Show");
                dismiss();
            }
        });

        //点击delete_rem取消修改日期时间
        delete_rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //已覆盖
                Toast.makeText(getContext(),"Reminder Removed!",Toast.LENGTH_SHORT).show();
                MainActivity2 m3a1=(MainActivity2)getActivity();
                m3a1.deleteRem();
                dismiss();
            }
        });

        //点击save_rem保存修改日期时间
        save_rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //增加
                Toast.makeText(getContext(),"Reminder Added!",Toast.LENGTH_SHORT).show();
                MainActivity2 m3a=(MainActivity2)getActivity();
                m3a.setDateTime(time_df.getText().toString(),date_df.getText().toString());
                dismiss();
            }
        });

        return view;
    }

}
