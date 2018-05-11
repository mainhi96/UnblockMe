package com.example.sony.unblockme.asset;

import com.example.sony.unblockme.model.Block;

import java.util.ArrayList;

/**
 * Created by SONY on 5/8/2018.
 */

public class AddLevel {
  private ArrayList<Block> arrayBlock= new ArrayList<>();

    public AddLevel() {
    }


    public ArrayList<Block> getArrayBlock() {
        return arrayBlock;
    }

    public void setArrayBlock(ArrayList<Block> arrayBlock) {
        this.arrayBlock = arrayBlock;
    }

    public void add1(){
        arrayBlock.add(new Block(2,0,0,"h1"));
        arrayBlock.add(new Block(2,4,3,"h2"));
        arrayBlock.add(new Block(2,0,2,"hm"));
        arrayBlock.add(new Block(3,0,5,"h3"));
        arrayBlock.add(new Block(2,0,3,"v1"));
        arrayBlock.add(new Block(2,4,4,"v2"));
        arrayBlock.add(new Block(3,5,0,"v3"));
        arrayBlock.add(new Block(3,2,1,"v4"));
    }

    public void add2(){

        arrayBlock.add(new Block(2,0,0,"h1"));
        arrayBlock.add(new Block(2,4,0,"h2"));
        arrayBlock.add(new Block(2,0,2,"hm"));
        arrayBlock.add(new Block(3,3,4,"h3"));
        arrayBlock.add(new Block(2,0,4,"v1"));
        arrayBlock.add(new Block(2,2,0,"v2"));
        arrayBlock.add(new Block(3,3,1,"v3"));
        arrayBlock.add(new Block(3,4,1,"v4"));

    }
}
