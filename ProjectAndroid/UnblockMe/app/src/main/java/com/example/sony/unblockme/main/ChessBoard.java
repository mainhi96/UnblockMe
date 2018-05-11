package com.example.sony.unblockme.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.example.sony.unblockme.R;
import com.example.sony.unblockme.asset.AddLevel;
import com.example.sony.unblockme.model.Block;

import com.example.sony.unblockme.state.MainMenuState;
import com.example.sony.unblockme.utility.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SONY on 5/7/2018.
 */

public class ChessBoard {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;

    private String[][] board;

    private Context context;


    private int bitmapWidth;
    private int bitmapHeight;

    private List<Line> listlines;
    private int colQty;
    private int rowQty;

    private Bitmap bmBlockVer;
    private Bitmap bmErase;
    private Bitmap bmBlockHor;
    private Bitmap bmBackground;
    private Bitmap bmRed;
    private ArrayList<Block> listBlock= new ArrayList();
    private boolean win=false;

    public ChessBoard(Context context, int colQty, int rowQty, int bitmapWidth, int bitmapHeight) {
        this.context = context;
        this.colQty = colQty;
        this.rowQty = rowQty;
        this.bitmapWidth = bitmapWidth - (bitmapWidth/colQty);
        this.bitmapHeight = bitmapHeight - (bitmapHeight/colQty);
    }

    public void init(int player) {
        listlines = new ArrayList<>();
        bitmap = Bitmap.createBitmap(bitmapWidth , bitmapHeight , Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        paint = new Paint();
        paint.setStrokeWidth(0);
        paint.setColor(Color.WHITE);
        board = new String[rowQty][colQty];  //dong/cot


        for (int i = 0; i < rowQty; i++) {
            for (int j = 0; j < colQty; j++)
                board[i][j] = "0";
        }


        int cellWidth = bitmapWidth / colQty;
        int cellHeight = bitmapHeight / rowQty;


        for (int i = 0; i <= colQty; i++) {
            listlines.add(new Line(i * cellWidth, 0, i * cellWidth, bitmapHeight));
        }
        for (int j = 0; j <= rowQty; j++) {
            listlines.add(new Line(0, j * cellHeight, bitmapWidth, j * cellHeight));
        }

        //bmCross = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
        bmBlockVer = BitmapFactory.decodeResource(context.getResources(), R.drawable.blockver);
        bmErase = BitmapFactory.decodeResource(context.getResources(), R.drawable.erase);
        bmBlockHor = BitmapFactory.decodeResource(context.getResources(), R.drawable.blockhor);
        bmBlockHor = BitmapFactory.decodeResource(context.getResources(), R.drawable.blockhor);
        bmBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.background2);
         bmRed = BitmapFactory.decodeResource(context.getResources(), R.drawable.red);
    }

    public int getBitmapWidth() {
        return bitmapWidth;
    }

    public int getBitmapHeight() {
        return bitmapHeight;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Bitmap getBmBlockVer() {
        return bmBlockVer;
    }

    public String[][] getBoard() {
        return board;
    }

    public boolean isWin() {
        return win;
    }

    public Bitmap drawBoard(AddLevel level) {

        Line temp;
    //    canvas.drawBitmap(bmBackground, new Rect(0, 0, bmBackground.getWidth(), bmBackground.getHeight()), new Rect(0, 0, bitmapWidth-((bitmapWidth/colQty/2)) , bitmapHeight), paint);
   //    canvas.drawBitmap(bmGoal, new Rect(0, 0, bmGoal.getWidth(), bmGoal.getHeight()), new Rect(bitmapWidth-(bitmapWidth/colQty), 0, bitmapWidth , bitmapHeight), paint);
          /*  for (int i = 0; i < listlines.size(); i++) {
            temp = listlines.get(i);
            canvas.drawLine(temp.getStartX(), temp.getStarY(), temp.getStopX(), temp.getStopY(), paint);
        }*/
        int cellWidthBM = bitmapWidth / colQty;
        int cellHeightBM = bitmapHeight / rowQty;
        int colIndex=0;
        int rowIndex=0;
        //canvas.drawBitmap(bmBlockVer, new Rect(0, 0, bmBlockVer.getWidth(), bmBlockVer.getHeight()), new Rect(0,0, (colIndex + 2) * cellWidthBM, (rowIndex+1) * cellHeightBM), paint);

        listBlock= level.getArrayBlock();
        Block block;

        for (int i=0;i<listBlock.size();i++){
            block=listBlock.get(i);
            drawBlock(block);


        }
       //
        return this.bitmap;

    }
    public void drawBlock(Block block ){
        int cellWidthBM = bitmapWidth / colQty;
        int cellHeightBM = bitmapHeight / rowQty;
        int colIndex=block.getStartX();
        int rowIndex=block.getStartY();
        String id= block.getId().charAt(0)+"";



        if (id.equals("v"))

        {
            for (int i=0; i<block.getLength();i++)
            {
                board[colIndex][rowIndex+i]=block.getId();
            }
            canvas.drawBitmap(bmBlockVer, new Rect(0, 0, bmBlockVer.getWidth(), bmBlockVer.getHeight()), new Rect((colIndex * cellWidthBM), rowIndex * cellHeightBM, (colIndex +1) * cellWidthBM, (rowIndex + block.getLength()) * cellHeightBM), paint);

        }
        else{

            {
                if (block.getId().equals("hm"))
                    canvas.drawBitmap(bmRed, new Rect(0, 0, bmRed.getWidth(), bmRed.getHeight()), new Rect((colIndex * cellWidthBM), rowIndex * cellHeightBM, (colIndex +block.getLength()) * cellWidthBM, (rowIndex + 1) * cellHeightBM), paint);
               else canvas.drawBitmap(bmBlockHor, new Rect(0, 0, bmBlockHor.getWidth(), bmBlockHor.getHeight()), new Rect((colIndex * cellWidthBM), rowIndex * cellHeightBM, (colIndex +block.getLength()) * cellWidthBM, (rowIndex + 1) * cellHeightBM), paint);
                for (int i=0; i<block.getLength();i++)
                {
                    board[colIndex+i][rowIndex]=block.getId();
                }
            }
        }

        //canvas.drawBitmap(bmErase, new Rect(0, 0, bmBlockVer.getWidth(), bmBlockVer.getHeight()), new Rect((blockver.getStartX()) * cellWidthBM, (blockver.getStartY()) * cellHeightBM, (blockver.getStartX() + 1) * cellWidthBM, (blockver.getStartY() + 1) * cellHeightBM), paint);



    }

    public void eraseBlock(Block block){
        int cellWidthBM = bitmapWidth / colQty;
        int cellHeightBM = bitmapHeight / rowQty;
        int colIndex=block.getStartX();
        int rowIndex=block.getStartY();
        String id= block.getId().charAt(0)+"";

        if (id.equals("v"))
        {

            canvas.drawBitmap(bmErase, new Rect(0, 0, bmErase.getWidth(), bmErase.getHeight()), new Rect((colIndex * cellWidthBM), rowIndex * cellHeightBM, (colIndex +1) * cellWidthBM, (rowIndex + block.getLength()) * cellHeightBM), paint);
            for (int i=0; i<block.getLength();i++)
            {
                board[colIndex][rowIndex+i]="0";
            }
        }
        else{

            {
                canvas.drawBitmap(bmErase, new Rect(0, 0, bmErase.getWidth(), bmErase.getHeight()), new Rect((colIndex * cellWidthBM), rowIndex * cellHeightBM, (colIndex +block.getLength()) * cellWidthBM, (rowIndex + 1) * cellHeightBM), paint);
                for (int i=0; i<block.getLength();i++)
                {
                    board[colIndex+i][rowIndex]="0";
                }
            }
        }

    }
    public boolean print(int colIndex1, int rowIndex1, int colIndex, int rowIndex, View v) {


        //  Toast.makeText(context, v.getX() + "- " + event.getY(),Toast.LENGTH_LONG ).show();
        int cellWidth = v.getWidth() / colQty;
        int cellHeight = v.getHeight() / rowQty;

        int cellWidthBM = bitmapWidth / colQty;
        int cellHeightBM = bitmapHeight / rowQty;
        Boolean win=false;

        Block block= new Block();
        Log.d("BAT DAU",colIndex1+" - "+rowIndex1);
        Log.d("DICH",colIndex+" - "+rowIndex);


        String id="null";

      id= board[colIndex1][rowIndex1];

        if(id=="0") return false;


        for (Block blocks: listBlock){
            if (blocks.getId()==id){
                block=blocks;
                eraseBlock(block);

                String type= blocks.getId().charAt(0)+"";


                if (type.equals("v")&&(rowIndex+blocks.getLength()>=rowQty))  rowIndex=rowQty-blocks.getLength();
                if (type.equals("h")&&(colIndex+blocks.getLength()>=colQty))  colIndex=colQty-blocks.getLength();
                int change=0;
                for (int i=0; i<blocks.getLength();i++){

                    if (type.equals("v")){
                        if (board[colIndex][rowIndex+i]!="0") change =i;
                    }
                    else  if (type.equals("h")){
                        if (board[colIndex+i][rowIndex]!="0") change =i;
                    }
                }
                if (type.equals("v")) rowIndex=rowIndex-change;
                if (type.equals("h")) colIndex=colIndex-change;




          /*    if (type.equals("v")){

                       //đi xuống
                       if (rowIndex-rowIndex1>0)
                           for (int i=rowIndex1+1; i<=rowIndex;i++){

                               if (board[colIndex][i]!="0"&& board[colIndex][i]!=board[colIndex][rowIndex]){
                                   Log.d("ABCZ", "CH = "+i );
                                   rowIndex=i-2-blocks.getLength();
                                   break;
                               }

                           }
                    //đi lên
                    if (rowIndex-rowIndex1<0)
                        for (int i=rowIndex1-1;i>rowIndex;i--){
                            Log.d("ABCF",board[colIndex][i]);
                            if(board[colIndex][i]!="0"){
                                rowIndex=i+1;
                                Log.d("ABCX",colIndex+" - "+i);
                                break;
                            }
                            }


                }

            /*   for (int i = colIndex1+1; i <= colIndex; i++) {
                    for (int j = rowIndex1+1; j <= rowIndex; j++)
                        if (board[i][j]!="0") {
                            if (colIndex-colIndex1>0) colIndex=i-blocks.getLength();
                            else colIndex=i+1;
                            /*if (rowIndex-rowIndex1>0) rowIndex=j-blocks.getLength();
                            else rowIndex=j+1;
                            Log.d("ABCX",board[i][j]);
                            break;
                        }
                }*/

                Log.d("ABCX",blocks.toString());

                Log.d("KET QUA",colIndex+" - "+rowIndex);
                blocks.setStartX(colIndex);
                blocks.setStartY(rowIndex);

                drawBlock(blocks);
                if (blocks.getId().equals("hm")&& block.getStartX()==4){
                    Log.d("WIN", "WIN ");
                    win=true;
                }

                    break;
            }
        }
        v.invalidate();

        //Toast.makeText(context,"-",Toast.LENGTH_LONG ).show();
        return win;
    }
    public int searchBlockLength(int colIndex, int rowIndex) {
        String id = board[colIndex][rowIndex];
        for (Block blocks : listBlock)
            if (blocks.getId().equals(id)) {
                Log.d("CHIEUDAI",blocks.getLength()+"");
                return blocks.getLength();
            }
            return 0;
    }



}
