package com.yu.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yu.dialog.util.Method;
import com.yu.dialog.view.CommonDialog;
import com.yu.dialog.view.CommonDialog2;
import com.yu.dialog.view.CommonDialogSingle;
import com.yu.dialog.view.DatetimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    protected Date now = new Date();
    public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.common_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommonDialog(MainActivity.this).showCommonDialog("提示信息", false, "弹出的对话框长文本内容", "关闭", "确定", new Method.Action1<String>() {
                    @Override
                    public void invoke(String p) {
                        Toast.makeText(MainActivity.this,"点击确定按钮",Toast.LENGTH_LONG).show();
                    }
                }, null, false);
            }
        });
        findViewById(R.id.common_dialog2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatetimePickerView datetimePickerView = new DatetimePickerView(MainActivity.this,now,sdf2);
                CommonDialog2.Builder builder = new CommonDialog2.Builder(MainActivity.this);
                builder.setOnClickListener(new CommonDialog2.OnButtonItemClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, View view, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(MainActivity.this,"点击确定按钮",Toast.LENGTH_LONG).show();
                                break;
                            case DialogInterface.BUTTON_NEUTRAL:
                                Toast.makeText(MainActivity.this,"点击清除按钮",Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
                builder.setPositiveButton(getString(R.string.ok))
                        .setNegativeButton(R.string.cancel)
                        .setNeutralButton("清除")
                        .setView(datetimePickerView).setTitle("开始时间").create().show();
            }
        });
        findViewById(R.id.common_dialog3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommonDialogSingle(MainActivity.this).showCommonDialog("提示信息", true,

                        "你输入的手机号已经注册帐号,请输入其他未注册的手机号", "点击关闭",
                        null, true);
            }
        });
    }
}
