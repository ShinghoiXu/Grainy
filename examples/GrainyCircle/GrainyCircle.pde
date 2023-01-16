import grainy.graphics.*;
PGraphics canv;
GrainyPainter myGP;

void setup(){
  size(600,600);
  //background(0);
  canv = createGraphics(600,600);

  myGP = new GrainyPainter(canv);
}

void draw(){
  canv.beginDraw();
  canv.background(0);
  canv.stroke(#72ABE3);
  canv.endDraw();
  myGP.setPaintingMode(3);
  for(int i = 0; i <= 4 ; i++){
    myGP.grainyCircle(300,300,200);
  }
  image(canv,0,0);
}
