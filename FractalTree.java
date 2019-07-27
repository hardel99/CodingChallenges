float angle = PI/6;

void setup() {
    size(1200, 630);
}

void draw() {
    background(30);

    stroke(245);
    translate(width/2, height);
    float branch = 200;
    branching(branch);
}

void branching(float branchSize) {
    line(0, 0, 0, - branchSize);
    translate(0, - branchSize);
    if(branchSize > 4) {
        push();
        rotate(angle);
        branching(branchSize * 0.7);
        pop();
        push();
        rotate(-angle);
        branching(branchSize * 0.7);
        pop();
    }
}
