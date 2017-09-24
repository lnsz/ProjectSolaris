package com.mygdx.game;


/**
 * Created by lucas on 7/11/2016.
 */
public class Vector {
    float x;
    float y;

    public Vector(){
        this.x = 0;
        this.y = 0;
    }

    public Vector(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void add(Vector vec){
        this.x += vec.x;
        this.y += vec.y;
    }
    public static Vector add(Vector vec1, Vector vec2){
        return new Vector(vec1.x + vec2.x, vec1.y + vec2.y);
    }

    public void sub(Vector vec){
        this.x -= vec.x;
        this.y -= vec.y;
    }
    public static Vector sub(Vector vec1, Vector vec2){
        return new Vector(vec1.x - vec2.x, vec1.y - vec2.y);
    }

    public float mag(){
        return (float)Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public float magSq(){
        return this.x * this.x + this.y * this.y;
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
        this.x *= scalar;
        this.y *= scalar;
    }

    public static Vector mult (Vector vec, float scalar){
        return new Vector(vec.x * scalar, vec.y * scalar);
    }

    public void div(float scalar){
        this.x /= scalar;
        this.y /= scalar;
    }

    public static Vector div (Vector vec, float scalar){
        return new Vector(vec.x / scalar, vec.y / scalar);
    }

    public void rotate (float angle){
        //angle in rads
        float xx = this.x;
        float yy = this.y;
        this.x = (float)(xx * Math.cos(angle) - yy * Math.sin(angle));
        this.y = (float)(yy * Math.cos(angle) + xx * Math.sin(angle));
    }

    public static Vector rotate (Vector vec ,float angle){
        //angle in rads
        Vector result = new Vector(vec.x, vec.y);
        result.x = (float)(vec.x * Math.cos(angle) - vec.y * Math.sin(angle));
        result.y = (float)(vec.y * Math.cos(angle) + vec.x * Math.sin(angle));
        return result;
    }


    //heading return can be negative
    //flip phone upside down to se, heading is clockwise goes clockwise from a [0,1] vector
    public float getHeading(){
        //handle dividing by 0
        if(this.y ==0){
            if(this.x == 0){
                //Zero vector special case
                return 0;
            }else if(this.x > 0){
                //vector colliniar with x axis
                return (float)(Math.PI /2);
            }else{
                //vector opposite to x axis
                return (float)(-Math.PI /2);
            }
        }

        float angle = (float)Math.atan(this.x / Math.abs(this.y));

        if(this.y > 0){
            return -angle;
        }else{
            //y must be less than 0
            return (float)Math.PI + angle;
        }
    }


    public static Vector random(){
        float angle = (float)(Math.random() * Math.PI * 2);
        return new Vector((float) Math.cos(angle), (float)Math.sin(angle));

    }

    public void reset(){
        x = 0;
        y = 0;
    }

    public static float distance(Vector v1, Vector v2){
        return (sub(v2, v1).mag());
    }

    @Override
    public String toString(){
        return("(" + this.x + ", " + this.y + ")");
    }

    public Vector scale(){
        Vector vec = new Vector(this.x, this.y);

        vec.mult(ProjectSolaris.timeScale);
        vec.mult(ProjectSolaris.resolutionMult);

        return vec;
    }
}