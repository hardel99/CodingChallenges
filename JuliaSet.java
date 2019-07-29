float zoom = 0;

void setup() {
    size(900, 700);
    colorMode(HSB);
}

void draw() {
  background(255);
  
  loadPixels();
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            int index = (i + j * width);
            float re = map(i, 0, width, -2, 2);
            float im = map(j, 0, height, -1.5, 1.5);

            /*float x = map(mouseX, 0, width, -1, 1);
            float y = map(mouseY, 0, height, -1, 1);*/
            
            float x = cos(zoom);
            float y = sin(zoom);
            
            int k = 0;
            for (k = 0; k < 100; k++) {
                float _re = re*re - im*im;
                float _im = 2 * re * im;

                re = _re + x;
                im = _im + y;

                if(abs(re + im) > 20) {
                    break;
                }
            }

            float colorMAP = map(k, 0, 100, 0, 255);
            if(k == 100) {
              colorMAP = 0;
            }

            pixels[index] = color(colorMAP, 255, 255);
        }
    }
    updatePixels();
    
    zoom += 0.01;
}
