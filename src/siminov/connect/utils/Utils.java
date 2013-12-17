package siminov.connect.utils;

import siminov.orm.resource.Resources;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

	/**
	 * Check whether device have network coverage or not.
	 * @return TRUE: If network coverage if there, FALSE: If network coverage is not there.
	 */
	public static boolean hasCoverage() {
		Context context = Resources.getInstance().getApplicationContext();
		
		final ConnectivityManager conMgr =  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
		    return true;
		} else {
		    return false;
		}
	}
}
