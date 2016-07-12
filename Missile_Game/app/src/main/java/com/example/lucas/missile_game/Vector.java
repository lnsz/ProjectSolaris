package com.example.lucas.missile_game;

/**
 * Created by lucas on 7/11/2016.
 */
public class Vector {
    float x;
    float y;

    public Vector(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void add(Vector vec){
        this.x+=vec.x;
        this.y+=vec.y;
    }
    public static Vector add(Vector vec1, Vector vec2){
        return new Vector(vec1.x+vec2.x, vec1.y+vec2.y);
    }

    public void sub(Vector vec){
        this.x-=vec.x;
        this.y-=vec.y;
    }
    public static Vector sub(Vector vec1, Vector vec2){
        return new Vector(vec1.x-vec2.x, vec1.y-vec2.y);
    }

    public float mag(){
        return (float)Math.sqrt(this.x*this.x + this.y*this.y);
    }

    public float magSq(){
        return this.x*this.x + this.y*this.y;
    }

    public void normalize(){
        float m = this.mag();
        this.x /= m;
        this.y /= m;
    }

    public float dot(Vector vec){
        return (this.x * vec.x + this.y * vec.y);
    }

    public void mult(float scalar){
        this.x*=scalar;
        this.y*=scalar;
    }

    public static Vector mult (Vector vec, float scalar){
        return new Vector(vec.x * scalar, vec.y * scalar);
    }

    public void div(float scalar){
        this.x/=scalar;
        this.y/=scalar;
    }

    public static Vector div (Vector vec, float scalar){
        return new Vector(vec.x / scalar, vec.y / scalar);
    }

    public void rotate (float angle){
        //angle in rads
        float xx = this.x;
        float yy = this.y;
        this.x = (float)(xx* Math.cos(angle) - yy * Math.sin(angle));
        this.y = (float)(yy* Math.cos(angle) + xx * Math.sin(angle));
    }

    public static void rotate (Vector vec ,float angle){
        //angle in rads
        float xx = vec.x;
        float yy = vec.y;
        vec.x = (float)(xx* Math.cos(angle) - yy * Math.sin(angle));
        vec.y = (float)(yy* Math.cos(angle) + xx * Math.sin(angle));
    }

}
