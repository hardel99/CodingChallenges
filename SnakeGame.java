Snake snie;      //cause its smoll
Cherry cher;
int bck = 255;
int score = 0;

void setup() {
  size(600, 600);
  snie = new Snake();
  cher = new Cherry(20);
 
  frameRate(10);
  textAlign(CENTER);
  PFont font = createFont("OCR A Extended", 25, true);
  textFont(font);
}

void draw() {
  background(bck);
  
  cher.doodle();
  
  if(snie.eat(cher.cherry)) {
    cher.runAway();
    score++;
    if(score%10 == 0) {
      if(bck == 255) {
        bck = 25;
      } else{
        bck = 255;
      }
    }
  }
  if (snie.rest()) {
    bck = 255;
    score = 0;
  }
  
  snie.refresh();
  snie.doodle();
  
  text("Your score :" + score, width/2, 45);
}

void keyPressed() {
  switch(keyCode) {
    case UP:
      snie.speedY = -1;
      snie.speedX = 0;
    break;
    case DOWN:
      snie.speedY = 1;
      snie.speedX = 0;
    break;
    case LEFT:
      snie.speedY = 0;
      snie.speedX = -1;
    break;
    case RIGHT:
      snie.speedY = 0;
      snie.speedX = 1;
    break;
    default:
    //nothing to do here
    break;
  }
}

class Cherry {
  PVector cherry;
  int scale;
  
  Cherry(int scl) {
    scale = scl;
    runAway();
  }
  
  void runAway() {
    int cols = floor(width / scale);
    int rows = floor(height / scale);
    cherry = new PVector(floor(random(cols)), floor(random(rows)));
    cherry.mult(scale);
  }
  
  void doodle() {
    fill(245, 66, 66);
    rect(cherry.x, cherry.y, scale, scale);
  }
}

class Snake {
  int x, y;
  float speedX, speedY;
  int scl = 20;
  int snakeSize = 0;
  ArrayList<PVector> body = new ArrayList();
  
  Snake() {
    x = 0;
    y = 0;
    speedX = 1;
    speedY = 0;
  }
  
  void refresh() {
    if(snakeSize == body.size()) {
      for(int i = 0; i < body.size() - 1; i++) {
        body.set(i, body.get(i + 1));
      }
    }
    
    if(body.size() > 0) {
      if(snakeSize - 1 >= 0) {
        body.set(snakeSize - 1, new PVector(x, y));
      } else{
        body.set(snakeSize, new PVector(x, y));
        println("happening");
      }
    }
    
    x += speedX * scl;
    y += speedY * scl;
    
    if(x > width) x = 0;
    if(x < 0) x = width;
    if(y > height) y = 0;
    if(y < 0) y = height;
  }
  
  boolean eat(PVector cherryPosition) {
    if(dist(cherryPosition.x, cherryPosition.y, x, y) < 1) {
      body.add(new PVector(cherryPosition.x, cherryPosition.y));
      snakeSize++;
      return true;
    } else{
      return false;
    }
  }
  
  void doodle() {
    fill(50);
    stroke(255);
    
    for(int i = 0; i < body.size(); i++) {
      rect(body.get(i).x, body.get(i).y, scl, scl);
    }
    
    rect(x, y, scl, scl);
    
  }
  
  boolean rest() {
    if(body.size() > 1) {
      for(int i = 2; i < body.size(); i++) {
        PVector position = body.get(i - 1);
        if(dist(x, y, position.x, position.y) < 20) {
          snakeSize = 0;
          body.clear();
          return true;
        }
      }
    }
    return false;
  }
}
