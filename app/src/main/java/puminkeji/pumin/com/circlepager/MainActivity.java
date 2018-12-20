package puminkeji.pumin.com.circlepager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import puminkeji.pumin.com.circlepager.holder.HolderCreator;
import puminkeji.pumin.com.circlepager.holder.ViewHolder;
import puminkeji.pumin.com.circlepager.utils.JsonUtils;
import puminkeji.pumin.com.circlepager.view.CircleViewPager;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {

    private CircleViewPager mViewpager;
//    private CircleViewPager mViewPager2;
    private List<DataBean> mList = new ArrayList<>();
//    private List<Integer> mListInt = new ArrayList<>();
    private List<Map<String, Object>> SliderlistMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setViewPager();
    }

    private void initView() {
        mViewpager = (CircleViewPager)findViewById(R.id.viewpager);
    }

    private void initData() {
        GetData();
    }

    private void setViewPager() {
        //  设置指示器位置
        // mViewpager.setIndicatorGravity(CircleViewPager.END);
        //  是否显示指示器
        mViewpager.isShowIndicator(true);
        //  设置图片切换时间间隔
        mViewpager.setInterval(3000);
        //  设置指示器圆点半径
         mViewpager.setIndicatorRadius(3);
        mViewpager.setIndicatorGravity(CircleViewPager.CENTER);
        //  设置页面点击事件
        mViewpager.setOnPageClickListener(new CircleViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                List<DataBean> list = mViewpager.getList();
                Toast.makeText(MainActivity.this, "点击了" + list.get(position).getDescribe(), Toast.LENGTH_SHORT).show();
            }
        });
        //  设置数据
        mViewpager.setPages(mList, new HolderCreator<ViewHolder>() {
            @Override
            public ViewHolder createViewHolder() {
                return new MyViewHolder();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewpager.stopLoop();
    }

    public void GetData() {
        String Url = "http://27.115.11.254:40011/api/index/get_index_data";
        RequestQueue mQueue = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            Map<String, String> mapStr = JsonUtils.getMapStr(response);
                            String data = mapStr.get("data");
                            Map<String, String> DataMap = JsonUtils.getMapStr(data);
                            String sliderList = DataMap.get("sliderList");
                            SliderlistMap = JsonUtils.getListMap(sliderList);
                            for (int i = 0; i < SliderlistMap.size(); i++) {
                                SliderlistMap.get(i).get("image_url");
                                mList.add(new DataBean(SliderlistMap.get(i).get("image_url").toString(),""));
                            }
                            Message message3 = new Message();
                            message3.what = 3;
                            mHandler.sendMessage(message3);//发送消息 
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("signature", "D648394AF40D5BE6675943E5CBA93C3D");
                return params;
            }
        };
        mQueue.add(jsonObjectRequest);
    }
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    mViewpager.setList(mList);
                    mViewpager.setAutoPlay(true);
                    break;
            }
        }
    };
}
