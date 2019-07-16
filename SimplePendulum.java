float m = 50;            //the mass of the oscilant object(using for the size of the ball)
float r = 400;           //the longitude of the pendulum(in pixels)
float angle = PI/4;      //the angle with the vertical axis 
float velocity = 0;      //used like w(angular frequency)
float aceleration = 0;   //used like a(angular aceleration)
float x, y;

PGraphics canvas;

void setup(){
  size(1200, 700);
  canvas = createGraphics(1200, 700);
  
  canvas.beginDraw();
  canvas.background(0);
  canvas.endDraw();
}

void draw(){
  x = r * sin(angle);
  y = r * cos(angle);
  
  image(canvas, 0, 0);
  translate(600, 100);
  
  stroke(255);
  strokeWeight(2);
  line(0, 0, x, y);
  fill(0);
  ellipse(x, y, m, m);  //drawing a perfect circle
  
  aceleration = (-0.98 / r) * sin(angle);
  
  velocity += aceleration;
  angle += velocity;
  
  velocity *= 0.99;     //has to lose energy
  
  canvas.beginDraw();
  canvas.translate(600, 100);
  canvas.stroke(255);
  canvas.strokeWeight(4);
  canvas.point(x, y);
  canvas.endDraw();
}
