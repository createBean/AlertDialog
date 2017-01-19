package com.yu.dialog.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yu.dialog.R;
import com.yu.dialog.util.Method;
import com.yu.dialog.util.StringUtil;

public class CommonDialog {
	protected Activity activity;
	protected Dialog dlg;
	protected Window window;
	public final static String VALIDATE_INFO = "result is ok";
	private String errorInfo = VALIDATE_INFO;
	protected String hint, text;

	public CommonDialog(Activity activity) {
		this.activity = activity;
		dlg = new Dialog(activity);
		dlg.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		window = dlg.getWindow();
		window.setContentView(R.layout.confirm_dialog);
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		WindowManager m = activity.getWindowManager();
		Display d = m.getDefaultDisplay();
		WindowManager.LayoutParams p = activity.getWindow().getAttributes();
		p.height = d.getHeight();
		p.width = ViewGroup.LayoutParams.MATCH_PARENT;
		p.gravity = Gravity.CENTER;
	}

	public CommonDialog setHint(String hint) {
		this.hint = hint;
		return this;
	}

	public CommonDialog setText(String text) {
		this.text = text;
		return this;
	}

	public void showCommonDialog(String title, final Method.Action1<String> action) {
		showCommonDialog(title, true, "确定修改吗", "", action);
	}

	public void showCommonDialog(String title, boolean isEidt, final Method.Action1<String> action) {
		showCommonDialog(title, true, "", "", action);
	}

	public void showCommonDialog(String title, boolean isEidt, String okString, final Method.Action1<String> action) {
		showCommonDialog(title, true, "", okString, action);
	}

	public void showCommonDialog(String title, String content, final Method.Action1<String> action) {
		showCommonDialog(title, false, content, "", action);
	}

	public void showCommonDialog(String title, String content, String okString, final Method.Action1<String> action) {
		showCommonDialog(title, false, content, okString, action);
	}

	public void showCommonDialog(String title, boolean isEdit, String content, String okString, final Method.Action1<String> action) {
		showCommonDialog(title, isEdit, content, okString, action, null);
	}

	public void showCommonDialog(String title, boolean isEdit, String content, String okString, final Method.Action1<String> action, final Method.Action cancleAction) {
		showCommonDialog(title, isEdit, content, null, okString, action, cancleAction);
	}

	public void showCommonDialog(String title, boolean isEdit, String content, String cancelString, String okString, final Method.Action1<String> action, final Method.Action cancleAction) {
		showCommonDialog(title, isEdit, content, cancelString, okString, action, cancleAction, true);
	}

	public void showCommonDialog(String title, boolean isEdit, String content, String cancelString, String okString, final Method.Action1<String> action, final Method.Action cancleAction, boolean autoCancel) {
		final TextView titleTv = (TextView) window.findViewById(R.id.title_name);
		final TextView contentTv = (TextView) window.findViewById(R.id.text_view);
		final EditText editText = (EditText) window.findViewById(R.id.edit_text);
		final Button ok = (Button) window.findViewById(R.id.btn_ok);
		final Button cancel = (Button) window.findViewById(R.id.btn_cancel);
		if (!StringUtil.isNullOrWhiteSpace(title)) {
			titleTv.setText(title);
		} else {
			titleTv.setVisibility(View.GONE);
		}
		if (isEdit) {
			editText.setVisibility(View.VISIBLE);
			contentTv.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
//            contentTv.setTextColor(Color.RED);
//            contentTv.setTextSize(10);
			contentTv.setText(content);

			if (!StringUtil.isNullOrWhiteSpace(hint)) {
				editText.setHint(hint);
			}
			if (!StringUtil.isNullOrWhiteSpace(text)) {
				editText.setText(text);
				editText.setSelection(text.length());//将光标移至文字末尾
			}
		} else {
			editText.setVisibility(View.GONE);
			contentTv.setVisibility(View.VISIBLE);
			if (!StringUtil.isNullOrWhiteSpace(content)) {
				contentTv.setText(content);
			}
		}
		if (!StringUtil.isNullOrWhiteSpace(okString)) {
			ok.setText(okString);
		} else {
			ok.setText("确定");
		}
		if (!StringUtil.isNullOrWhiteSpace(cancelString)) {
			cancel.setText(cancelString);
		} else {
			cancel.setText("取消");
		}
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (action != null) {
					String text = editText.getText().toString();
					action.invoke(StringUtil.isNullOrWhiteSpace(text) ? "" : text);
				}
				if (VALIDATE_INFO.equals(errorInfo)) {
					dlg.dismiss();
				} else {
					contentTv.setText(errorInfo);
					contentTv.setVisibility(View.VISIBLE);
					errorInfo = VALIDATE_INFO;
				}
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dlg.cancel();
				if (cancleAction != null)
					cancleAction.invoke();
			}
		});
		if (autoCancel) {
			dlg.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					if (cancleAction != null)
						cancleAction.invoke();
				}
			});
		}
		if (!activity.isFinishing()) {
			dlg.show();
		}
	}

	public void showError(String text) {
		errorInfo = text;
	}
}
