package com.example.a1.tmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import java.util.ArrayList;

public class PosNowActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback {

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 2;

    private Context mContext = null;
    private boolean m_bTrackingMode = true;
    String flag= "false";
    private TMapGpsManager tmapGps = null;
    private TMapView tmapView = null;
    private static String mApiKey = "b42a1814-4abc-36c2-a743-43c5f81cd73d";
    private static int mMarkerID;
    private ArrayList<TMapPoint> m_tmapPoint = new ArrayList<TMapPoint>();
    private ArrayList<String> mArrayMarkerID = new ArrayList<String>();
    private ArrayList<MapPoint> m_mapPoint = new ArrayList<MapPoint>();

    @Override
    public void onLocationChange(Location location) {
        if(m_bTrackingMode) {
            tmapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        // 권한 획득 메소드






        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.mapview);
        tmapView = new TMapView(this);
        linearLayout.addView(tmapView);
        tmapView.setSKPMapApiKey(mApiKey);


        addPoint();
        showMarkerPoint();


        tmapView.setCompassMode(true);  //  현재 보는 방향

        tmapView.setIconVisibility(true);   //  현 위치 아이콘 표시

        tmapView.setZoomLevel(15);  //  줌 레벨
        tmapView.setMapType(TMapView.MAPTYPE_STANDARD);;
        tmapView.setLanguage(TMapView.LANGUAGE_KOREAN);

        tmapGps = new TMapGpsManager(PosNowActivity.this);
        tmapGps.setMinTime(1000);
        tmapGps.setMinDistance(5);
        tmapGps.setProvider(tmapGps.NETWORK_PROVIDER);  //  연결된 인터넷으로 현 위치를 받음, 실내에 유용
        //tmapGps.setProvider(tmapGps.GPS_PROVIDER);    //  gps로 현 위치를 잡음


        tmapGps.OpenGps();




        // 화면 중심을 단말의 현 위치로 이동
        tmapView.setTrackingMode(true);
        tmapView.setSightVisible(true);

        //풍선에서 우측 버튼 클릭시 할 행동
        tmapView.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
            @Override
            public void onCalloutRightButton(TMapMarkerItem tMapMarkerItem) {
                Toast.makeText(PosNowActivity.this, "Click!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void addPoint() {
        m_mapPoint.add(new MapPoint("강남", 37.510350, 127.066847));
    }

    public void showMarkerPoint() {
        for (int i=0;i<m_mapPoint.size();i++) {
            TMapPoint point = new TMapPoint(m_mapPoint.get(i).getLat(),
                    m_mapPoint.get(i).getLon());

            TMapMarkerItem item1 = new TMapMarkerItem();

            Bitmap bitmap = null;
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.poi_dot);

            item1.setTMapPoint(point);
            item1.setName(m_mapPoint.get(i).getName());
            item1.setVisible(item1.VISIBLE);

            item1.setIcon(bitmap);

            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.poi_dot);


            // 풍선뷰 안의 항목에 글 지정
            item1.setCalloutTitle(m_mapPoint.get(i).getName());
            item1.setCalloutSubTitle("서울");
            item1.setCanShowCallout(true);
            item1.setAutoCalloutVisible(true);

            Bitmap bitmap_i = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.i_go);

            item1.setCalloutRightButtonImage(bitmap_i);

            String strId = String.format("pmarker%d", mMarkerID++);

            tmapView.addMarkerItem(strId, item1);
            mArrayMarkerID.add(strId);

        }
    }

}
