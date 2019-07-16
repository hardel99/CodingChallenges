float m1 = 45;     //mass of the first ball
float m2 = 30;     //mass of the second ball
float r1 = 200;    //longitude of the first pendulum
float r2 = 180;    //longitude of the second pendulum
float a1 = 5*PI/6; //init angle forthe first ball
float a2 = PI/2;   //init angle for the second ball
float v1 = 0;      //velocities
float v2 = 0;
float g = 1;       //gravity value, to change this divide the actual acceleration/10(i.e.: 9.8 -> 0.98)
float x1, y1, x2, y2, xo2, yo2;

PGraphics canvas;

void setup(){
  size(1200, 700);
  canvas = createGraphics(width, height);
  
  canvas.beginDraw();
  canvas.background(0);
  canvas.endDraw();
  xo2 = 0;
  yo2 = 0;
}

void draw(){
  //hard math stuffs
  float denominator = r1 * (2*m1 + m2 - m2*cos(2*a1 - 2*a2));
  float term1 = -g * (2*m1 + m2) * sin(a1);
  float term2 = m2 * g * sin(a1 - 2*a2);
  float term3 = 2*sin(a1 - a2) * m2;
  float term4 = v2*v2 * r2 + v1*v1 * r1 * cos(a1 - a2);
  float acc1 = (term1 - term2 - term3 * term4)/ denominator;
  
  term1 = 2 * sin(a1 - a2);
  term2 = v1*v1 * r1 * (m1 + m2);
  term3 = g * (m1 + m2) * cos(a1);
  term4 = v2*v2 * r2 * m2 * cos(a1 - a2);
  denominator = r2 * (2*m1 + m2 - m2*cos(2*a1 - 2*a2));
  float acc2 = term1 * (term2 + term3 + term4) / denominator;
  
  //determinate the first ball position
  x1 = r1 * sin(a1);
  y1 = r1 * cos(a1);
  
  //determinate the second ball position
  x2 = x1 + r2 * sin(a2);
  y2 = y1 + r2 * cos(a2);
  
  image(canvas, 0, 0);
  translate(600, 300);
  
  stroke(255);
  strokeWeight(2);
  line(0, 0, x1, y1);
  line(x1, y1, x2, y2);
  fill(0);
  ellipse(x1, y1, m1, m1);  //drawing a perfect 1 circle
  ellipse(x2, y2, m2, m2);  //drawing a perfect 2 circle
  
  v1 += acc1;
  a1 += v1;
  
  v2 += acc2;
  a2 += v2;
  v2 *= 0.999;     //has to lose energy
  v1 *= 0.999;     //has to lose energy
  
  canvas.beginDraw();
  canvas.translate(600, 300);
  canvas.stroke((int)random(0, 255), (int)random(100, 200), (int)random(100, 200));
  canvas.strokeWeight(1.5);
  if(frameCount > 1) {
    canvas.line(xo2, yo2, x2, y2);
  }
  canvas.endDraw();
  
  xo2 = x2;  //preius X, and previus Y, just for draw the line descibing the path of the second ball
  yo2 = y2;
}
