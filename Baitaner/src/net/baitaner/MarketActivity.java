package net.baitaner;

import java.lang.reflect.Method;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;


/**
 * 
 * 交易处理
 * 
 * 包含2个Fragment：MarketMainFragment用于显示主页面和信息列表，MarketDetailFragment用于显示信息的详细内容
 * 
 * Activity只负责管理Fragment，不处理业务数据
 * 
 * 需要自定义的内容
 * Section.1 Activity初始化
 * Section.2 ActionBar初始化
 * Section.5 业务处理
 * 
 */

public class MarketActivity extends Activity {

	public final static String TAG_WAITING = "WAITING";
	public final static String TAG_MARKET = "MARKET";
	public final static String TAG_INFO = "INFOMATION";
	
	private AppContext appContext;
	private ActionBar mActionBar;
	
	/**
	 *  记录当前显示的Fragment
	 */
	private Fragment mCurrent;
	

	/**
	 *  用于遮盖界面，屏蔽用户操作的Fragment
	 */
	private WaitingFragment mWaiting;
	
	
	
	
	/*******************************************************************************
	 * 
	 * Section.1 生命周期处理
	 * 
	 *******************************************************************************/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		mActionBar = getActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		
		/**
		 *  重入时不处理
		 */
		if(null==savedInstanceState){
			/**
			 *  启动，加载主Fragment，此Fragment不能回退
			 */
			getFragmentManager()
				.beginTransaction()
				.add(R.id.content, new MarketMainFragment(),TAG_MARKET)
				.commit();
		}
		
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}
	
	
	
	/*******************************************************************************
	 * 
	 * Section.2 ActionBar初始化和相关方法
	 * 
	 *******************************************************************************/
	
	/**
	 * 设置ActionBar菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * ActionBar按钮处理
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		
		switch (item.getItemId()) {
		/**
		 *  ActionBar返回按钮，回退到上一个显示的Fragment
		 */
		case android.R.id.home:
			getFragmentManager().popBackStack();
			return true;
			
		case R.id.messages:
			intent = new Intent(MarketActivity.this, MessagesActivity.class);
	        startActivity(intent);
			return true;
			
		case R.id.settings:
			intent = new Intent(MarketActivity.this, SettingsActivity.class);
	        startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 设置ActionBar的Title，每次切换Fragment时都需要设置，包括新建和回退
	 * @param title    ActionBar上显示的标题
	 * @param showUP   标题左边的返回箭头
	 */
	public void setActionbar(Fragment current, String strTitle, boolean bShowUP) {
		mActionBar.setTitle(strTitle);
		mActionBar.setDisplayHomeAsUpEnabled(bShowUP);
		
		mCurrent = current;
	}
	
	/**
	 * Hack，在ActionBar的菜单中显示ICON
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	
	
	/*******************************************************************************
	 * 
	 * Section.3 Fragment方法
	 * 
	 *******************************************************************************/
	
	/**
	 * 界面切换，显示新的Fragment
	 * 
	 * @param containerViewId    Activity的layout里承载Fragment的容器
	 * @param fragment           需要显示的Fragment界面
	 * @param tag                统一使用变量名标记Fragment，同@param fragment
	 */
	public void addFragment(int containerViewId, Fragment fragment, String tag)  
    {  
		getFragmentManager()
		.beginTransaction()
		.hide(mCurrent)
		.add(containerViewId, fragment, tag)
		.addToBackStack(null)
		.commit();
		
		/**
		 * 为什么要.hide(mCurrent)
		 * 每次显示新的Fragment时需要隐藏当前显示的Fragment，只有被隐藏后才会在下次出栈显示时触发onHiddenChanged回调。
		 * 因为Fragment出栈时，生命周期不会发生变化，没有回调，无法进行处理。
		 * 这里主要用于设置ActionBar，当回退到上一个Fragment时需要重新设置ActionBar
		 */
    }
	
	/**
	 * 清空Fragment堆栈，直接回退到主Fragment
	 */
	public void backHome()  
    { 
		getFragmentManager()
		.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
	
	/**
	 * 显示waitingFragment
	 */
	public void showWaitingFragment() {
		if(mWaiting == null){
			mWaiting = (WaitingFragment) getFragmentManager().findFragmentByTag(TAG_WAITING);
			if(mWaiting == null){
				mWaiting = new WaitingFragment();
			}
		}
		if(!mWaiting.isAdded()){
		    FragmentTransaction transaction=getFragmentManager().beginTransaction();
		    mWaiting.show(transaction, TAG_WAITING);
		}
	}
	
	/**
	 * 隐藏waitingFragment
	 */
	public void dismissWaitingFragment() {
    	if(mWaiting != null){
    		mWaiting.dismiss();
    	}
	}
	
	
	/*******************************************************************************
	 * 
	 * Section.4 为公共方法
	 * 
	 *******************************************************************************/
	
	/**
	 * 显示提示信息
	 * @param message
	 */	
    public void showMessage(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    
    
	/*******************************************************************************
	 * 
	 * Section.5 事件处理
	 * 
	 *******************************************************************************/
    
	private long mExitTime;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(mCurrent.getTag() == TAG_MARKET){
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
					Toast.makeText(this, "再按一次退出",
							Toast.LENGTH_SHORT).show();
					mExitTime = System.currentTimeMillis();
				} else {
					finish();
				}
				return true;
			}
		}

		return super.onKeyDown(keyCode, event);
	}
	
	
    /**
     * 所有Fragment静态按钮控件的onClick事件都只能在Activity里处理
     */
	public void onMarketClick(View v) {
		
		switch (v.getId()) {
		case R.id.login:
			break;

		default:
			break;
		}
		
	}
	
	
	
}
