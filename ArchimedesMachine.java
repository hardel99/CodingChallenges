float angle = 0;
float r = 200;
float circles = 20;
int n = 4;

void setup() {
    size(500,500);
    stroke(255, 200);
    noFill();
}

void draw() {
    background(0);
    
    strokeWeight(3);
    ellipse(width/2, height/2, r*2, r*2);
    
    refract();
    
    angle -= 0.05;
}

void refract() {
    pushMatrix();
    translate(width/2, height/2);
    strokeWeight(1);
    float phi = PI/n;
    for (int i = 0; i < n; ++i) {
        float realAngle = angle + (phi * i);
        float x = r * cos(realAngle);
        ellipse(x, 0, circles, circles);
        line(-r, 0, r, 0);
        rotate(phi);
    }
    popMatrix();
}
