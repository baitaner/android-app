package net.baitaner;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * 
 * 用于遮盖界面，阻止用户操作，需要等待系统处理时调用
 * show()
 * dismiss()
 *
 */
public  class WaitingFragment extends DialogFragment
{
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    	getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    	getDialog().setCanceledOnTouchOutside(false);
    	this.getDialog().setOnKeyListener(new OnKeyListener(){
           public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event){
	           if (keyCode == KeyEvent.KEYCODE_BACK){
	             return true;
	           }
	           return false;
           }
        });
        v=inflater.inflate(R.layout.waitingdialog, container, false);
        return v;
    }
    
}