package grainy.graphics;

import processing.core.*;

/**
 * This is a template class and can be used to start a new processing Library.
 * Make sure you rename this class as well as the name of the example package 'template' 
 * to your own Library naming convention.
 * 
 * (the tag example followed by the name of an example included in folder 'examples' will
 * automatically include the example in the javadoc.)
 */

public class GrainyPainter {
	
	private PGraphics canvas;
	
	private float stepScale = 1f;
	private float endR = 100f;
	private int density = 1;
	private float rIni = 0.1f;
	private float rStepStep = 0.1f;
	
	private boolean silentMode = false;
	public final static String VERSION = "##library.prettyVersion##";

	/**
	 * a Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the Library.
	 */
	public GrainyPainter(PGraphics myCanvas) {
		this.canvas = myCanvas;
		welcome();
	}
	
	public GrainyPainter(PGraphics myCanvas, String extraCommand) {
		this.canvas = myCanvas;
		if(extraCommand!="silent") {
			welcome();
		}
		else {
			this.silentMode = true;
		}
	}
	
	
	private void welcome() {
		System.out.println("##library.name## ##library.prettyVersion## by ##author##");
		System.out.println("The Target PGraphics is "+canvas.toString());
	}
	
	/**
	 * return the version of the Library.
	 * 
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}

	public void setCanvas(PGraphics newCanvas) {
		this.canvas = newCanvas;
		if(this.silentMode==false) {
			System.out.println("The Target PGraphics is "+this.canvas.toString());
		}
	}
	
	public PGraphics getCanvas() {
		return this.canvas;
	}
	
	/////////////////////////////////////////////

	public void setStepScale(float stepScale) {
		this.stepScale = stepScale;
	}
	
	public void setEndR(float endR) {
		this.endR = endR;
	}
	
	public void setDensity(int density) {
		this.density = density;
	}
	
	public void setInitialR(float rIni) {
		this.rIni = rIni;
	}
	
	public void setMetaStepR(float metaStep) {
		this.rStepStep = metaStep;
	}
	
	///////////////////////////////
	
	public float getStepScale() {
		return this.stepScale;
	}
	
	public float getEndR() {
		return this.endR;
	}
	
	public int getDensity() {
		return this.density;
	}
	
	public float getInitialR() {
		return this.rIni;
	}
	
	public float getMetaStepR() {
		return this.rStepStep;
	}
	
	///////////////////////////////////
	
	void grainyLine(float startX,float startY,float endX,float endY){
	  float myStep = stepScale;
	  float x = startX;
	  float y = startY;
	  if((endX-startX)!=0){
	    if(endX<startX) myStep = -myStep;
	    float k = (endY-startY)/(endX-startX);
	    float b = startY-k*startX;
	    do{
	      x+=myStep;
	      y = k*x+b;
	      dropPoints(x,y);
	    }while(notReachEnd(x,startX,endX));
	  }
	  else{
	    if(endY<startY) myStep = -myStep;
	    do{
	      y+=myStep;
	      x = startX;
	      dropPoints(x,y);
	    }while(notReachEnd(y,startY,endY));
	  }
	}

	void grainyLine(float startX,float startY,float endX,float endY,PGraphics canvas){	
	  this.canvas = canvas;
	  float myStep = stepScale;
	  float x = startX;
	  float y = startY;
	  if((endX-startX)!=0){
	    if(endX<startX) myStep = -myStep;
	    float k = (endY-startY)/(endX-startX);
	    float b = startY-k*startX;
	    do{
	      x+=myStep;
	      y = k*x+b;
	      dropPoints(x,y,canvas);
	    }while(notReachEnd(x,startX,endX));
	  }
	  else{
	    if(endY<startY) myStep = -myStep;
	    do{
	      y+=myStep;
	      x = startX;
	      dropPoints(x,y,canvas);
	    }while(notReachEnd(y,startY,endY));
	  }
	}

	boolean notReachEnd(float x,float start,float end){
	  if(start<=end){
	      if(x<=end) return true; else return false;
	  }
	  else{
	    if(x>end) return true; else return false;
	  }
	}

	void dropPoints(float x,float y){
	  float rStep = 0.0f;
	  float r = rIni;
	  do{
	    for(int drops = 0; drops < density;drops++){
	      float tempX = (float) (Math.random()-0.5f);
	      float tempY = (float) (Math.random()-0.5f);
	      tempX *= r; tempY *= r;
	      while(dist(x+tempX,y+tempY,x,y)>r){
	        tempX = (float) (Math.random()-0.5f);
	        tempY = (float) (Math.random()-0.5f);
	        tempX *= r; tempY *= r;
	      }
	      this.canvas.point(x+tempX,y+tempY);
	    }
	    rStep+=rStepStep;
	    //r=rStep;
	    //r=easeOutCirc(rStep)*endR;
	    r=easeInExpo(rStep)*endR;
	    //r=easeInOutSine(rStep)*endR;
	  }while(r<endR);
	}

	void dropPoints(float x,float y,PGraphics canvas){
	  float rStep = 0.0f;
	  float r = rIni;
	  do{
	    for(int drops = 0; drops < density;drops++){
	      float tempX = (float) (Math.random()-0.5f);
	      float tempY = (float) (Math.random()-0.5f);
	      tempX *= r; tempY *= r;
	      while(dist(x+tempX,y+tempY,x,y)>r){
	        tempX = (float) (Math.random()-0.5f);
	        tempY = (float) (Math.random()-0.5f);
	        tempX *= r; tempY *= r;
	      }
	      canvas.point(x+tempX,y+tempY);
	    }
	    rStep+=rStepStep;
	    //r=rStep;
	    //r=easeOutCirc(rStep)*endR;
	    r=easeInExpo(rStep)*endR;
	    //r=easeInOutSine(rStep)*endR;
	  }while(r<endR);
	}

	void grainyPoint(float x,float y){
	  grainyLine(x,y,x,y);
	}

	void grainyPoint(float x,float y,PGraphics canvas){
	  grainyLine(x,y,x,y,canvas);
	}

	void grainyRect(float x,float y,float myWidth,float myHeight){
	  grainyLine(x,y,x+myWidth,y);
	  grainyLine(x,y,x,y+myHeight);
	  grainyLine(x+myWidth,y,x+myWidth,y+myHeight);
	  grainyLine(x,y+myHeight,x+myWidth,y+myHeight);
	}

	void grainyEllipse(float x,float y,float myWidth,float myHeight){
	    for (int i=0; i<Math.round(Math.max(myWidth,myHeight)*2f); i++) {
		    float a = (float) (i * 2 * Math.PI/Math.round(Math.max(myWidth,myHeight)*2f));
		    float x1 = (float) (myWidth * 0.5f * Math.cos(a));
		    float y1 = (float) (myHeight * 0.5f * Math.sin(a));
		    grainyPoint(x1, y1);
	  }
	}

	void grainyCircle(float x,float y,float r){
	  grainyEllipse(x,y,r,r);
	}
	
	////////////////////////////////////////
	
	private float dist(float x1, float y1, float x2, float y2) {
	  return (float) Math.sqrt( (x2-x1)*(x2-x1)+ (y2-y1)*(y2-y1));
	}

	private float easeOutCirc(float x){
	  return (float) Math.sqrt(1 - Math.pow(x - 1, 2));
	}

	float easeInExpo(float x){
	  if(x==0){
	    return 0;
	  }
	  else{
	    return (float) Math.pow(2, 10 * x - 10);
	  }
	}

	float easeInOutSine(float x){
	  return (float) (-(Math.cos(Math.PI * x) - 1) / 2);
	}
}



