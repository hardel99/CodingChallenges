import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

Oscilation[] yFT;      //fourier transform for y axys
Oscilation[] xFT;      //fourier transform for x axys
float dt;           //time differential

float angle = 0;
ArrayList<PVector> actualPath = new ArrayList<PVector>();

void setup() {
    size(1200, 800);

    ArrayList<Float> inputSignalX = new ArrayList<Float>();
    ArrayList<Float> inputSignalY = new ArrayList<Float>();
    
    for (int a = 0; a < JustThePath.path.length; a++) {
        inputSignalX.add(JustThePath.path[a][0] / 7);
        inputSignalY.add(JustThePath.path[a][1] / 7);
    }
    
    yFT = FourierTransform.discreteFourierTransform(inputSignalY.toArray(new Float[inputSignalY.size()]));
    xFT = FourierTransform.discreteFourierTransform(inputSignalX.toArray(new Float[inputSignalX.size()]));
    
    Arrays.sort(xFT, new Comparator<Oscilation>() {
      @Override
      public int compare(Oscilation a, Oscilation b) {
        Float ampA = a.amplitude;
        Float ampB = b.amplitude;
        return ampB.compareTo(ampA);
      }
    });
    
    Arrays.sort(yFT, new Comparator<Oscilation>() {
      @Override
      public int compare(Oscilation a, Oscilation b) {
        Float ampA = a.amplitude;
        Float ampB = b.amplitude;
        return ampB.compareTo(ampA);
      }
    });
    
    dt = TWO_PI / yFT.length;
    
    noFill();
}

void draw() {
    background(0);

    PVector yComp = descomposeFrequency(100, height/2, HALF_PI, yFT);
    PVector xComp = descomposeFrequency(width/2, 80, 0, xFT);
    PVector tot = new PVector(xComp.x, yComp.y);
    
    actualPath.add(0, tot);

    line(xComp.x, xComp.y, tot.x, tot.y);
    line(yComp.x, yComp.y, tot.x, tot.y);
    beginShape();
    for (PVector curr : actualPath) {
        vertex(curr.x, curr.y);
    }
    endShape();

    if(angle < - TWO_PI) {
      angle = 0;
        actualPath.clear();
    }

    angle -= dt;
}

PVector descomposeFrequency(float x, float y, float dephase, Oscilation[] dft) {
    for (int i = 0; i < dft.length; i++) {
        float prevX = x;
        float prevY = y;

        float radius =  dft[i].amplitude;
        float frequency = dft[i].frequency;
        float phi = dft[i].phase;

        x += radius * cos(frequency * angle + phi + dephase);
        y += radius * sin(frequency * angle + phi + dephase);

        stroke(255, 80);
        ellipse(prevX, prevY, radius * 2,radius * 2);

        stroke(255);
        line(prevX, prevY, x, y);
    }

    return new PVector(x, y);
}

static class FourierTransform {
    
    FourierTransform() {
        //now im back
    }

    static Oscilation[] discreteFourierTransform(Float[] x) {
        //using actually math terminology
        final int N = x.length;                         //length of the signal
        Oscilation[] X = new Oscilation[N];                  //corrected value

        for (int k = 0; k < N; k++) {
            float re = 0;   //real component
            float im = 0;   //imaginary component

            for (int n = 0; n < N; n++) {
                float angle = (2 * PI * k * n)/ N;

                re += x[n] * cos(angle);
                im -= x[n] * sin(angle);
            }

            re /= N;
            im /= N;

             //another properties that describes a wave
            float frequency = k;               
            float amp = sqrt(re*re + im*im);
            float phi = atan2(im, re); 
            X[k] = new Oscilation();
            X[k].realPart = re;
            X[k].imaginaryPart = im;
            X[k].frequency = frequency;
            X[k].amplitude = amp;
            X[k].phase = phi;
        }

        return X;
    }
}

static class Oscilation{
  float amplitude = 0;
  float frequency = 0;
  float phase = 0;
  float realPart = 0;
  float imaginaryPart = 0;
  
  Oscilation() {
  }
}

static class JustThePath {
    static float[][] path = {{}}; //make your own path from a svg file o contact me for how to do it
}
