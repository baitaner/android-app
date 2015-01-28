package net.baitaner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/**
 * 显示欢迎界面并跳转到登陆界面
 */
public class WelcomeActivity extends Activity {
	
	private static final String TAG  = "AppStart";
	
	private AlphaAnimation start_anima;
	View view;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
		view = View.inflate(this, R.layout.welcome, null);
		setContentView(view);
		
		/**
		 * 延时动画
		 */
		start_anima = new AlphaAnimation(0.8f, 1.0f);
		start_anima.setDuration(2000);
		view.startAnimation(start_anima);
		start_anima.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				redirectTo();
			}
		});
    }

    /**
     * 跳转到登陆界面
     */
    private void redirectTo(){  
    	Intent intent;
    	
    	AppContext appContext = (AppContext) getApplication();

		if(appContext.isLogined()){
			intent = new Intent(WelcomeActivity.this, MarketActivity.class);
		}else{
			intent = new Intent(WelcomeActivity.this, LoginActivity.class);
		}
		
        startActivity(intent);
        WelcomeActivity.this.finish();
    }
}