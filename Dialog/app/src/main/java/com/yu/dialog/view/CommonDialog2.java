package com.yu.dialog.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yu.dialog.R;
import com.yu.dialog.util.StringUtil;

public class CommonDialog2 {
    private Dialog dlg;

    private CommonDialog2() {

    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mMessage;
        private String mPositiveButtonText;//积极的，正面的。
        private String mNegativeButtonText;//负面的，消极的。
        private String mNeutralButtonText;//中立的，
        private boolean mCancelable = false;
        private boolean mIsEdit = false;
        private DialogInterface.OnCancelListener mOnCancelListener;
        private DialogInterface.OnDismissListener mOnDismissListener;
        private DialogInterface.OnKeyListener mOnKeyListener;
        private OnButtonItemClickListener mClickListener;
        private View mView;
        public Builder(Context activity) {
            mContext = activity;
            mTitle = "提示";
        }

        public Builder setView(View view) {
            mView = view;
            return this;
        }
        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setTitle(int stringResId) {
            mTitle = mContext.getResources().getString(stringResId);
            return this;
        }

        public Builder setMessage(int messageId) {
            mMessage = mContext.getResources().getString(messageId);
            return this;
        }

        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        public Builder setOnClickListener(OnButtonItemClickListener clickListener) {
            mClickListener = clickListener;
            return this;
        }

        public Builder setPositiveButton(int textId) {
            mPositiveButtonText = mContext.getResources().getString(textId);
            return this;
        }

        public Builder setPositiveButton(String text) {
            mPositiveButtonText = text;
            return this;
        }

        public Builder setNegativeButton(int textId) {
            mNegativeButtonText = mContext.getResources().getString(textId);
            return this;
        }

        public Builder setNegativeButton(String text) {
            mNegativeButtonText = text;
            return this;
        }

        public Builder setNeutralButton(int textId) {
            mNeutralButtonText = mContext.getResources().getString(textId);
            return this;
        }

        public Builder setNeutralButton(String text) {
            mNeutralButtonText = text;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder isEdit(boolean isEdit) {
            mIsEdit = isEdit;
            return this;
        }

        public CommonDialog2 create() {
            final CommonDialog2 dialog2 = new CommonDialog2();
            dialog2.create(mContext);
            dialog2.dlg.setCancelable(mCancelable);
            final TextView titleTv = (TextView) dialog2.dlg.getWindow().findViewById(R.id.title_name);
            final TextView contentTv = (TextView) dialog2.dlg.getWindow().findViewById(R.id.text_view);
            final EditText editText = (EditText) dialog2.dlg.getWindow().findViewById(R.id.edit_text);
            final Button ok = (Button) dialog2.dlg.getWindow().findViewById(R.id.btn_ok);
            final Button cancel = (Button) dialog2.dlg.getWindow().findViewById(R.id.btn_cancel);
            final Button btnCentre = (Button) dialog2.dlg.getWindow().findViewById(R.id.btn_centre);
            final FrameLayout centreView = (FrameLayout) dialog2.dlg.getWindow().findViewById(R.id.centre_view_confirm_dialog);

            if(mView != null) {
                centreView.setVisibility(View.VISIBLE);
                centreView.removeAllViews();
                centreView.addView(mView);
            }else if (mIsEdit) {
                editText.setVisibility(View.VISIBLE);
                contentTv.setVisibility(View.GONE);
                contentTv.setTextColor(Color.RED);
                contentTv.setTextSize(10);
            } else {
                editText.setVisibility(View.GONE);
                contentTv.setVisibility(View.VISIBLE);
                if (!StringUtil.isNullOrWhiteSpace(mMessage)) {
                    contentTv.setText(mMessage);
                }
            }

            if(mTitle != null) {
                titleTv.setText(mTitle);
            }

            if (!StringUtil.isNullOrWhiteSpace(mPositiveButtonText)) {
                ok.setText(mPositiveButtonText);
            } else {
                ok.setText("确定");
            }

            if (!StringUtil.isNullOrWhiteSpace(mNegativeButtonText)) {
                cancel.setText(mNegativeButtonText);
            } else {
                cancel.setText("取消");
            }

            if(!StringUtil.isNullOrWhiteSpace(mNeutralButtonText)) {
                btnCentre.setText(mNeutralButtonText);
            } else {
                btnCentre.setVisibility(View.GONE);
                dialog2.dlg.getWindow().findViewById(R.id.separator0_confirm_dialog2).setVisibility(View.GONE);
            }

            View.OnClickListener clickListener = new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int which = (Integer)view.getTag();
                    if(mClickListener != null) {
                        mClickListener.onClick(dialog2.dlg,mView,which);
                    }
                    if(dialog2!=null && dialog2.isShowing()) {
                        dialog2.dismiss();
                    }
                }
            };

            dialog2.dlg.setOnCancelListener(mOnCancelListener);
            dialog2.dlg.setOnDismissListener(mOnDismissListener);
            dialog2.dlg.setOnKeyListener(mOnKeyListener);

            cancel.setTag(DialogInterface.BUTTON_NEGATIVE);
            btnCentre.setTag(DialogInterface.BUTTON_NEUTRAL);
            ok.setTag(DialogInterface.BUTTON_POSITIVE);
            cancel.setOnClickListener(clickListener);
            btnCentre.setOnClickListener(clickListener);
            ok.setOnClickListener(clickListener);
            return dialog2;
        }
    }

    public void create(Context context) {
        dlg = new Dialog(context);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        window = dlg.getWindow();
        dlg.setContentView(R.layout.confirm_dialog2);
////        window.setContentView(R.layout.confirm_dialog3);
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
//                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//
//        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
//        Display d = wm.getDefaultDisplay();
//        WindowManager.LayoutParams p = window.getAttributes();
//        p.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        p.gravity = Gravity.CENTER;
//        window.setAttributes(p);
    }

    public void show() {
        dlg.show();
    }

    public void dismiss() {
        dlg.dismiss();
    }

    public boolean isShowing() {
        if(dlg!=null && dlg.isShowing()) {
            return true;
        }
        return false;
    }

    public interface OnButtonItemClickListener {
        public void onClick(DialogInterface dialog, View view, int which);
    }
}
