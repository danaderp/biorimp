/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.search;

import unalcol.io.Write;

/**
 *
 * @author Jonatan
 */
public class Solution<T> {
    protected T x;
    protected double qx;
    
    public Solution( T x, double qx ){
        this.x = x;
        this.qx = qx;
    }
    
    public Solution( T x, Goal<T> goal ){
        this.x = x;
        this.qx = goal.quality(x);
    }
    
    public double quality( Goal<T> g ){
        if( g.nonStationary() ) qx = g.quality(x);
        System.out.println( "���QUALITY������������:" + qx );	
        return qx;
    }
    
    public T value(){ return x; }
    public double quality(){ return qx; }
    
    public String toString(){
    	String txt = Write.toString(x);
    	return txt + "," + quality();
    }
}
