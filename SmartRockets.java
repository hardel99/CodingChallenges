Populate pop;
PVector piniata; 
int age = 0;

void setup() {
    size(500, 500);
    piniata = new PVector(width/2, 50);
    pop = new Populate(piniata);
}

void draw() {
    background(0);
    pop.run();
    
    age++;
    if(age >= 500) {
      pop.evaluate();
      pop.select();
      age = 0;
    }

    rect(250, 240, 300, 20);

    ellipse(piniata.x, piniata.y, 30, 30);
}





class Rocket {
    PVector pos, vel, acc;
    ADN adn;
    int counter = 0;
    float succes = 0;
    boolean isCompleted = false;
    boolean isCrahed = false;
    
    int rX = 100;
    int rY = 240;
    int rW = 300;
    int rH = 20;

    Rocket() {
        pos = new PVector(width/2, height);
        vel = new PVector();
        acc = new PVector();

        adn = new ADN();
    }

    Rocket(ADN adn) {
        pos = new PVector(width/2, height);
        vel = new PVector();
        acc = new PVector();

        this.adn = adn;
    }

    void addForce(PVector force) {
        acc.add(force);
    }

    void calcSucces(PVector target) {
        float dis = dist(pos.x, pos.y, target.x, target.y);
        succes = map(dis, 0, width, width, 0);

        if(isCompleted) {
            succes *= 10;
        }

        if(isCrahed) {
            succes /= 10;
        }
    }

    void refresh(PVector target) {
        float dis = dist(pos.x, pos.y, target.x, target.y);

        if(dis < 10) {
            isCompleted = true;
            pos = target.copy();
        }

        if(pos.x > rX && pos.x < rX + rW && pos.y > rY && pos.y < rY + rH) {
            isCrahed = true;
        }

        if(pos.x < 0 || pos.x > width) {
            isCrahed = true;
        }

        if(pos.y < 0 || pos.y > height) {
            isCrahed = true;
        }

        if(!isCompleted && !isCrahed) {
            if(counter < adn.genes.length) {
                addForce(adn.genes[counter]);
                counter++;
            } else{
            counter = 0;
            }

            vel.add(acc);
            pos.add(vel);

            acc.mult(0);
            vel.limit(4);
        }
    }

    void doodle() {
        pushMatrix();
        noStroke();
        fill(255, 200);
        translate(pos.x, pos.y);
        rotate(vel.heading());
        rectMode(CENTER);
        rect(0, 0, 20, 4);
        popMatrix();
    }
}





class Populate {
    Rocket[] rockets;
    int pob = 100;
    PVector target;
    ArrayList<Rocket> succesfully = new ArrayList<Rocket>();

    Populate(PVector target) {
        rockets = new Rocket[pob];
        this.target = target;

        for (int i = 0; i < rockets.length; i++) {
            rockets[i] = new Rocket();
        }
    }

    void evaluate() {
        float maxSucces = 0;

        for (int i = 0; i < rockets.length; ++i) {
            rockets[i].calcSucces(target);

            if(rockets[i].succes > maxSucces) {
                maxSucces = rockets[i].succes;
            }
        }

        for (int i = 0; i < rockets.length; ++i) {
            rockets[i].succes /= maxSucces;
        }

        succesfully.clear();

        for (int i = 0; i < rockets.length; ++i) {
            float n = rockets[i].succes * 100;

            for (int j = 0; j < n; ++j) {
                succesfully.add(rockets[i]);
            }
        }
    }

    void select() {
        Rocket[] newRockets = new Rocket[pob];

        for (int i = 0; i < newRockets.length; ++i) {
            Rocket parentA = succesfully.get(floor(random(succesfully.size())));
            Rocket parentB = succesfully.get(floor(random(succesfully.size())));

            ADN child = parentA.adn.crossover(parentB.adn);
            child.mutate();

            newRockets[i] = new Rocket(child);
        }

        rockets = newRockets;
    }

    void run() {
        for (int i = 0; i < rockets.length; i++) {
            rockets[i].refresh(target);
            rockets[i].doodle();
        }
    }
}




class ADN {
    int lifespawn = 500;
    PVector[] genes = new PVector[lifespawn];

    ADN() {
        for (int i = 0; i < genes.length; ++i) {
            genes[i] = PVector.random2D();
            genes[i].setMag(0.3);
        }
    }

    ADN(PVector[] genes) {
        this.genes = genes;
    }

    ADN crossover(ADN partnerGenes) {
        PVector[] newGenes = new PVector[lifespawn];
        int middle = floor(random(lifespawn));

        for (int i = 0; i < genes.length; ++i) {
            if(i > middle) {
                newGenes[i] = genes[i];
            } else{
                newGenes[i] = partnerGenes.genes[i];
            }
        }

        return new ADN(newGenes);
    }

    void mutate() {
        for (int i = 0; i < genes.length; ++i) {
            if(random(1) < 0.01) {
                genes[i] = PVector.random2D();
                genes[i].setMag(0.3);
            }
        }
    }
}
