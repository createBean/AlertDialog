
package com.yu.dialog.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yu.dialog.R;
import com.yu.dialog.widget.OnWheelChangedListener;
import com.yu.dialog.widget.WheelView;
import com.yu.dialog.widget.adapters.ArrayWheelAdapter;
import com.yu.dialog.widget.adapters.NumericWheelAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatetimePickerView extends LinearLayout {

	protected Date value;
	protected SimpleDateFormat format;

	protected WheelView yearWheelView;
	protected WheelView monthWheelView;
	protected WheelView dayWheelView;
	protected WheelView hourWheelView;
	protected WheelView minuteWheelView;
	SimpleDateFormat sdf;

	OnWheelChangedListener dateUpdateListener = new OnWheelChangedListener() {
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			updateDays();
		}
	};

	public DatetimePickerView(Context context) {
		super(context);
		initialize();
	}

	public DatetimePickerView(Context context, Date value, SimpleDateFormat format) {
		super(context);
		this.value = value;
		this.format = format;
		initialize();
	}

	public DatetimePickerView(Context context, AttributeSet attrs) {
		super(context);
		initFromAttributes(context, attrs);
		initialize();
	}

	protected void initialize() {
		if (format == null) {
			format = new SimpleDateFormat("yyyy-MM-dd");
		}
		if (value == null) {
			value = new Date();
		}

		this.setGravity(Gravity.CENTER);
		this.setOrientation(LinearLayout.HORIZONTAL);

		LayoutParams llp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		llp.weight = 1;

		if (format.toPattern().indexOf("y") != -1) {
			int nowYear = Calendar.getInstance().get(Calendar.YEAR);
			int year = value.getYear() + 1900;
			yearWheelView = new WheelView(getContext());
			yearWheelView.setViewAdapter(new DateNumericAdapter(getContext(), Math.min(nowYear, year) - 10, Math.max(nowYear, year) + 10, 10, getResources().getString(R.string.year)));
			yearWheelView.addChangingListener(dateUpdateListener);
			this.addView(yearWheelView, llp);
		}
		if (format.toPattern().indexOf("M") != -1) {
			int month = value.getMonth();
			String months[] = new String[]{
					getResources().getString(R.string.january), getResources().getString(R.string.february), getResources().getString(R.string.march), getResources().getString(R.string.april), getResources().getString(R.string.may),
					getResources().getString(R.string.june), getResources().getString(R.string.july), getResources().getString(R.string.august), getResources().getString(R.string.september), getResources().getString(R.string.october), getResources().getString(R.string.november), getResources().getString(R.string.december)
			};
			monthWheelView = new WheelView(getContext());
			monthWheelView.setViewAdapter(new DateArrayAdapter(getContext(), months, month));
			monthWheelView.addChangingListener(dateUpdateListener);
			this.addView(monthWheelView, llp);
		}
		if (format.toPattern().indexOf("d") != -1) {
			dayWheelView = new WheelView(getContext());
			updateDays();
			this.addView(dayWheelView, llp);
		}
		if (format.toPattern().indexOf("H") != -1) {
			hourWheelView = new WheelView(getContext());
			hourWheelView.setViewAdapter(new DateNumericAdapter(getContext(), 0, 23, value.getHours(), getResources().getString(R.string._time)));
			this.addView(hourWheelView, llp);
		}
		if (format.toPattern().indexOf("m") != -1) {
			minuteWheelView = new WheelView(getContext());
			minuteWheelView.setViewAdapter(new DateNumericAdapter(getContext(), 0, 59, value.getMinutes(), getResources().getString(R.string.minute)));
			this.addView(minuteWheelView, llp);
		}

		setValue(value);
	}

	protected void initFromAttributes(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DatetimePicker);
		String format = a.getString(R.styleable.DatetimePicker_format);
		String value = a.getString(R.styleable.DatetimePicker_value);
		if (!TextUtils.isEmpty(format)) {
			this.format = new SimpleDateFormat(format);
		}
		if (!TextUtils.isEmpty(value)) {
			try {
				this.value = this.format.parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	protected void updateDays() {
		if (dayWheelView == null)
			return;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + yearWheelView.getCurrentItem());
		calendar.set(Calendar.MONTH, monthWheelView.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		dayWheelView.setViewAdapter(new DateNumericAdapter(getContext(), 1, maxDays, value.getDate() - 1, getResources().getString(R.string.day)));
	}

	public void setValue(Date newDate) {
		this.value = newDate;
		if (yearWheelView != null) {
			yearWheelView.setCurrentItem(newDate.getYear() - value.getYear() + 10);
		}
		if (monthWheelView != null) {
			monthWheelView.setCurrentItem(value.getMonth());
			((DateArrayAdapter) monthWheelView.getViewAdapter()).setCurrentValue(value.getMonth());
		}
		if (dayWheelView != null) {
			dayWheelView.setCurrentItem(value.getDate() - 1);
		}
		if (hourWheelView != null) {
			hourWheelView.setCurrentItem(value.getHours());
		}
		if (minuteWheelView != null) {
			minuteWheelView.setCurrentItem(value.getMinutes());
		}
	}

	public Date getSelectedDate() {
		Calendar calendar = Calendar.getInstance();
		if (yearWheelView != null) {
			calendar.set(Calendar.YEAR, getSelectedYear());
		}
		if (monthWheelView != null) {
			calendar.set(Calendar.MONTH, getSelectedMonth());
		}
		if (dayWheelView != null) {
			calendar.set(Calendar.DATE, getSelectedDay());
		}
		if (hourWheelView != null) {
			calendar.set(Calendar.HOUR_OF_DAY, getSelectedHour());
		}
		if (minuteWheelView != null) {
			calendar.set(Calendar.MINUTE, getSelectedMinute());
		}
		return calendar.getTime();
	}

	public Integer getSelectedYear() {
		if (yearWheelView == null) {
			return null;
		}
		return yearWheelView.getCurrentItem() - 10 + value.getYear() + 1900;
	}

	public Integer getSelectedMonth() {
		if (monthWheelView == null) {
			return null;
		}
		return monthWheelView.getCurrentItem();
	}

	public Integer getSelectedDay() {
		if (dayWheelView == null) {
			return null;
		}
		return dayWheelView.getCurrentItem() + 1;
	}

	public Integer getSelectedHour() {
		if (hourWheelView == null) {
			return null;
		}
		return hourWheelView.getCurrentItem();
	}

	public Integer getSelectedMinute() {
		if (minuteWheelView == null) {
			return null;
		}
		return minuteWheelView.getCurrentItem();
	}

	protected class DateNumericAdapter extends NumericWheelAdapter {
		int currentItem;
		int currentValue;

		public DateNumericAdapter(Context context, int minValue, int maxValue, int current, String format) {
			super(context, minValue, maxValue, format);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	protected class DateArrayAdapter extends ArrayWheelAdapter<String> {
		int currentItem;
		int currentValue;

		public DateArrayAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}

		protected void setCurrentValue(int currentValue) {
			this.currentValue = currentValue;
		}
	}
}
