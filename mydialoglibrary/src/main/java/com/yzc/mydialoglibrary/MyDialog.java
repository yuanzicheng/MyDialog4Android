package com.yzc.mydialoglibrary;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/5/27.
 */
public class MyDialog {

	public final static int TYPE_INFO = 1;
	public final static int TYPE_WARNING = 2;
	public final static int TYPE_ERROR = 3;
	public final static int TYPE_OK = 4;

	AlertDialog alertDialog = null;
	Context context = null;
	int mWidth;
	int mHeight;

	public MyDialog(Context context) {
		this.context = context;
	}

	public void dismiss(){
		alertDialog.dismiss();
	}

	// waiting
	public void waiting(String text, Boolean cancelAble) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View dialog_view = inflater.inflate(R.layout.layout_dialog_loading,null);
		alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setCancelable(false);
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();
		Window window = alertDialog.getWindow();
		window.setContentView(dialog_view);
		window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		((TextView) dialog_view.findViewById(R.id.text)).setText(text);

		ImageView cancel = (ImageView) dialog_view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});
		if (!cancelAble) {
			cancel.setVisibility(View.GONE);
		}
	}



	public void message(int type, String title, String text) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View dialog_view = inflater.inflate(R.layout.layout_dialog_message, null);
		alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.show();
		alertDialog.setCancelable(false);
		alertDialog.setCanceledOnTouchOutside(true);
		Window window = alertDialog.getWindow();

		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);// AlertDialog中的EditText获取焦点时，弹起软键盘
		window.setContentView(dialog_view);

		chooseIcon(type,(ImageView) dialog_view.findViewById(R.id.icon));

		((TextView) dialog_view.findViewById(R.id.title)).setText(title);
		((TextView) dialog_view.findViewById(R.id.text)).setText(text);
		
		if(text==null){
			((LinearLayout)dialog_view.findViewById(R.id.bottom)).setVisibility(View.GONE);
		}
		
		((LinearLayout)dialog_view.findViewById(R.id.bottom)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});
	}

	public void prompt(int type, String title) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View dialog_view = inflater.inflate(R.layout.layout_dialog_prompt, null);
		alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.show();
		alertDialog.setCancelable(false);
		alertDialog.setCanceledOnTouchOutside(false);
		Window window = alertDialog.getWindow();

		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);// AlertDialog中的EditText获取焦点时，弹起软键盘
		window.setContentView(dialog_view);

		chooseIcon(type,(ImageView) dialog_view.findViewById(R.id.icon));

		((TextView) dialog_view.findViewById(R.id.title)).setText(title);

		new Handler().postDelayed(new Runnable(){
			public void run() {
				alertDialog.dismiss();
			}
		}, 1500);

	}

	// 确定，取消，回调
	public void callback(int type, String title, final MyDialogListener listener1,String positive, final MyDialogListener listener2, String negative) {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View dialog_view = inflater.inflate(R.layout.layout_dialog_callback, null);
		alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.show();
		alertDialog.setCancelable(false);
		alertDialog.setCanceledOnTouchOutside(true);
		Window window = alertDialog.getWindow();

		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);// AlertDialog中的EditText获取焦点时，弹起软键盘
		window.setContentView(dialog_view);

		chooseIcon(type,(ImageView) dialog_view.findViewById(R.id.icon));

		((TextView) dialog_view.findViewById(R.id.title)).setText(title);

		// 确定
		Button positiveButton = (Button) dialog_view.findViewById(R.id.positiveButton);
		if (positive != null) {
			positiveButton.setText(positive);
			positiveButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					alertDialog.dismiss();
					if (listener1 != null) {
						listener1.callback(new String[]{"",""});
					}
				}
			});
		} else {
			positiveButton.setVisibility(View.GONE);
		}

		// 取消
		Button negativeButton = (Button) dialog_view.findViewById(R.id.negativeButton);
		if (negative != null) {
			negativeButton.setText(negative);
			negativeButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					alertDialog.dismiss();
					if (listener2 != null) {
						listener2.callback(new String[]{"",""});
					}
				}
			});
		} else {
			negativeButton.setVisibility(View.GONE);
		}
		if (positive == null || negative == null) {
			((View) dialog_view.findViewById(R.id.bottom_separator)).setVisibility(View.GONE);
		}
		if (positive == null && negative == null) {
			((View) dialog_view.findViewById(R.id.bottom)).setVisibility(View.GONE);
		}
	}

	/**
	 * 计算PopupWindow的宽高
	 */
	private void calculatePopupWindowSize(){
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);
		mWidth = metrics.widthPixels;
		mHeight = (int)(0.6*metrics.heightPixels);
	}


	/**
	 * 根据类型使用不同的icon
	 * @param type
	 * @param imageView
     */
	private void chooseIcon(int type,ImageView imageView){
		if(imageView!=null){
			switch(type) {
				case TYPE_INFO:
					imageView.setImageResource(R.mipmap.icon_info);
					break;
				case TYPE_WARNING:
					imageView.setImageResource(R.mipmap.icon_warning);
					break;
				case TYPE_ERROR:
					imageView.setImageResource(R.mipmap.icon_error);
					break;
				case TYPE_OK:
					imageView.setImageResource(R.mipmap.icon_success);
					break;
			}
		}
	}
}
