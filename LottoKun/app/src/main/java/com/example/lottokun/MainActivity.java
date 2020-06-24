package com.example.lottokun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    View mView1, mView2, mView3; //다른 뷰
    ProgressBar pross;
    CustomDialog customDialog, customDialog2, customDialog3, customDialog4;  //고정수용 다이얼로그

    int primenum1,primenum2,primenum3,primenum4=0;

    int one,two,three=0;


    TextView valtv; //횟수모니터
    ListView Lottoitem;
    boolean th_flag, th_flag2 = true;
    boolean prime_flag = false; //프라임넘버
    boolean noclear=false;
    int number = 0; //횟수

    Button stopbtn; //멈춤
    boolean saveflag; //세이브용


    ArrayList<String> items; //세이브 넘버 자료구조
    String buffer ; //커스텀 가져오기.
    ArrayAdapter<String> Adapter;

    TextView gametv;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView1 = findViewById(R.id.View1_main);
        mView2 = findViewById(R.id.View2_game);
        mView3 = findViewById(R.id.View3_save);


        //버튼 리스너 부착
        ((Button) findViewById(R.id.btn1)).setOnClickListener(mClickListener);
        ((Button) findViewById(R.id.btn2)).setOnClickListener(mClickListener);
        ((Button) findViewById(R.id.btn3)).setOnClickListener(mClickListener);

        pross = (ProgressBar) findViewById(R.id.progressbar); //프로그래스 부착

        valtv = findViewById(R.id.ValueText);
        Lottoitem = findViewById(R.id.View3_list);

        stopbtn = (Button) findViewById(R.id.main_btn4);


        items = new ArrayList<String>();
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items);
        Lottoitem.setAdapter(Adapter);
        Lottoitem.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        saveflag = true;

        customDialog = new CustomDialog(this, positiveListener, negativeListener);
        customDialog2 = new CustomDialog(this, positiveListener1, negativeListener1);
        customDialog3 = new CustomDialog(this,positiveListener3,negativeListener3);
        customDialog4 = new CustomDialog(this,positiveListener4,negativeListener4);

        gametv=(TextView)findViewById(R.id.View2_gameTV);

    }



    public void primenumber(View view) {
        switch (view.getId()){
            case R.id.grida1:
                customDialog.show();
                break;
            case R.id.grida4:
                customDialog2.show();
                break;
            case R.id.gridaaa1:
                customDialog3.show();
                break;
            case R.id.gridaaa4:
                customDialog4.show();
                break;


        }

    }

    //View2 로또 시뮬 클릭리스너.
    public void gmaeset(View view){
        Lotto L1= new Lotto();
        int[] arr={0,0,0,0,0,0};
        TextView gametv=(TextView)findViewById(R.id.View2_gameTV);
        boolean gametriger= false;


        switch (view.getId()){
            case R.id.View2_gameran:
                L1.setLotto();
                arr=L1.arr;
                Lottoprint(L1,"grid_game",1500);

                break;
            case R.id.View2_gamechoice:
                arr=getchoice("grid_gameE");
                for(int i=0; i<6;i++){
                    if(arr[i]<=0 || arr[i]>45){
                        gametv.setText("1~45 사이에 숫자를 모든 칸에 입력하세요.");
                        return;
                    }
                }
                L1.setLotto(arr);
                Lottoprint(L1,"grid_game",1500);
                gametv.setText("커스텀 픽 완료");

                break;

            case R.id.View2_gameload:
               String test=buffer.replaceAll(" ","");  //공백제거!
               String[] arrbuf = test.split("\\|"); //특문 버그있어서 \\붙여야함.
                for(int i=0; i<6;i++){
                    arr[i]=Integer.parseInt(arrbuf[i]);
                }
                L1.setLotto(arr);
                Lottoprint(L1,"grid_game",1500);
                Toast.makeText(getApplicationContext(), "저장된 로또를 성공적으로 가져왔습니다.", Toast.LENGTH_SHORT).show();
               gametv.setText(arrbuf[0]+arrbuf[1]+arrbuf[2]+arrbuf[3]+arrbuf[4]+arrbuf[5] +"저장로또 픽 완료");

                break;

            case R.id.View2_gamestart:






                break;

            case R.id.View2_gamestop:

                break;
        }


    }

    View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            TextView primeview1=(TextView)findViewById(R.id.grida1);
                primenum1=customDialog.getPrime();
                prime_flag=true; //한정 조건검사.
                primeview1.setText(primenum1+"");
                customDialog.dismiss();
                Toast.makeText(getApplicationContext(), "고정 수 (" + primenum1+ ") 선택 완료", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener negativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "취소버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
            customDialog.dismiss();
        }
    };

    View.OnClickListener positiveListener1 = new View.OnClickListener() {
        public void onClick(View v) {
            TextView primeview2=(TextView)findViewById(R.id.grida4);
               primenum2=customDialog2.getPrime();
               prime_flag=true;
               primeview2.setText(primenum2+"");
               customDialog2.dismiss();
               Toast.makeText(getApplicationContext(), "고정 수 (" + primenum2+ ") 선택 완료", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener negativeListener1 = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "취소버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
            customDialog2.dismiss();
        }
    };


    View.OnClickListener positiveListener3 = new View.OnClickListener() {
        public void onClick(View v) {
            TextView primeview3=findViewById(R.id.gridaaa1);
            prime_flag=true;
            primenum3=customDialog3.getPrime();
            primeview3.setText(primenum3+"");
            customDialog3.dismiss();
            Toast.makeText(getApplicationContext(), "고정 수 (" + primenum3+ ") 선택 완료", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener negativeListener3 = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "취소버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
            customDialog3.dismiss();
        }
    };

    View.OnClickListener positiveListener4 = new View.OnClickListener() {
        public void onClick(View v) {
            TextView primeview4=findViewById(R.id.gridaaa4);
            prime_flag=true;
            primenum4=customDialog4.getPrime();
            primeview4.setText(primenum4+"");
            customDialog4.dismiss();
            Toast.makeText(getApplicationContext(), "고정 수 (" + primenum4+ ") 선택 완료", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener negativeListener4 = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "취소버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
            customDialog4.dismiss();
        }
    };






    Button.OnClickListener mClickListener = new Button.OnClickListener() {
        public void onClick(View v) { //페이지 변환 메소드
            switch (v.getId()) {
                case R.id.btn1:
                    ChangePage(1);
                    break;
                case R.id.btn2:
                    ChangePage(2);
                    break;
                case R.id.btn3:
                    ChangePage(3);
                    break;
            }
        }
    };


    void ChangePage(int page) {
        mView1.setVisibility(View.INVISIBLE);
        mView2.setVisibility(View.INVISIBLE);
        mView3.setVisibility(View.INVISIBLE);

        switch (page) {
            case 1:
                mView1.setVisibility(View.VISIBLE);
                break;
            case 2:
                mView2.setVisibility(View.VISIBLE);
                break;
            case 3:
                mView3.setVisibility(View.VISIBLE);
                break;

        }
    } //페이지 전환

    //View3 용 btn 리스너
    public void View3_btn(View v) {
        SparseBooleanArray sb = Lottoitem.getCheckedItemPositions();
        switch (v.getId()) {
            case R.id.View3_btn1:
                int countnum=0;
                //택하기
                int count=Adapter.getCount() ;
                for(int i=count-1; i>=0; i--){
                    if(sb.get(i)){
                        buffer=items.get(i);
                        countnum++;
                    }
                }
                Toast.makeText(getApplicationContext(), "다중선택시 제일 위 번호를 택합니다.", Toast.LENGTH_SHORT).show();
                break;




            case R.id.View3_btn2:
                //삭제

                if (sb.size() != 0) {
                    for (int i = Lottoitem.getCount() - 1; i >= 0; i--) {
                        if (sb.get(i)) {
                            items.remove(i);
                        }
                    }
                    Lottoitem.clearChoices();
                    Adapter.notifyDataSetChanged();
                }
                break;
        }

    }


    void Lottoprint(Lotto L1, String View_name, int num) {
        for (int i = 0; i < L1.arr.length; i++) {
            int resID = getResources().getIdentifier(View_name + i, "id", "com.example.lottokun"); //view name 을 기준으로
            TextView View123 = ((TextView) findViewById(resID));
            startCountAnimation(View123, L1.arr[i], num);
        }

    }//로또 프린트

    int[] getchoice(String View_name){
        int[] arr2=new int[6];
        for(int i=0; i< 6; i++) {
            int resID = getResources().getIdentifier(View_name+i, "id", "com.example.lottokun");
            EditText E0 = (EditText) findViewById(resID);
            int num = Integer.parseInt(E0.getText().toString());
            arr2[i]=num;

        }
        return arr2;
    }

    //((TextView)findViewById(resID)).setText(""+L1.arr[i]);

    private void startCountAnimation(final TextView LottoP, int i, int speed) {
        int n = 0;
        Random rnd = new Random();
        if (saveflag) { //true 일때 45
            n = rnd.nextInt(45);
        } else {
            n = rnd.nextInt(10);
        }

        ValueAnimator animator = ValueAnimator.ofInt(n, i); //0 is min number, 600 is max number
        animator.setDuration(speed); //Duration is in milliseconds
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                LottoP.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    } //애니


    public void Mainclick(View v) {
        switch (v.getId()) {
            case R.id.main_btn1:
                th_flag = true;
                saveflag = true;
                noclear=true;
                int num = 0;
                pross.setVisibility(View.VISIBLE);
                stopbtn.setVisibility(View.VISIBLE);

                CalThread Th = new CalThread();
                Thread thread = new Thread(Th);
                thread.start();

                break;

            case R.id.main_btn2:
                setLottoPrint(2000);
                saveflag = true;
                noclear=true; //클리어 플래그

                break;

            case R.id.main_btn3:
                //초기화 버튼
                stopbtn.setVisibility(View.INVISIBLE);
                if(saveflag){
                    valtv.setText("초기화 완료");
                    setLottoPrint("b");
                }

                number = 0;

            case R.id.main_btn4:
                th_flag = false;
                if (number > 1) {
                    valtv.setText((number - 1) + "회");
                }
                pross.setVisibility(View.INVISIBLE);
                break;


        }

    } //메인 클릭

    public void Save(View v) {  //세이브 리스너
        String buffer;
        if (v.getId() == R.id.save_btn1) {
            addSave("grid");
            addSave("grida");
            addSave("gridaa");
            addSave("gridaaa");
            addSave("gridaaaa");
            Toast myToast = Toast.makeText(this.getApplicationContext(), "All Save complete!!", Toast.LENGTH_SHORT);
            myToast.show();

        } else {
            PopupMenu popup = new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

            getMenuInflater().inflate(R.menu.mymenu, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.m1:
                            addSave("grid");
                            Toast.makeText(getApplication(), "One line save..", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.m2:
                            addSave("grida");
                            Toast.makeText(getApplication(), "Two line save..", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.m3:
                            addSave("gridaa");
                            Toast.makeText(getApplication(), "Three line save", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.m4:
                            addSave("gridaaa");
                            Toast.makeText(getApplication(), "Four line save..", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.m5:
                            addSave("gridaaaa");
                            Toast.makeText(getApplication(), "Five line save..", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });

            popup.show();//Popup Menu 보이기
        }


    }


    public void addSave(String name) {
        String buffer;
        buffer = allgetLottoPrint(name);
        items.add(buffer);
        Adapter.notifyDataSetChanged();

    }

    class Lotto {
        int[] arr;

        int[] getLotto() {
            return arr;
        }

        void setLotto() {
            arr = ran();
        }

        void setLotto(int arr2[]) {
            this.arr = arr2;
        }
    }

    public static int[] ran() {
        Random rn = new Random();
        int[] arr2 = new int[6];
        //System.out.print("예상 번호");
        for (int i = 0; i < 6; i++) {
            int n = rn.nextInt(45) + 1;
            arr2[i] = n;
            //System.out.printf("%d -- ",arr2[i]);
        }
        return arr2;
    } //랜덤 넘버 생성 메소드

    public static int Lottocheck(int[] arr, int[] arr2) {
        int num = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (arr[i] == arr2[j]) {
                    num++;
                }
            }
        }

        return num;
    }  //잠시 구현, 로또 비교

    public void setLottoPrint(int num) {
        Lotto L1 = new Lotto();
        L1.setLotto();
        Lottoprint(L1, "grid", num); //1번


        L1.setLotto();
        if(prime_flag){
            if(primenum1>0){
                L1.arr[1]=primenum1;
            }
            if(primenum2>0){
                L1.arr[4]=primenum2;
            }
        }

        Lottoprint(L1, "grida", num); //2번

        L1.setLotto();
        Lottoprint(L1, "gridaa", num); //3번

        L1.setLotto();


        if(prime_flag){
            if(primenum3>0){
                L1.arr[1]=primenum3;
            }
            if(primenum4>0){
                L1.arr[4]=primenum4;
            }
        }

        Lottoprint(L1, "gridaaa", num); //4번

        L1.setLotto();
        Lottoprint(L1, "gridaaaa", num); //5번


    }


    public void setLottoPrint(String A) {
        Lotto L1 = new Lotto();
        int[] arr2 = {0, 0, 0, 0, 0, 0};
        if(noclear){
            L1.arr=arr2;
            saveflag = false; //초기화할때는 애니메이션 안이쁘게
            primenum1=0;
            primenum2=0;
            primenum3=0;
            primenum4=0;
            Lottoprint(L1, "grid", 2000); //1번
            Lottoprint(L1, "grida", 2000); //2번
            Lottoprint(L1, "gridaa", 2000); //3번
            Lottoprint(L1, "gridaaa", 2000); //4번
            Lottoprint(L1, "gridaaaa", 2000); //4번
            noclear=false;
        }


        } //초기화용 오버로딩

    public String allgetLottoPrint(String View_name) {
        StringBuilder B1 = new StringBuilder();
        String Lottoitem;
        String buffer;
        for (int i = 0; i < 6; i++) {
            int resID = getResources().getIdentifier(View_name + i, "id", "com.example.lottokun"); //view name 을 기준으로
            TextView View123 = ((TextView) findViewById(resID));
            buffer = View123.getText().toString().trim();
            if ((Integer.parseInt(buffer)) < 10) {
                B1.append("  " + buffer + " | ");
            } else {
                B1.append(buffer + " | ");
            }

        }

        Lottoitem = B1.toString();
        return Lottoitem;

    }



    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            setLottoPrint(1000);
            TextView value = (TextView) findViewById(R.id.ValueText);
            value.setText(number + " 회 반복중입니다. ");
            number++;
        }
    };


    class CalThread implements Runnable {
        public void run() {

            while (th_flag) {
                ran();
                Message msg = mHandler.obtainMessage();
                mHandler.sendMessage(msg);
                try {
                    Thread.sleep(600);
                } catch (Exception e) {

                }


            }


        }

    }

}



