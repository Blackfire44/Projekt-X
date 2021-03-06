package com.example.fabian.gameofpoints;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public class GameView extends View {
    private float scale, Vx, Vy;
    private int countdown;

    private BitmapDrawable color;

    private Rect viechRect = new Rect();
    private RectF drawRect = new RectF();

    private Paint paintBitmap = new Paint();
    private Paint paintText = new Paint();
    
    
    public GameView(Context context) { //Konstruktor
        super(context);
        scale = getResources().getDisplayMetrics().density;
        paintBitmap.setAntiAlias(true); //Macht die Ecken des gezeichneten glatter
        paintText.setAntiAlias(true); //Macht die Ecken des gezeichneten glatter
        paintText.setColor(Color.rgb(255,255,255)); //Die Werte für den "Pinsel" für denText werden gesetzt
        paintText.setTextSize(scale*30);
        paintText.setStyle(Paint.Style.FILL);
    }

    public void setCountdown(){
        countdown += 1;
    } //Der Countdown wird um 1 erhöht

    public int getCountdown(){
        return countdown;
    } //Getter Countdown

    public void setData() {
        invalidate();       
    } //Das System wird angehalten, so bald wie möglich eine Neuzeichnung vorzunehmen
    
    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawText(Integer.toString(countdown), 10*scale, canvas.getHeight()-10*scale, paintText); //Der Countdown wird gesetzt
        for(int i = 0; i<Objekt.getListe().size(); i++){ //Die Liste mit den Objekten wird durchgegengen
            if(Objekt.getObjekt(i).getLife()>0){ //Es werden nur lebendige Objekte gezeichnet
                Vx = Objekt.getObjekt(i).getX(); //Der X-Wert wird geholt
                Vy = Objekt.getObjekt(i).getY(); //Der Y-Wert wird geholt
                if(Objekt.getObjekt(i).getControl()){
                    color = (BitmapDrawable) getResources().getDrawable(R.drawable.yellow);
                    drawRect.set(Vx - (int)( Objekt.getObjekt(i).getR() / 1.9), Vy - (int) ( Objekt.getObjekt(i).getR() / 1.9), Vx + (int) ( Objekt.getObjekt(i).getR() / 1.9), Vy + (int) ( Objekt.getObjekt(i).getR() / 1.9));
                    viechRect.set(0,0,color.getBitmap().getWidth(), color.getBitmap().getHeight()); //viechRect setzt ein Rechteck mit den Größen der Bitmap des Drawables
                    canvas.drawBitmap(color.getBitmap(), viechRect, drawRect, paintBitmap);
                }else {
                    if (Objekt.getObjekt(i).getBreedTimer() > 200) {
                        color = (BitmapDrawable) getResources().getDrawable(R.drawable.green);
                        drawRect.set(Vx - (int) (Objekt.getObjekt(i).getR() / 1.9), Vy - (int) (Objekt.getObjekt(i).getR() / 1.9), Vx + (int) (Objekt.getObjekt(i).getR() / 1.9), Vy + (int) (Objekt.getObjekt(i).getR() / 1.9));
                        viechRect.set(0, 0, color.getBitmap().getWidth(), color.getBitmap().getHeight()); //viechRect setzt ein Rechteck mit den Größen der Bitmap des Drawables
                        canvas.drawBitmap(color.getBitmap(), viechRect, drawRect, paintBitmap);
                    }
                }
                color = (BitmapDrawable) getResources().getDrawable(Objekt.getObjekt(i).getColor()); //Das BitmapDrawable holt sich aus dem zu zeichnenden Objekt die Adresse und sucht sich an dieser Stelle das Bild(Drawable)
                drawRect.set(Vx - (int) Objekt.getObjekt(i).getR() / 2, Vy - (int) Objekt.getObjekt(i).getR() / 2, Vx + (int) Objekt.getObjekt(i).getR() / 2, Vy + (int) Objekt.getObjekt(i).getR() / 2); //Das Rehteck wird auf 4 float Koordinaten in alle 4 Richtungen gesetzt, wobei der Abstand der gegenüberliegenden jeweils den Durchmesser ausmachen, sodass die Koordinaten den mittelpunkt darstellen.
                viechRect.set(0,0,color.getBitmap().getWidth(), color.getBitmap().getHeight()); //viechRect setzt ein Rechteck mit den Größen der Bitmap des Drawables
                canvas.save();
                canvas.translate(Objekt.getObjekt(i).getX(), Objekt.getObjekt(i).getY());
                canvas.rotate(Objekt.getObjekt(i).getDirection()+90);//Change
                canvas.translate(-Objekt.getObjekt(i).getX(),- Objekt.getObjekt(i).getY());
                canvas.drawBitmap(color.getBitmap(), viechRect, drawRect, paintBitmap);
                canvas.restore();
            }
        }
    }
}