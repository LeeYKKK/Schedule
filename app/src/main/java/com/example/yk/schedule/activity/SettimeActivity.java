package com.example.yk.schedule.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;

import com.example.yk.schedule.DAO.ScheduleDAO;
import com.example.yk.schedule.R;
import com.example.yk.schedule.utils.SDate;
import com.example.yk.schedule.utils.STime;
import com.example.yk.schedule.utils.TipUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SettimeActivity extends AppCompatActivity {
    private ListView lvTime;  //时间ListView
    private Button btNext;  //下一步
    private SimpleAdapter simpleAdapter; //适配器
    private List<Map<String, Object>> mapList; //存放ListView数据的List
    private ArrayList<String> timelist;//上课时间List
    private String data;//开学日期
    private String tiptime;//提醒时间
    private String theme;//主题
    private ScheduleDAO sd;//数据库服务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settime);

        initDatas();
        findView();

    }

    private void findView() {
        setTitle("设定时间");
        lvTime = (ListView) findViewById(R.id.lvTime);
        btNext = (Button) findViewById(R.id.btNext);
        simpleAdapter = new SimpleAdapter(this, getData(), R.layout.settimelist,
                new String[]{"tip", "time"},
                new int[]{R.id.tvTip, R.id.tvTime});
        lvTime.setAdapter(simpleAdapter);
        //添加事件监听
        MyListener ml = new MyListener();
        lvTime.setOnItemClickListener(ml);


    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        //初始化数据库
        sd = new ScheduleDAO(SettimeActivity.this);
        //初始化mapList
        mapList = new ArrayList<Map<String, Object>>();
        //获取上课时间
        Cursor cursor = sd.getWeekValue("Time");
        timelist = new ArrayList<String>();
        while (cursor.moveToNext()) {
            timelist.add(cursor.getString(0));
        }

        Cursor cursor1 = sd.getWeekValue("data");
        if (cursor1.moveToNext()) {
            //获取开学时期
            data = cursor1.getString(0);
            //获取主题
            if (cursor1.moveToNext()) {
                switch (Integer.parseInt(cursor1.getString(0))) {
                    case 0:
                        theme = "阿狸";
                        break;
                    case 1:
                        theme = "表情包";
                        break;

                }
                //获取提醒时间
                if (cursor1.moveToNext()) {
                    tiptime = cursor1.getString(0);
                }
            }
        }

    }

    private List<Map<String, Object>> getData() {
        Map<String, Object> map;
        for (int i = 0; i < 6; i++) {
            map = new HashMap<String, Object>();
            map.put("tip", TipUtil.getTipWithFormate(i));
            map.put("time", timelist.get(i));
            mapList.add(map);
        }
        map = new HashMap<String, Object>();
        map.put("tip", "开学日期");
        map.put("time", data);
        mapList.add(map);
        map = new HashMap<String, Object>();
        map.put("tip", "提醒时间");
        map.put("time", tiptime);
        mapList.add(map);
        map = new HashMap<String, Object>();
        map.put("tip", "风格主题");
        map.put("time", theme);
        mapList.add(map);
        return mapList;
    }

    class MyListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (i <= 5) {
                //设置上课时间
                SetTime(i);
            } else if (i == 6) {
                //设置开学日期
                setDate(i);
            } else if (i == 7) {
                //设置提醒时间
                setTip(i);
            } else if (i == 8) {
                //设置风格
                setTheTheme(i);
            }

        }
    }

    private void SetTime(final int position) {
        new TimePickerDialog(SettimeActivity.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String thetime = STime.getTimeBack(new STime(hourOfDay, minute));
                String tip = TipUtil.getTipWithFormate(position);
                updateListView(new String[]{"tip", "time"}, new String[]{tip, thetime}, position);
                String theclass = TipUtil.getTheClass(position);
                sd.updateValue("Time", theclass, thetime);
                timelist.set(position, thetime);
            }
        }, STime.getTime(timelist.get(position)).getHour(), STime.getTime(timelist.get(position)).getMinutes(), true).show();
    }

    /**
     * 设置开学时间
     *
     * @param position
     */
    private void setDate(final int position) {
        //构造函数为DatePickerDialog(Context context, DatePickerDialog.OnDateSetListener listener, int year, int month, int day)
        new DatePickerDialog(SettimeActivity.this, new DatePickerDialog.OnDateSetListener() {
            //切记月份从零开始
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                String thedata = SDate.getDateBack(new SDate(year, monthOfYear + 1, dayOfMonth));
                updateListView(new String[]{"tip", "time"}, new String[]{"开学日期", thedata}, position);

                sd.updateValue("Data", "1-2", thedata);
                data = thedata;
            }
        }, SDate.getDate(data).getYear(), SDate.getDate(data).getMonth(), SDate.getDate(data).getDay()).show();
    }

    private void setTip(final int posotion) {
        new AlertDialog.Builder(SettimeActivity.this)
                .setTitle("请选择提醒时间")
                .setSingleChoiceItems(new String[]{"5分钟", "10分钟", "15分钟", "20分钟", "不提醒"}, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String valueTip = "";
                                switch (i) {
                                    case 0:
                                    case 1:
                                    case 2:
                                    case 3:
                                        valueTip = (i + 1) * 5 + "";
                                        break;
                                    case 4:
                                        valueTip = "不提醒";
                                        break;
                                }
                                updateListView(new String[]{"tip", "time"}, new String[]{"提醒时间", valueTip}, posotion);
                                sd.updateValue("Data", "5-6", valueTip);
                                dialogInterface.dismiss();

                            }
                        }).show();
    }

    /**
     * 设置主题
     * @param position
     */
    private void setTheTheme(final int position) {
        new AlertDialog.Builder(SettimeActivity.this)
                .setTitle("请选择主题风格")
                .setSingleChoiceItems(new String[]{"阿狸", "表情包"}, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String valueTip = "";
                                switch (which) {
                                    case 0:
                                        valueTip = "阿狸";
                                        break;
                                    case 1:
                                        valueTip = "表情包";
                                        break;
                                }
                                updateListView(new String[]{"tip", "time"}, new String[]{"主题风格", valueTip}, position);

                                sd.updateValue("Data", "3-4", which + "");
                                dialog.dismiss();
                            }
                        }
                )
                .show();
    }


    private void updateListView(String[] key, String[] value, int position) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < key.length; i++) {
            map.put(key[i], value[i]);
        }
        mapList.set(position, map);
        simpleAdapter.notifyDataSetChanged();
    }

}
