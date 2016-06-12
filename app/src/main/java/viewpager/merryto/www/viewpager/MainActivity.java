package viewpager.merryto.www.viewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<ImageView> mListData;
    private LinearLayout ll;
    private int[] imgId;
    private String[] strTitle;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initEvent() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % mListData.size();
                int childCount = ll.getChildCount();
                for(int i=0;i<childCount;i++) {
                    ImageView iv = (ImageView) ll.getChildAt(i);
                    if(i == position) {
                        iv.setBackgroundResource(R.drawable.point_select);
                    }else {
                        iv.setBackgroundResource(R.drawable.point_normal);
                    }
                }
                tv.setText(strTitle[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        //需要展示的图片
        imgId = new int[]{R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};

        strTitle = new String[]{"我是标题0","我是标题1","我是标题2","我是标题3","我是标题4"};

        //将图片添加到数据源中
        for(int i = 0; i< imgId.length; i++) {
            ImageView iv = new ImageView(MainActivity.this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(imgId[i]);
            mListData.add(iv);
            //动态添加point
            ImageView point = new ImageView(MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(12,12);
            point.setBackgroundResource(R.drawable.point_normal);
            if (i != 0) {
                params.leftMargin = 10;
            } else {
                point.setBackgroundResource(R.drawable.point_select);

                tv.setText(strTitle[0]);
            }
            ll.addView(point,params);
        }
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tv = (TextView) findViewById(R.id.tv);
        tv.setTextSize(18);
        mListData = new ArrayList<ImageView>();
        ll = (LinearLayout) findViewById(R.id.ll);
        initData();
        MyAdapter adapter = new MyAdapter();
        viewPager.setAdapter(adapter);

        // 设置默认选中中间的item
        int middle = Integer.MAX_VALUE / 2;
        int extra = middle % mListData.size();
        int item = middle - extra;
        viewPager.setCurrentItem(item);
    }
    //适配器
    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if(mListData != null) {
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //实例化
            position = position % mListData.size();
            ImageView imageView = mListData.get(position);
            viewPager.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            position = position % mListData.size();
            viewPager.removeView(mListData.get(position));
        }
    }
}
