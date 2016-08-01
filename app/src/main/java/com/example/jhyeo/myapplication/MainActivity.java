package com.example.jhyeo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    Marker selectedMarker;
    View marker_root_view;
    TextView tv_marker;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(),ScrollingActivity.class);
                startActivity(intent);
            }
        });

        /*
        xml관련 구동부분
         */
        String addr = getString(R.string.api_url);
        Log.d("MYMSG",addr);
        ConnectThread thread = new ConnectThread(addr);
        thread.execute();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_histories) {
            Intent intent = new Intent(getApplicationContext(),HistoriesActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*
        // Add a marker in Sydney and move the camera
        LatLng seoul = new LatLng(37.5609615,126.9763382);
        mMap.addMarker(new MarkerOptions().position(seoul).title("서울특별시청"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
        */

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.537523, 126.96558), 14));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

        setCustomMarkerView();
        getSampleMarkerItems();
    }

    private void setCustomMarkerView() {

        marker_root_view = LayoutInflater.from(this).inflate(R.layout.marker_layout, null);
        tv_marker = (TextView) marker_root_view.findViewById(R.id.tv_marker);
    }

    private void getSampleMarkerItems() {
        ArrayList<MarkerItem> sampleList = new ArrayList();


        sampleList.add(new MarkerItem(37.4632016047, 126.9345984302, "서울신림동민속순대타운"));
        sampleList.add(new MarkerItem(37.5611107256, 126.9465464425, "이대/구제거리"));
        sampleList.add(new MarkerItem(37.5744028289, 126.9873802368, "피맛골"));
        sampleList.add(new MarkerItem(37.5385603718, 127.1470468799, "병천토속순대"));
        sampleList.add(new MarkerItem(37.5510497104, 127.0824623102, "능동로"));
        sampleList.add(new MarkerItem(37.5365973016, 126.8758936853, "축제의거리"));
        sampleList.add(new MarkerItem(37.4787998297, 126.9940983899, "방배동/카페골목"));
        sampleList.add(new MarkerItem(37.568058597, 126.9788194313, "명동거리"));
        sampleList.add(new MarkerItem(37.592642472, 126.9217039604, "응암동/감자탕골목"));
        sampleList.add(new MarkerItem(37.5536935109, 127.0612932517, "장안평자동차/용품거리"));
        sampleList.add(new MarkerItem(37.5771315908, 127.0054112549, "대학로/카페거리"));
        sampleList.add(new MarkerItem(37.515081363, 127.0090625635, "신사동/간장게장골목"));
        sampleList.add(new MarkerItem(37.5611107256, 126.9465464425, "이대앞/사주카페거리"));
        sampleList.add(new MarkerItem(37.5580500713, 126.9774324475, "남대문로/낚시용품거리"));
        sampleList.add(new MarkerItem(37.4787998297, 126.9940983899, "방배역/먹자골목"));
        sampleList.add(new MarkerItem(37.4829408135, 126.948985568, "봉천동/먹자골목"));
        sampleList.add(new MarkerItem(37.568058597, 126.9788194313, "무교동음식/문화의거리"));
        sampleList.add(new MarkerItem(37.5670083299, 127.040689845, "30년전통/마장동먹자골목"));
        sampleList.add(new MarkerItem(37.5828099517, 126.9803912024, "삼청동/문화거리"));
        sampleList.add(new MarkerItem(37.5714984223, 126.8725938165, "메트로/폴리스길"));
        sampleList.add(new MarkerItem(37.5431790738, 127.1266790476, "천호문구/완구거리"));
        sampleList.add(new MarkerItem(37.5637990488, 126.9545535618, "북아현동/가구거리"));
        sampleList.add(new MarkerItem(37.6192011241, 126.9353908377, "불광동/먹자골목"));
        sampleList.add(new MarkerItem(37.5365973016, 126.8758936853, "바람의거리"));
        sampleList.add(new MarkerItem(37.6370942943, 127.0045998616, "순국선열묘역/순례길"));
        sampleList.add(new MarkerItem(37.516577197, 126.8629865421, "어울림의거리"));
        sampleList.add(new MarkerItem(37.6178956805, 126.957402192, "북악산길/산책로"));
        sampleList.add(new MarkerItem(37.5896729314, 126.9684554703, "북악산길/산책로"));
        sampleList.add(new MarkerItem(37.5773373025, 126.8133225984, "한강자전거도로(강서지구)"));
        sampleList.add(new MarkerItem(37.5389940121, 126.991703953, "이태원거리"));
        sampleList.add(new MarkerItem(37.5641304492, 126.9799342986, "정동길"));
        sampleList.add(new MarkerItem(37.5888316341, 127.0625992198, "경희대파전골목"));
        sampleList.add(new MarkerItem(37.578389933, 126.9726262878, "경복궁뒷길"));
        sampleList.add(new MarkerItem(37.5828099517, 126.9803912024, "별궁길"));
        sampleList.add(new MarkerItem(37.5136359848, 127.0311957909, "논현동포차골목"));
        sampleList.add(new MarkerItem(37.5819896661, 126.9929898185, "필리핀거리"));
        sampleList.add(new MarkerItem(37.5546022525, 126.9560892337, "공덕동족발골목"));
        sampleList.add(new MarkerItem(37.5431790738, 127.1266790476, "천호동/로데오거리"));
        sampleList.add(new MarkerItem(37.5353973609, 127.0059286959, "꼼데가르송길"));
        sampleList.add(new MarkerItem(37.5736899154, 127.0001901776, "동대문/생선구이골목"));
        sampleList.add(new MarkerItem(37.4967500548, 126.9012770519, "대림동/차이나타운"));
        sampleList.add(new MarkerItem(37.482833211, 126.8873071257, "가리봉동/차이나타운"));
        sampleList.add(new MarkerItem(37.5319212362, 127.0802146943, "자양동/차이나타운"));
        sampleList.add(new MarkerItem(37.568610138, 127.0204336493, "중앙시장/가구단지"));
        sampleList.add(new MarkerItem(37.5744028289, 126.9873802368, "인사동문화마당"));
        sampleList.add(new MarkerItem(37.5542409105, 126.9379276799, "홍대예술의거리"));
        sampleList.add(new MarkerItem(37.5611107256, 126.9465464425, "신촌이대거리"));
        sampleList.add(new MarkerItem(37.582612384, 126.9848066184, "계동길"));
        sampleList.add(new MarkerItem(37.4829408135, 126.948985568, "관악디자인거리"));
        sampleList.add(new MarkerItem(37.5433219294, 127.0727360464, "건대맛의거리"));
        sampleList.add(new MarkerItem(37.6305927195, 127.087693357, "화랑로낙엽거리"));
        sampleList.add(new MarkerItem(37.5536935109, 127.0612932517, "로데오거리"));
        sampleList.add(new MarkerItem(37.5365973016, 126.8758936853, "청소년/문화거리"));
        sampleList.add(new MarkerItem(37.5365973016, 126.8758936853, "평화의거리"));
        sampleList.add(new MarkerItem(37.5046949142, 127.0019000246, "허밍웨이"));
        sampleList.add(new MarkerItem(37.5353973609, 127.0059286959, "이태원솔마루"));
        sampleList.add(new MarkerItem(37.5046949142, 127.0019000246, "서래마을카페거리"));
        sampleList.add(new MarkerItem(37.5828099517, 126.9803912024, "삼청동길"));
        sampleList.add(new MarkerItem(37.5771315908, 127.0054112549, "이화마을"));
        sampleList.add(new MarkerItem(37.5546022525, 126.9560892337, "아현동전골목"));
        sampleList.add(new MarkerItem(37.5232030914, 126.8886175026, "안양천제방/벚꽃길"));
        sampleList.add(new MarkerItem(37.5365973016, 126.8758936853, "배움의거리"));
        sampleList.add(new MarkerItem(37.5304833863, 127.128897657, "천호디자인거리"));
        sampleList.add(new MarkerItem(37.5238250318, 127.029231648, "가로수길"));
        sampleList.add(new MarkerItem(37.5923458269, 126.9640836947, "북악산길/산책로"));
        sampleList.add(new MarkerItem(37.4885117081, 127.0164271437, "강남역/먹자골목"));
        sampleList.add(new MarkerItem(37.6168942335, 126.9973948722, "둘레길1구간"));
        sampleList.add(new MarkerItem(37.516577197, 126.8629865421, "목동/로데오거리"));
        sampleList.add(new MarkerItem(37.5641304492, 126.9799342986, "돌담길"));
        sampleList.add(new MarkerItem(37.475038316, 126.8842347738, "가리봉/로데오거리"));
        sampleList.add(new MarkerItem(37.4829408135, 126.948985568, "봉천동/차이나타운"));
        sampleList.add(new MarkerItem(37.6178956805, 126.957402192, "둘레길3구간"));
        sampleList.add(new MarkerItem(37.5319212362, 127.0802146943, "건대/로데오거리"));
        sampleList.add(new MarkerItem(37.5353973609, 127.0059286959, "해맞이길"));
        sampleList.add(new MarkerItem(37.5613498068, 126.9959254377, "충무로애견거리"));
        sampleList.add(new MarkerItem(37.4998663454, 127.0388891889, "테헤란로"));
        sampleList.add(new MarkerItem(37.5828099517, 126.9803912024, "사간동갤러리골"));
        sampleList.add(new MarkerItem(37.557255013, 127.013160116, "신당동/떡볶이골목"));
        sampleList.add(new MarkerItem(37.5163186648, 127.126461696, "방이동모텔촌"));
        sampleList.add(new MarkerItem(37.5771315908, 127.0054112549, "대학로거리"));
        sampleList.add(new MarkerItem(37.475038316, 126.8842347738, "먹거리촌"));
        sampleList.add(new MarkerItem(37.5613498068, 126.9959254377, "인현시장/먹거리"));
        sampleList.add(new MarkerItem(37.5971607572, 127.0207811637, "옛집순례길"));
        sampleList.add(new MarkerItem(37.568058597, 126.9788194313, "콴챈루거리"));
        sampleList.add(new MarkerItem(37.5136359848, 127.0311957909, "신의주찹쌀순대"));
        sampleList.add(new MarkerItem(37.5714984223, 126.8725938165, "메타세콰이어길"));
        sampleList.add(new MarkerItem(37.5664098491, 126.9976456492, "페인트도배/전문거리"));
        sampleList.add(new MarkerItem(37.5039643623, 127.1040253187, "석촌호수길"));
        sampleList.add(new MarkerItem(37.4854043468, 127.1221039568, "문정동/로데오거리"));
        sampleList.add(new MarkerItem(37.5238250318, 127.029231648, "압구정/카페골목"));
        sampleList.add(new MarkerItem(37.4632016047, 126.9345984302, "걷고싶은/문화의거리"));
        sampleList.add(new MarkerItem(37.5319454062, 126.9730641438, "용산/감자탕거리"));
        sampleList.add(new MarkerItem(37.5613498068, 126.9959254377, "중앙아시아촌"));
        sampleList.add(new MarkerItem(37.5389940121, 126.991703953, "경리단길"));
        sampleList.add(new MarkerItem(37.568058597, 126.9788194313, "을지한빛거리"));
        sampleList.add(new MarkerItem(37.5254064028, 127.0523782445, "청담동/명품거리"));
        sampleList.add(new MarkerItem(37.6386797307, 126.9340959558, "둘레길3구간"));
        sampleList.add(new MarkerItem(37.5283125747, 126.9294280442, "여의서로"));
        sampleList.add(new MarkerItem(37.5579256806, 127.1308327064, "한강자전거도로(광나루지구)"));
        sampleList.add(new MarkerItem(37.515081363, 127.0090625635, "신사동/아구찜골목"));
        sampleList.add(new MarkerItem(37.5744028289, 126.9873802368, "낙원동아구찜거리"));
        sampleList.add(new MarkerItem(37.5006879112, 126.9736046405, "현충로"));
        sampleList.add(new MarkerItem(37.5613859155, 127.0005749663, "장충단길"));
        sampleList.add(new MarkerItem(37.5881976109, 127.0463896964, "홍릉길"));
        sampleList.add(new MarkerItem(37.5744028289, 126.9873802368, "종로/귀금속거리"));
        sampleList.add(new MarkerItem(37.5238250318, 127.029231648, "압구정/로데오거리"));
        sampleList.add(new MarkerItem(37.5664098491, 126.9976456492, "초동길"));
        sampleList.add(new MarkerItem(37.568610138, 127.0204336493, "황학동/도깨비시장"));
        sampleList.add(new MarkerItem(37.5896729314, 126.9684554703, "청와대앞길"));
        sampleList.add(new MarkerItem(37.6370942943, 127.0045998616, "우이동/먹거리마을"));
        sampleList.add(new MarkerItem(37.516577197, 126.8629865421, "사랑의거리"));
        sampleList.add(new MarkerItem(37.59692086, 126.9921504705, "성북동길"));
        sampleList.add(new MarkerItem(37.5580500713, 126.9774324475, "남대문갈치조림골목"));
        sampleList.add(new MarkerItem(37.5365973016, 126.8758936853, "꽃향기와/새소리의거리"));
        sampleList.add(new MarkerItem(37.6370942943, 127.0045998616, "둘레길1구간"));
        sampleList.add(new MarkerItem(37.5613498068, 126.9959254377, "청계천/헌책방거리"));
        sampleList.add(new MarkerItem(37.5059772708, 126.9115342551, "신길동/홍어거리"));
        sampleList.add(new MarkerItem(37.6209706338, 126.9129160256, "연신내/로데오거리"));
        sampleList.add(new MarkerItem(37.5365973016, 126.8758936853, "빛과통신의/거리"));
        sampleList.add(new MarkerItem(37.5714984223, 126.8725938165, "웰빙황토길"));
        sampleList.add(new MarkerItem(37.6178956805, 126.957402192, "북악산길/산책로"));
        sampleList.add(new MarkerItem(37.5613498068, 126.9959254377, "남산공원북측/순환도로"));
        sampleList.add(new MarkerItem(37.5631083128, 126.9223460779, "연남동/차이나타운"));
        sampleList.add(new MarkerItem(37.5611107256, 126.9465464425, "신촌명물순대"));
        sampleList.add(new MarkerItem(37.5486842731, 127.1496921479, "당리순대국"));
        sampleList.add(new MarkerItem(37.52168028, 126.8984840296, "신의주찹쌀순대/영등포구청역점"));
        sampleList.add(new MarkerItem(37.5403675217, 127.0581593864, "김가네순대국"));
        sampleList.add(new MarkerItem(37.6178956805, 126.957402192, "평창동화랑가"));
        sampleList.add(new MarkerItem(37.5637990488, 126.9545535618, "아현동/웨딩거리"));
        sampleList.add(new MarkerItem(37.5971607572, 127.0207811637, "장수길"));
        sampleList.add(new MarkerItem(37.59692086, 126.9921504705, "둘레길2구간"));
        sampleList.add(new MarkerItem(37.6370942943, 127.0045998616, "둘레길/순례길구간"));
        sampleList.add(new MarkerItem(37.516577197, 126.8629865421, "고향의거리"));
        sampleList.add(new MarkerItem(37.568610138, 127.0204336493, "왕십리곱창골목"));

        for (MarkerItem markerItem : sampleList) {
            addMarker(markerItem, false);
        }

    }



    private Marker addMarker(MarkerItem markerItem, boolean isSelectedMarker) {


        LatLng position = new LatLng(markerItem.getLat(), markerItem.getLon());
        String title = markerItem.getTitle();

        tv_marker.setText(title);

        if (isSelectedMarker) {
            tv_marker.setBackgroundResource(R.drawable.ic_marker_phone_blue);
            tv_marker.setTextColor(Color.WHITE);
        } else {
            tv_marker.setBackgroundResource(R.drawable.ic_marker_phone);
            tv_marker.setTextColor(Color.BLACK);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(title);
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));


        return mMap.addMarker(markerOptions);

    }




    // View를 Bitmap으로 변환
    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        String title = marker.getTitle();
        MarkerItem temp = new MarkerItem(lat, lon, title);
        return addMarker(temp, isSelectedMarker);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(center);

        changeSelectedMarker(marker);

        return true;
    }

    private void changeSelectedMarker(Marker marker) {
        // 선택했던 마커 되돌리기
        if (selectedMarker != null) {
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }

        // 선택한 마커 표시
        if (marker != null) {
            selectedMarker = addMarker(marker, true);
            marker.remove();
        }


    }

    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
    }
}
