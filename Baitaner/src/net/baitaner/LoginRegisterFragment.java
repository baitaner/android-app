package net.baitaner;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * 
 * 注册UI
 * 
 * 需要自定义的内容
 * Section.1 Layout初始化、数据初始化
 * Section.2 业务处理
 * 
 */

public class LoginRegisterFragment extends Fragment {

	private final static String TAG_TITLE = "title";
	private final static String TAG_HIDDEN = "isHidden";
	
	private String mTitle;

	
	/*******************************************************************************
	 * 
	 * Section.1 生命周期处理
	 * 
	 *******************************************************************************/
	
	/**
	 * Fragment生命周期
	 * 
	 * 创建：（新建、屏幕旋转）
	 *    onAttach()
	 *    onCreate()
	 *    onCreateView()
	 *    onActivityCreated()
	 *    onStart()
	 *    onResume()
	 * 
	 * 离开：（Home键、切换其他APP）
	 *    onPause()
	 *    onStop()
	 *    
	 * 进入：（切换回APP）
	 * 	  onStart()
	 *    onResume()
	 *    
	 * 销毁：（Back键回退）
	 *    onPause()
	 *    onStop()
	 *    onDestroyView()
	 *    onDestroy()
	 *    onDetach()
	 *    
	 * Activity销毁重建的时候（如屏幕旋转）会按顺序重建所有的Fragment
	 *    
	 */
	

	/**
	 * Layout初始化
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.login_register_fragment, null);
		return v;
	}
	
	/**
	 * 数据初始化，此时才能findViewById
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	
		/**
		 *  重入时用保存的数据，不再重新获取数据
		 */
		if(null != savedInstanceState){
			
			/**
			 *  销毁前是隐藏状态，重新隐藏
			 */
			if(savedInstanceState.getBoolean(TAG_HIDDEN)){
				getFragmentManager()
				.beginTransaction()
				.hide(this)
				.commit();
			}
			
			mTitle = savedInstanceState.getString(TAG_TITLE);
		}else{
			
			mTitle = this.getString(R.string.title_register);
		}
		
		/**
		 * 新建的时候在此处设置ActionBar
		 */
		setActionBar();
	}	
	
	/**
	 * 屏幕解锁和APP切换时的入口
	 */
    @Override
    public void onStart() {
    	super.onStart();
	}
	
	/**
	 * 回退显示的入口
	 */
	@Override  
    public void onHiddenChanged(boolean hidden) { 
        super.onHiddenChanged(hidden);
        
        if(!hidden){
        	/*
        	 * 回退的时候在此处设置ActionBar
        	 */
        	setActionBar();
        }
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		
		/**
		 *  需要自己记录是否隐藏，在重入的时候恢复
		 */
		if(this.isHidden()){
			savedInstanceState.putBoolean(TAG_HIDDEN, true);
		}
		
		savedInstanceState.putString(TAG_TITLE, mTitle);
	}
	

	
	/*******************************************************************************
	 * 
	 * Section.2 业务处理
	 * 
	 *******************************************************************************/
	
	/**
	 * 设置ActionBar，初始化和回退显示的时候都需要设置
	 */
	private void setActionBar(){
    	LoginActivity mActivity = (LoginActivity) getActivity();
    	mActivity.setActionbar(this, mTitle, true);
	}
	
	/**
	 * 用户注册
	 */
	public void register(){
		
	}
	
}