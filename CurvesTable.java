float angle = 0;
float boxWidth = 100;
float diameter = boxWidth - 10;

Curve[][] curves; 

void setup() {
  size(1200, 700);
  curves = new Curve[(int)(height / boxWidth) - 1][(int)(width / boxWidth) - 1];
  
  for(int j = 0; j < (height / boxWidth) - 1; j++) {
    for(int i = 0; i < (width / boxWidth) - 1; i++) {
      curves[j][i] = new Curve();
    }
  }
}

void draw() {
  background(0);
  
  noFill();
  stroke(255);
  
  strokeWeight(0.5);
  for(int i = 0; i < (width / boxWidth) - 1; i++) {
    float offsetX = boxWidth * (1.5 + i);      //expression reduced using linear algebra
    ellipse(offsetX, boxWidth/2, diameter, diameter);
    
    strokeWeight(8);
    float x = (diameter/2) * cos(angle * (i + 1) - PI/2);
    float y = (diameter/2) * sin(angle * (i + 1) - PI/2);
    point(offsetX + x, (boxWidth/2) + y);
    
    strokeWeight(0.5);
    line(offsetX + x, 0, offsetX + x, height);
    
    for(int j = 0; j < (height / boxWidth) - 1; j++) {
      curves[j][i].setX(offsetX + x);
    }
  }
  
  for(int i = 0; i < (height / boxWidth) - 1; i++) {
    float offsetY = boxWidth * (1.5 + i);      //expression reduced using linear algebra
    ellipse(boxWidth/2, offsetY, diameter, diameter);
    
    strokeWeight(8);
    float x = (diameter/2) * cos(angle * (i + 1) - PI/2);
    float y = (diameter/2) * sin(angle * (i + 1) - PI/2);
    point((boxWidth/2) + x, offsetY + y);
    
    strokeWeight(0.5);
    line(0, offsetY + y, width, offsetY + y);
    
    for(int j = 0; j < (width / boxWidth) - 1; j++) {
      curves[i][j].setY(offsetY + y);
    }
  }
  
  if(angle < 2 * PI) {
    for(int j = 0; j < (height / boxWidth) - 1; j++) {
      for(int i = 0; i < (width / boxWidth) - 1; i++) {
        curves[j][i].addPoint();
        curves[j][i].doodle();
      }
    }
  } else{
    angle = 0;
    for(int j = 0; j < (height / boxWidth) - 1; j++) {
      for(int i = 0; i < (width / boxWidth) - 1; i++) {
        curves[j][i].removeAll();
      }
    }
  }
  
  angle += 0.015;
}

class Curve {
  ArrayList<PVector> path;
  PVector workingOn;
  
  Curve() {
    path = new ArrayList<PVector>();
    workingOn = new PVector();
  }
  
  void setX(float x) {
    workingOn.x = x;
  }
  
  void setY(float y) {
    workingOn.y = y;
  }
  
  void addPoint(){
    path.add(workingOn);
  }
  
  void removeAll() {
    path.clear();
  }
  
  //making a shape from vertex
  void doodle(){
    stroke(255);
    strokeWeight(1.5);
    noFill();
    beginShape();
    for(PVector p: path) {
      vertex(p.x, p.y);
    }
    endShape();
    
    //adding a point
    strokeWeight(5);
    point(workingOn.x, workingOn.y);
    workingOn = new PVector();
  }
}
