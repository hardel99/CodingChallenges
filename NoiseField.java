int vectorScale = 20;
int cols, rows, numParticles;
float time = 0;      //time as a new dimention
PVector vec;
Particle[] particles;
PVector[] field;      //physics III spoilers

void setup() {
  size(600, 600);
  cols = floor(width/vectorScale);
  rows = floor(height/vectorScale);
  
  numParticles = 400;
  
  field = new PVector[rows * cols];
  particles = new Particle[numParticles];
  
  for(int i = 0; i < numParticles; i++) {
    particles[i] = new Particle();
  }
}

void draw() {
  background(255);
  float offsetX = 0;
  for(int x = 0; x < cols; x++) {
    float offsetY = 0;
    for(int y = 0; y < rows; y++) {
      int index = x + y * rows;
      float r = noise(offsetX, offsetY, time) * TWO_PI * 2;
      vec = PVector.fromAngle(r);
      vec.setMag(1);
      field[index] = vec;
      
      noFill();
      stroke(0, 100);
      strokeWeight(1);
      push();
      translate(x * vectorScale, y * vectorScale);
      rotate(vec.heading());
      line(0, 0, vectorScale, 0);
      pop();
      offsetY += 0.1;
    }
    offsetX += 0.1;
  }
  
  for(int i = 0; i < particles.length; i++) {
    particles[i].follow(field, vectorScale, cols);
    particles[i].update();
    particles[i].doodle();
    particles[i].edgeless();
  }
  
  time += 0.005;
}

class Particle{
  PVector position, velocity, acceleration;
  
  Particle() {
    position = new PVector(random(width), random(height));
    velocity = new PVector(0, 0);
    acceleration = new PVector(0, 0);
  }
  
  void update() {
    velocity.add(acceleration);
    velocity.limit(4);
    position.add(velocity);
    
    //reset acceleration
    acceleration.mult(0);
  }
  
  void follow(PVector[] field, int scale, int cols) {
    int x = floor(position.x / scale);
    int y = floor(position.y / scale);
    
    int index = x + y + cols;
    
    addForce(field[index]);
  }
  
  void addForce(PVector force) {
    acceleration.add(force);
  }
  
  void doodle() {
    stroke(0);
    strokeWeight(5);
    point(position.x, position.y);
  }
  
  void edgeless() {
    if(position.x > width) position.x = 0;
    if(position.x < 0) position.x = width;
    if(position.y > height) position.y = 0;
    if(position.y < 0) position.y = height;
  }
}
