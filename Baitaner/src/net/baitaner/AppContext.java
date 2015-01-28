package net.baitaner;

import java.lang.reflect.Field;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.ViewConfiguration;
import android.widget.Toast;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 */
public class AppContext extends Application {
	
	@Override
	public void onCreate() {
		
		/**
		 * Hack代码，让系统认为设备没有实体菜单键，这样ActionBar会一直显示Overflow图标（右上角的三个点）
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
	 * 检测网络是否可用
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
	 * 检查用户是否登录
	 */
	public boolean isLogined() {

		return false;
	}
	
	/**
	 * 获取登录用户ID
	 */
	public String getLoginUid() {

		return "";
	}
	
	/**
	 * 用户注销
	 */
	public void Logout() {
		
	}
	
	/**
	 * 初始化用户登录信息
	 */
	public void initLoginInfo() {

	}
	
}