package net.baitaner;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * 主界面和信息列表UI
 * 
 * 需要自定义的内容
 * Section.1 Layout初始化、数据初始化
 * Section.2 业务处理
 * 
 */

public class MarketMainFragment extends Fragment {

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
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.market_main_fragment, null);
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
			
			mTitle = this.getString(R.string.app_name);
		}

		//testing
		ListView marketListView = (ListView) getActivity().findViewById(R.id.info_list);
		marketListView.setAdapter(new ListAdapter());
		marketListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				if (getActivity() == null)
					return;
				MarketActivity mActivity = (MarketActivity) getActivity();
				MarketDetailFragment fragment = (MarketDetailFragment) MarketDetailFragment.newInstance(position);
				mActivity.addFragment(R.id.content, fragment, mActivity.TAG_INFO);
			}			
		});

		
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
        	/**
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
		MarketActivity mActivity = (MarketActivity) getActivity();
    	mActivity.setActionbar(this, mTitle, false);
	}
	
	//InfoList Adapter
	private class ListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 30;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_info, null);
			}
			TextView v = (TextView) convertView.findViewById(R.id.text);
			v.setText(String.valueOf(position));

			return convertView;
		}
		
	}
	
}
