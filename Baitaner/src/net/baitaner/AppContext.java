package net.baitaner;

import java.lang.reflect.Field;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.ViewConfiguration;
import android.widget.Toast;

/**
 * ȫ��Ӧ�ó����ࣺ���ڱ���͵���ȫ��Ӧ�����ü�������������
 */
public class AppContext extends Application {
	
	@Override
	public void onCreate() {
		
		/**
		 * Hack���룬��ϵͳ��Ϊ�豸û��ʵ��˵���������ActionBar��һֱ��ʾOverflowͼ�꣨���Ͻǵ������㣩
		 */
		try {  
            ViewConfiguration config = ViewConfiguration.get(this);  
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");  
            if (menuKeyField != null) {  
                menuKeyField.setAccessible(true);  
                menuKeyField.setBoolean(config, false);  
            }  
        }  
        catch (Exception ex) {  
        }  
		
		super.onCreate();

	}
	
	/**
	 * ��������Ƿ����
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if(ni != null && ni.isConnectedOrConnecting()){
			return true;
		}else{
			Toast.makeText(this, R.string.app_network_not_connected, Toast.LENGTH_LONG).show();
			return false;
		}
	}
	
	/**
	 * ����û��Ƿ��¼
	 */
	public boolean isLogined() {

		return false;
	}
	
	/**
	 * ��ȡ��¼�û�ID
	 */
	public String getLoginUid() {

		return "";
	}
	
	/**
	 * �û�ע��
	 */
	public void Logout() {
		
	}
	
	/**
	 * ��ʼ���û���¼��Ϣ
	 */
	public void initLoginInfo() {

	}
	
}