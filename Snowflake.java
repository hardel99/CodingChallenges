Particle current;
ArrayList<Particle> wholeSnowflake = new ArrayList<Particle>();

void setup() {
    //size(800, 600);
    fullScreen();
    current = new Particle(width/2, random(10));
}

void draw() {
    background(255);
    translate(width / 2, height / 2);

    while(!current.hasDone() && !current.isInterescting(wholeSnowflake)) {
      current.refresh();
    }
    
    wholeSnowflake.add(current);
    current = new Particle(width / 2, 0);
        
    for (int i = 0; i < 8; i++) {
        rotate(PI/4);
        current.doodle();
        for (Particle p : wholeSnowflake) {
            p.doodle();
        }
    
        pushMatrix();
        current.doodle();
        scale(1, -1);
        for (Particle p : wholeSnowflake) {
            p.doodle();
        }
        popMatrix();
    }
}

class Particle {
    float radius;
    PVector position;
    
    Particle(float x, float y) {
        position = new PVector(x, y);
        radius = 3;
    }

    void refresh() {
        position.x -= 1;
        position.y += random(-3, 3);
        
        float angle = constrain(position.heading(), 0, PI/4);
        float mag = position.mag();
        position = PVector.fromAngle(angle);
        position.setMag(mag);
    }

    void doodle() {
        fill(0);
        stroke(0);

        ellipse(position.x, position.y, radius * 2, radius * 2);
    }

    boolean hasDone() {
        return position.x < 1;
    }

    boolean isInterescting(ArrayList<Particle> snowflake) {
        boolean last = false;
        for (Particle p : snowflake) {
            if(dist(p.position.x, p.position.y, position.x, position.y) < radius * 2) {
                last = true;
            }
        }

        return last;
    }
}
