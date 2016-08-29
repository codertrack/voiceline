package com.example.voiceline;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

/**
 * 随机动态声波曲线（矩形）
 * @author maoyu
 * @date   2016年8月29日
 */
public class VoiceLineView  extends View {
    private final int LINE = 0;
    private final int RECT = 1;
    private int date = 0;
    private Paint paintVoicLine;
    private Paint paintVoicLine_other;
    private int mode;
    private int numberlist = 4;

    private List<Rect> rectList;


    List<Path> paths = null;
	private Random random;

    public VoiceLineView(Context context) {
        super(context);
    }

    public VoiceLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAtts(context, attrs);
    }

    public VoiceLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAtts(context, attrs);
    }

    private void initAtts(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.voiceView);
        mode = typedArray.getInt(R.styleable.voiceView_viewMode, 0);
        numberlist = typedArray.getInt(R.styleable.voiceView_numberlist, 16);
        typedArray.recycle();
        random = new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mode == RECT) {
        		 drawVoiceRect(canvas);
        	}
        run();
    }

    private void drawVoiceRect(Canvas canvas) {
        if (paintVoicLine == null) {
            paintVoicLine = new Paint();
            paintVoicLine.setColor(Color.GREEN);
            paintVoicLine.setAntiAlias(true);
            paintVoicLine.setStyle(Paint.Style.FILL_AND_STROKE);
            paintVoicLine.setStrokeWidth(2);
        }
        
        if (paintVoicLine_other == null) {
        	paintVoicLine_other = new Paint();
        	paintVoicLine_other.setColor(Color.BLUE);
        	paintVoicLine_other.setAntiAlias(true);
        	paintVoicLine_other.setStyle(Paint.Style.FILL_AND_STROKE);
        	paintVoicLine_other.setStrokeWidth(2);
        }
        int width = getWidth();
        int height = getHeight();
        int left = getLeft();
        int top = getTop();
        if (rectList == null) {
            rectList = new LinkedList<>();
        }
        int pingjun = width/(numberlist*2);
        for (int i = 1; i <numberlist+1; i++) {
        	int da =(random.nextInt(height)-height/2);
        	if(da>0){
        		Rect d = new Rect(left+pingjun*(i-1)*2, top+height/2-da/2, left+pingjun*(2*i-1),top+height/2+da/2);
        		rectList.add(d);
        	}else if(da<0){
        		Rect d = new Rect(left+pingjun*(i-1)*2, top+height/2+da/2, left+pingjun*(2*i-1),top+height/2-da/2);
        		rectList.add(d);
        	}else{
        		Rect d = new Rect(left+pingjun*(i-1)*2, top+height/2+da, left+pingjun*(2*i-1),top+height/2+da);
        		rectList.add(d);
        	}  	
        	
		}
        canvas.restore();
        for (int i = rectList.size() - 1; i >= 0; i--) {
        	if(i>=7){
        		canvas.drawRect(rectList.get(i), paintVoicLine_other);
        	}else{
        		canvas.drawRect(rectList.get(i), paintVoicLine);
        	}
          
      }
        rectList.clear();
    }

    public void setVolume(int volume) {
    	date = volume;
    }

    public void run() {
        if (mode == RECT) {
            postInvalidateDelayed(200);
        } 
    }

}