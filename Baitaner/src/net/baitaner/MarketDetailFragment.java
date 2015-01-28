package net.baitaner;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * ��Ϣ����UI
 * 
 * ��Ҫ�Զ��������
 * Section.1 Layout��ʼ�������ݳ�ʼ��
 * Section.2 ҵ����
 * 
 */

public class MarketDetailFragment extends Fragment {

	private final static String TAG_TITLE = "title";
	private final static String TAG_HIDDEN = "isHidden";
	
	private static final String ARG_POS = "position"; 
	
	private String mTitle;

	
	
	/*******************************************************************************
	 * 
	 * Section.0 ���ղ��������
	 * 
	 *******************************************************************************/
		
	public static Fragment newInstance(int position){  
		MarketDetailFragment fragment = new MarketDetailFragment();  
        Bundle bundle = new Bundle();  
        bundle.putInt(ARG_POS, position);
        fragment.setArguments(bundle);  
        return fragment;  
	} 
	
	
	 
	/*******************************************************************************
	 * 
	 * Section.1 �������ڴ���
	 * 
	 *******************************************************************************/
	
	/**
	 * Fragment��������
	 * 
	 * ���������½�����Ļ��ת��
	 *    onAttach()
	 *    onCreate()
	 *    onCreateView()
	 *    onActivityCreated()
	 *    onStart()
	 *    onResume()
	 * 
	 * �뿪����Home�����л�����APP��
	 *    onPause()
	 *    onStop()
	 *    
	 * ���룺���л���APP��
	 * 	  onStart()
	 *    onResume()
	 *    
	 * ���٣���Back�����ˣ�
	 *    onPause()
	 *    onStop()
	 *    onDestroyView()
	 *    onDestroy()
	 *    onDetach()
	 *    
	 * Activity�����ؽ���ʱ������Ļ��ת���ᰴ˳���ؽ����е�Fragment
	 *    
	 */
	

	/**
	 * Layout��ʼ��
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.market_detail_fragment, null);
		return v;
	}
	
	/**
	 * ���ݳ�ʼ������ʱ����findViewById
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	
		/**
		 *  ����ʱ�ñ�������ݣ��������»�ȡ����
		 */
		if(null != savedInstanceState){
			
			/**
			 *  ����ǰ������״̬����������
			 */
			if(savedInstanceState.getBoolean(TAG_HIDDEN)){
				getFragmentManager()
				.beginTransaction()
				.hide(this)
				.commit();
			}
			
			mTitle = savedInstanceState.getString(TAG_TITLE);
		}else{
			//��̬���ñ���
			mTitle = this.getString(R.string.title_information) + this.getArguments().getInt(ARG_POS);
		}
		
		/**
		 * �½���ʱ���ڴ˴�����ActionBar
		 */
		setActionBar();
	}	
	
	/**
	 * ��Ļ������APP�л�ʱ�����
	 */
    @Override
    public void onStart() {
    	super.onStart();
	}
	
	/**
	 * ������ʾ�����
	 */
	@Override  
    public void onHiddenChanged(boolean hidden) { 
        super.onHiddenChanged(hidden);
        
        if(!hidden){
        	/**
        	 * ���˵�ʱ���ڴ˴�����ActionBar
        	 */
        	setActionBar();
        }
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		
		/**
		 *  ��Ҫ�Լ���¼�Ƿ����أ��������ʱ��ָ�
		 */
		if(this.isHidden()){
			savedInstanceState.putBoolean(TAG_HIDDEN, true);
		}
		
		savedInstanceState.putString(TAG_TITLE, mTitle);
	}
	

	
	/*******************************************************************************
	 * 
	 * Section.2 ҵ����
	 * 
	 *******************************************************************************/
	
	/**
	 * ����ActionBar����ʼ���ͻ�����ʾ��ʱ����Ҫ����
	 */
	private void setActionBar(){
		MarketActivity mActivity = (MarketActivity) getActivity();
    	mActivity.setActionbar(this, mTitle, true);
	}
	
	/**
	 * 
	 */
	public void login(){

	}
	
}