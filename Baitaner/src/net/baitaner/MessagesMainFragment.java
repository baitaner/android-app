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
 * �Ự�б�UI
 * 
 * ��Ҫ�Զ��������
 * Section.1 Layout��ʼ�������ݳ�ʼ��
 * Section.2 ҵ����
 * 
 */

public class MessagesMainFragment extends Fragment {

	private final static String TAG_TITLE = "title";
	private final static String TAG_HIDDEN = "isHidden";
	
	private String mTitle;

	
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
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.messages_main_fragment, null);
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
			
			mTitle = this.getString(R.string.title_messages);
		}

		//testing
		ListView sessionListView = (ListView) getActivity().findViewById(R.id.info_list);
		sessionListView.setAdapter(new ListAdapter());
		sessionListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				if (getActivity() == null)
					return;
				MessagesActivity mActivity = (MessagesActivity) getActivity();
				MessagesDetailFragment fragment = (MessagesDetailFragment) MessagesDetailFragment.newInstance(position);
				mActivity.addFragment(R.id.content, fragment, mActivity.TAG_CHAT);
			}			
		});

		
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
		MessagesActivity mActivity = (MessagesActivity) getActivity();
    	mActivity.setActionbar(this, mTitle, true);
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
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_session, null);
			}
			TextView v = (TextView) convertView.findViewById(R.id.text);
			v.setText(String.valueOf(position));

			return convertView;
		}
		
	}
	
}