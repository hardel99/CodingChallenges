int n = 0;
int c = 10; //spacing

void setup() {
    size(700, 700);
    background(0);
    stroke(255);
    strokeWeight(5);
    colorMode(HSB);
}

void draw() {
    float phi = n * radians(137.5);
    float r = c * sqrt(n);

    float x = r * cos(phi);
    float y = r * sin(phi);

    translate(width/2, height/2);
    stroke(n % 256, 255, 255);
    point(x, y);

    n++;
}
