float angle = 0;
ArrayList<Float> yPoints = new ArrayList<Float>();

void setup() {
    size(800, 600);
}

void draw() {
    background(0);

    translate(200, height/2);
    noFill();
    strokeWeight(1);

    float x = 0;
    float y = 0;
    
    for (int i = 0; i < 100; i++) {
        float prevX = x;
        float prevY = y;
        //SquareWave
        /*float n = i * 2 + 1;
        float radius = 7 * (4.0 / n * PI);*/
        
        //TriangularWave
        float n = i + 1;
        float radius = 7 * (2.0 / -n * PI);
        
        x += radius * cos(n * angle);
        y += radius * sin(n * angle);
    
        stroke(255, 80);
        ellipse(prevX, prevY, radius * 2,radius * 2);
        
        stroke(255);
        line(prevX, prevY, x, y);
    }

    //we only care the y value
    yPoints.add(0, y);

    translate(200, 0);
    line(x - 200, y, 0, y);
    beginShape();
    for (int i = 0; i < yPoints.size(); i++) {
        vertex(i , yPoints.get(i));
    }
    endShape();

    if(yPoints.size() > 400) {
        yPoints.remove(yPoints.size() - 1);
    }
    
    angle -= 0.03;
}
