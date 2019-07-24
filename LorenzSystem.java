import peasy.*;

float x = 0.01;
float y = 0.01;
float z = 0.01;
//constants for the equation system
final float a = 10;
final float b = 14;
final float c = 8.0/3.0;

ArrayList<PVector> data = new ArrayList<PVector>();

//A 3D camera to navigate
PeasyCam cam;
void setup() {
  size(800, 600, P3D);
  noFill();
  colorMode(HSB);
  cam = new PeasyCam(this, 500);
}

void draw() {
  background(0);
  //differentials
  float dt = 0.01;
  float dx = (a * (y - x)) * dt;
  float dy = (x * (b - z) - y) * dt;
  float dz = (x * y - c * z) * dt;
  
  x += dx;
  y += dy;
  z += dz;
  
  data.add(new PVector(x, y, z));
  translate(0, 0, - 80);
  scale(5);
  
  float inc = 0;
  beginShape();
  for(PVector v : data) {
    stroke(inc, 255, 255);
    vertex(v.x, v.y, v.z);
    PVector randomnes = PVector.random3D();
    randomnes.mult(0.01);
    v.add(randomnes);
    
    inc += 0.2;
    if(inc >= 255) {
      inc = 0;
    }
  }
  endShape();
}
