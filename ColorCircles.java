ArrayList<Circle> circles = new ArrayList<Circle>();
PImage img;

void setup() {
    size(731, 732);
    img = loadImage("dark.png");
    img.loadPixels();
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
    float x = random(width);
    float y = random(height);

    boolean isValid = true;
    for (Circle c : circles) {
        if(dist(x, y, c.x, c.y) < c.r) {
            isValid = false;
            break;
        }
    }

    if(isValid) {
        int index = int(x) + (int(y) * width);
        color col = img.pixels[index];

        return new Circle(x, y, col);
    }

    return null;
}

class Circle {
    float x, y, r;
    color c;
    boolean growMore = true;

    Circle (float x, float y, color c) {
        this.x = x;
        this.y = y;
        this.c = c;

        this.r = 1;
    }

    void doodle() {
        noStroke();
        fill(c);
        ellipse(x, y, r * 2, r * 2);
    }

    void increment() {
        if(growMore) {
            r += 0.5;
        }
    }

    boolean limitScreen() {
        return (x - r < 0 || x + r > width || y + r > height || y - r < 0);
    } 
}
