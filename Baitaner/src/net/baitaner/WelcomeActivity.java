package net.baitaner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/**
 * ��ʾ��ӭ���沢��ת����½����
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
		 * ��ʱ����
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
     * ��ת����½����
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