ArrayList<Circle> circles = new ArrayList<Circle>();
ArrayList<PVector> points = new ArrayList<PVector>();
PImage img;

void setup() {
    size(731, 732);
    img = loadImage("dark.png");
    img.loadPixels();
    for (int i = 0; i < img.width; ++i) {
        for (int j = 0; j < img.height; ++j) {
            int index = i + j * img.width;
            color c = img.pixels[index];
            if(brightness(c) > 200) {
                points.add(new PVector(i, j));
            }
        }
    }
}

void draw() {
    background(0);

    int totalAmount = 5;
    int counter = 0;
    int attemps = 0;

    while (totalAmount > counter) {
        Circle cir = createCircle();

        if(cir != null) {
            circles.add(cir);
            counter++;
        }

        //preventing an infinite loop
        attemps++;
        if(attemps > 1000) {
            noLoop();
            break;
        }
    }

    for (Circle c : circles) {
        if(c.growMore) {
            if(c.limitScreen()) {
                c.growMore = false;
            } else{
                for (Circle ci : circles) {
                    if(c != ci) {
                        if(dist(c.x, c.y, ci.x, ci.y) - 2 < c.r + ci.r) {
                            c.growMore = false;
                        }
                    }
                }
            }
        }

        c.doodle();
        c.increment();
    }
}

Circle createCircle() {
    int r = int(random(0, points.size()));
    PVector specialPoint = points.get(r);

    float x = specialPoint.x;
    float y = specialPoint.y;

    boolean isValid = true;
    for (Circle c : circles) {
        if(dist(x, y, c.x, c.y) < c.r) {
            isValid = false;
            break;
        }
    }

    if(isValid) {
        return new Circle(x, y);
    }

    return null;
}

class Circle {
    float x, y, r;
    boolean growMore = true;

    Circle (float x, float y) {
        this.x = x;
        this.y = y;
        this.r = 1;
    }

    void doodle() {
        stroke(255);
        noFill();
        strokeWeight(2);
        ellipse(x, y, r * 2, r * 2);
    }

    void increment() {
        if(growMore) {
            r+=0.5;
        }
    }

    boolean limitScreen() {
        return (x - r < 0 || x + r > width || y + r > height || y - r < 0);
    } 
}
