package puminkeji.pumin.com.circlepager.holder;

import android.content.Context;
import android.view.View;

public interface ViewHolder<T> {
    View createView(Context context, int position);
   // void onBind(Context context, int position, T data);
    /**
     * @param context context
     * @param data 实体类对象
     * @param position 当前位置
     * @param size 页面个数
     */
    void onBind(Context context, T data, int position, int size);
}
