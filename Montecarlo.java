int totalDots = 0;
int circleDots = 0;
int diameter = 670;
PGraphics canvas;
double prevPI = 0;
double prevDifference;

void setup() {
  size(700, 740);
  background(0);
  stroke(255);
  noFill();
  strokeWeight(3);
  rect(15, 15, diameter, diameter);
  ellipse(width/2, height/2 - 20, diameter, diameter);
  strokeWeight(5);
  
  canvas = createGraphics(width, 30);
}

void draw() {
  //Montecarlo's method
  //π = 4 * circleDots/totalDots
  
  //makin things a little faster
  //if you don't care about the exact value and wanna see the animation just reduce the iterations
  for(int i = 0; i < 100; i++) {
    float x = random(15, 685);
    float y = random(15, 685);
    
    if(dist(width/2, height/2 -20, x, y) <= (diameter/2)) {
      circleDots++;      //the dot it inside the circle
      stroke(227, 39, 57);
    } else{
      stroke(41, 217, 76);
    }
    totalDots++;
    
    point(x, y);
  }
  
  double pi = 4 * (double)circleDots/ (double)totalDots;
  double realDifference = Math.abs(Math.PI - pi);
  double prevDifference = Math.abs(Math.PI - prevPI);
  
  if(prevDifference > realDifference) {
    prevDifference = realDifference;
    prevPI = pi;
    
    canvas.beginDraw();
    canvas.background(255);
    canvas.fill(0);
    canvas.textSize(20);
    canvas.text("" + pi, 250, 20);
    canvas.endDraw();
    
    
    image(canvas, 0, 700);
  }
}
