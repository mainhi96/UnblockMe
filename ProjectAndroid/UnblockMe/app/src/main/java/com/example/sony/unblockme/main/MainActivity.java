package com.example.sony.unblockme.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sony.unblockme.R;
import com.example.sony.unblockme.asset.AddLevel;
import com.example.sony.unblockme.state.GameComplete;
import com.example.sony.unblockme.state.MainMenuState;
import com.example.sony.unblockme.state.SignState;

public class MainActivity extends AppCompatActivity {
   private ImageView imageView;
    public ChessBoard chessBoard;
    private Bitmap board;
    GestureDetector gestureDetector;
    int SWIPE = 300/6;
    int VELOCITY= 100;
    private GestureDetectorCompat mDetector;
    public static int level;

    public View viewt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      imageView = (ImageView) findViewById(R.id.imv);

      new Thread(){
          @Override
          public void run() {
              while(level==0){
                  Intent intent =getIntent();
                  level= intent.getIntExtra("level",0);
                  Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                  startActivity(intent1);
                  finish();
              }
              super.run();
          }
      }.start();



        chessBoard = new ChessBoard(MainActivity.this, 6, 6, 300, 300);
        chessBoard.init(0);
        AddLevel addLevel= new AddLevel();
        if (level==1)
        addLevel.add1();
        if (level==2)
            addLevel.add2();

        board = chessBoard.drawBoard(addLevel);

        imageView.setImageBitmap(board);
        mDetector = new GestureDetectorCompat(MainActivity.this, new MyGestureListener());


    imageView.setOnTouchListener(new View.OnTouchListener() {
                                     @Override
                                     public boolean onTouch( View view, MotionEvent motionEvent) {
                                         viewt=view;
                                         mDetector.onTouchEvent(motionEvent);

                                         return true;
                                     }
                                 }
    );

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";
        private int cellWidth;
        private int cellHeight;
        private int colIndex;
        private int rowIndex;
        private int colIndex1;
        private int rowIndex1;



        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            cellWidth = imageView.getWidth() / 6;
            cellHeight = imageView.getHeight() / 6;

            colIndex = (int) (event2.getX() / cellWidth);
            rowIndex = (int) (event2.getY() / cellHeight);

            colIndex1 = (int) (event1.getX() / cellWidth);
            rowIndex1 = (int) (event1.getY() / cellHeight);
            String type= chessBoard.getBoard()[colIndex1][rowIndex1].charAt(0)+"";


         if (type.equals("v") && (colIndex!=colIndex1))    return false;
         if (type.equals("h")&&(rowIndex!=rowIndex1))    return false;

            if (type.equals("v")) {

                //đi xuống
                if (rowIndex - rowIndex1 > 0) {
                    Log.d("ABCF", "Đi xuống");

                    for (int i = rowIndex1 + 1; i <= rowIndex; i++) {

                        if (chessBoard.getBoard()[colIndex][i] != "0" && chessBoard.getBoard()[colIndex][i] != chessBoard.getBoard()[colIndex1][rowIndex1]) {
                            Log.d("ABCF", "Chan =" + i);
                            Log.d("CHECK", chessBoard.searchBlockLength(colIndex1, rowIndex1) + "");
                            rowIndex = i - chessBoard.searchBlockLength(colIndex1, rowIndex1);
                             Log.d("ABCF","GUI ="+ i+ " - " + chessBoard.searchBlockLength(colIndex,rowIndex)+" = "+rowIndex);
                            break;
                        }

                    }
                }
                //đi lên
                else if (rowIndex - rowIndex1 < 0){
                    Log.d("ABCF", "Đi lên");
                    for (int i = rowIndex1 - 1; i >= rowIndex; i--) {
                        Log.d("ABCF", colIndex + " - " + i + " : " + chessBoard.getBoard()[colIndex][i]);
                        if (chessBoard.getBoard()[colIndex][i] != "0" && chessBoard.getBoard()[colIndex][i] != chessBoard.getBoard()[colIndex1][rowIndex1]) {
                            Log.d("CHON", colIndex + " - " + i + " : " + chessBoard.getBoard()[colIndex][i]);
                            rowIndex = i + 1;
                            Log.d("GUI", colIndex + " - " + i);
                            break;
                        }
                    }
            }
            }
            if (type.equals("h")){

                //sang phai
                if (colIndex-colIndex1>0)
                    for (int i=colIndex1+1; i<=colIndex;i++){

                        if (chessBoard.getBoard()[i][rowIndex]!="0" && chessBoard.getBoard()[i][rowIndex]!=chessBoard.getBoard()[colIndex1][rowIndex1] ){
                            Log.d("ABCF","Chan =" +i);
                            Log.d("CHECK",chessBoard.searchBlockLength(colIndex1,rowIndex1)+"");
                            colIndex=i-chessBoard.searchBlockLength(colIndex1,rowIndex1);
                             Log.d("SANGPHAI","GUI ="+ i+ " - " + chessBoard.searchBlockLength(colIndex,rowIndex)+" = "+rowIndex);
                            break;
                        }

                    }
                    //sang trai

                else if (colIndex-colIndex1<0)
                    for (int i=colIndex1-1;i>=colIndex;i--){
                        Log.d("ABCF",i+" - "+rowIndex+" : "+chessBoard.getBoard()[i][rowIndex]);
                        if(chessBoard.getBoard()[i][rowIndex]!="0" &&chessBoard.getBoard()[i][rowIndex]!=chessBoard.getBoard()[colIndex1][rowIndex1] ) {
                            Log.d("CHON", i + " - " + rowIndex + " : " + chessBoard.getBoard()[i][rowIndex]);
                            colIndex = i + 1;
                            Log.d("SANGTRAI", colIndex + " - " + rowIndex);
                            break;
                        }
                    }

            }
            Log.d("FINAL",colIndex+ " - "+rowIndex);
           if(chessBoard.print(colIndex1,rowIndex1, colIndex, rowIndex, viewt)){
               Toast.makeText(MainActivity.this,"THANG ROI",Toast.LENGTH_LONG).show();
               Intent intent = new Intent(MainActivity.this, GameComplete.class);
               intent.putExtra("Level",level);
               startActivity(intent);
               level=0;

           }

            ;


           // chessBoard.drawBlock(colIndex,rowIndex);
          //  chessBoard.getCanvas().drawBitmap(chessBoard.getBmBlockVer(), new Rect(0, 0, chessBoard.getBmBlockVer().getWidth(), chessBoard.getBmBlockVer().getHeight()), new Rect(colIndex * cellWidthBM, rowIndex * cellHeightBM, (colIndex + 1) * cellWidthBM, (rowIndex + 1) * cellHeightBM), chessBoard.getPaint());

            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
            return true;
        }
    }
}
