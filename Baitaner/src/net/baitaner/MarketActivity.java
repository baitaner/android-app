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
 * ���״���
 * 
 * ����2��Fragment��MarketMainFragment������ʾ��ҳ�����Ϣ�б�MarketDetailFragment������ʾ��Ϣ����ϸ����
 * 
 * Activityֻ�������Fragment��������ҵ������
 * 
 * ��Ҫ�Զ��������
 * Section.1 Activity��ʼ��
 * Section.2 ActionBar��ʼ��
 * Section.5 ҵ����
 * 
 */

public class MarketActivity extends Activity {

	public final static String TAG_WAITING = "WAITING";
	public final static String TAG_MARKET = "MARKET";
	public final static String TAG_INFO = "INFOMATION";
	
	private AppContext appContext;
	private ActionBar mActionBar;
	
	/**
	 *  ��¼��ǰ��ʾ��Fragment
	 */
	private Fragment mCurrent;
	

	/**
	 *  �����ڸǽ��棬�����û�������Fragment
	 */
	private WaitingFragment mWaiting;
	
	
	
	
	/*******************************************************************************
	 * 
	 * Section.1 �������ڴ���
	 * 
	 *******************************************************************************/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		mActionBar = getActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		
		/**
		 *  ����ʱ������
		 */
		if(null==savedInstanceState){
			/**
			 *  ������������Fragment����Fragment���ܻ���
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
	 * Section.2 ActionBar��ʼ������ط���
	 * 
	 *******************************************************************************/
	
	/**
	 * ����ActionBar�˵�
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * ActionBar��ť����
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		
		switch (item.getItemId()) {
		/**
		 *  ActionBar���ذ�ť�����˵���һ����ʾ��Fragment
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
	 * ����ActionBar��Title��ÿ���л�Fragmentʱ����Ҫ���ã������½��ͻ���
	 * @param title    ActionBar����ʾ�ı���
	 * @param showUP   ������ߵķ��ؼ�ͷ
	 */
	public void setActionbar(Fragment current, String strTitle, boolean bShowUP) {
		mActionBar.setTitle(strTitle);
		mActionBar.setDisplayHomeAsUpEnabled(bShowUP);
		
		mCurrent = current;
	}
	
	/**
	 * Hack����ActionBar�Ĳ˵�����ʾICON
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
	 * Section.3 Fragment����
	 * 
	 *******************************************************************************/
	
	/**
	 * �����л�����ʾ�µ�Fragment
	 * 
	 * @param containerViewId    Activity��layout�����Fragment������
	 * @param fragment           ��Ҫ��ʾ��Fragment����
	 * @param tag                ͳһʹ�ñ��������Fragment��ͬ@param fragment
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
		 * ΪʲôҪ.hide(mCurrent)
		 * ÿ����ʾ�µ�Fragmentʱ��Ҫ���ص�ǰ��ʾ��Fragment��ֻ�б����غ�Ż����´γ�ջ��ʾʱ����onHiddenChanged�ص���
		 * ��ΪFragment��ջʱ���������ڲ��ᷢ���仯��û�лص����޷����д���
		 * ������Ҫ��������ActionBar�������˵���һ��Fragmentʱ��Ҫ��������ActionBar
		 */
    }
	
	/**
	 * ���Fragment��ջ��ֱ�ӻ��˵���Fragment
	 */
	public void backHome()  
    { 
		getFragmentManager()
		.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
	
	/**
	 * ��ʾwaitingFragment
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
	 * ����waitingFragment
	 */
	public void dismissWaitingFragment() {
    	if(mWaiting != null){
    		mWaiting.dismiss();
    	}
	}
	
	
	/*******************************************************************************
	 * 
	 * Section.4 Ϊ��������
	 * 
	 *******************************************************************************/
	
	/**
	 * ��ʾ��ʾ��Ϣ
	 * @param message
	 */	
    public void showMessage(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    
    
	/*******************************************************************************
	 * 
	 * Section.5 �¼�����
	 * 
	 *******************************************************************************/
    
	private long mExitTime;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(mCurrent.getTag() == TAG_MARKET){
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
					Toast.makeText(this, "�ٰ�һ���˳�",
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
     * ����Fragment��̬��ť�ؼ���onClick�¼���ֻ����Activity�ﴦ��
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
