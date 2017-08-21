package com.example.a1.tmap;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback {

    private Context mContext = null;
    private boolean m_bTrackingMode = true;

    private TMapData tMapData = null;
    private TMapGpsManager tmapGps = null;
    private TMapView tmapView = null;
    private static String mApiKey = "b42a1814-4abc-36c2-a743-43c5f81cd73d";
    private static int mMarkerID;

    private ArrayList<TMapPoint> m_tmapPoint = new ArrayList<TMapPoint>();
    private ArrayList<String> mArrayMarkerID = new ArrayList<String>();
    private ArrayList<MapPoint> m_mapPoint = new ArrayList<MapPoint>();

    private String address;
    private Double lat = null;
    private Double lon = null;

    private Button bt_find;
    private Button bt_fac;

    @Override
    public void onLocationChange(Location location) {
        if(m_bTrackingMode) {
            tmapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchact);

        mContext = this;

        bt_find = (Button)findViewById(R.id.bt_findadd);
        bt_fac = (Button)findViewById(R.id.bt_findfac);

        tMapData = new TMapData();
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

        tmapGps = new TMapGpsManager(MainActivity.this);
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
            public void onCalloutRightButton(TMapMarkerItem markerItem) {

                lat = markerItem.latitude;
                lon = markerItem.longitude;

                // 위도 경도로 주소 검색하기
                tMapData.convertGpsToAddress(lat, lon, new TMapData.ConvertGPSToAddressListenerCallback() {
                    @Override
                    public void onConvertToGPSToAddress(String strAddress) {
                        address = strAddress;
                    }
                });
                Toast.makeText(MainActivity.this, "주소 : "+ address, Toast.LENGTH_SHORT).show();
            }
        });

        bt_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertToAddress();
            }
        });

        bt_fac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAroundBizPoi();
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

    /*
    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.bt_findadd:
                convertToAddress();
                break;
            case R.id.bt_findfac:
                getAroundBizPoi();
                break;
        }
    }
    */

    // 주소검색으로 위도 경도 검색하기
    // 명칭 검색을 통한 주소 변환

    public void convertToAddress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("POI 통합 검색");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                final String strData = input.getText().toString();
                TMapData tMapData = new TMapData();

                tMapData.findAllPOI(strData, new TMapData.FindAllPOIListenerCallback() {
                    @Override
                    public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {
                        for(int i=0;i<poiItem.size();i++){
                            TMapPOIItem item = poiItem.get(i);

                            Log.d("주소로 찾기", "POI Name : "+ item.getPOIName().toString() + ", " +
                            "Address : "+ item.getPOIAddress().replace("null", "") + ", " +
                            "Point : "+ item.getPOIPoint().toString());
                        }
                    }
        });
    }
});

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
    });

        builder.show();

}

    //  주변 편의시설 검색
    //  화면 중심의 위도 경도를 통한 검색
    public void getAroundBizPoi() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tmapView.getCenterPoint();

        tMapData.findAroundNamePOI(point, "편의점;은행", 1, 99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItem) {
                        for(int i=0;i<poiItem.size();i++) {
                            TMapPOIItem item = poiItem.get(i);
                            Log.d("편의시설", "POI Name : "+ item.getPOIName() + ", " + "Address : " +
                            item.getPOIAddress().replace("null", ""));
                        }
                    }
                });
    }
}
