String instructions = "F";
float increase = 200;
float angle = PI/6;

//turttle instructions
//rules(F  ----- >  FF+[+F-F-F] - [-F+F+F])
Rule[] rules = new Rule[1];

void setup() {
    size(800, 600);
    rules[0] = new Rule('F', "FF+[+F-F-F] - [-F+F+F]");

    background(20);
    commonTurtle();
}

//it seems like if you don't add this, there isn't any event type working
void draw() {}

void mouseClicked() {
    increase *= 0.5;
    String newInstructions = "";
    for (int i = 0; i < instructions.length(); i++) {
        char actual = instructions.charAt(i);
        boolean isChanged = false;
        for (int j = 0; j < rules.length; j++) {
            if(actual == rules[j].a) {
                isChanged = true;
                newInstructions += rules[j].b;
                break;
            }
        }

        if(!isChanged) {
            newInstructions += actual;
        }
    }

    instructions = newInstructions;
    commonTurtle();
}

void commonTurtle() {
    resetMatrix();

    background(20);
    stroke(255, 150);

    translate(width/2, height);

    for (int i = 0; i < instructions.length(); i++) {
        char actual = instructions.charAt(i);

        switch (actual) {
            case 'F':
                line(0, 0, 0, -increase);
                translate(0, -increase);
                break;
            case '+':
                rotate(angle);
                break;
            case '-':
                rotate(-angle);
                break;
            case '[':
                pushMatrix();
                break;
            case ']':
                popMatrix();
                break;

            default:
                break;
        }
    }
}

class Rule {
    char a;
    String b;

    Rule(char a, String b) {
        this.a = a;
        this.b = b;
    }
}
