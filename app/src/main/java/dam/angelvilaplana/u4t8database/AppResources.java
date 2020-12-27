package dam.angelvilaplana.u4t8database;

import android.app.Application;
import android.content.res.Resources;

/**
 * Obtain the resources of our project to work
 * with application resources
 */
public class AppResources extends Application {

    private static Resources resources;

    @Override
    public void onCreate() {
        super.onCreate();
        resources = getResources();
    }

    public static Resources getRes(){
        return resources;
    }

    public static String getPriority(int position) {
        return resources.getStringArray(R.array.priority)[position];
    }

    public static String getStatus(int position) {
        return resources.getStringArray(R.array.status)[position];
    }

}
