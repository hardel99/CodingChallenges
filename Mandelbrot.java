void setup() {
    size(700, 700);
    loadPixels();
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            int index = (i + j * width);
            float re = map(i, 0, width, -2, 1);
            float im = map(j, 0, height, -1.5, 1.5);

            float x = re;
            float y = im;
            
            int k = 0;
            for (k = 0; k < 100; k++) {
                float _re = re*re - im*im;
                float _im = 2 * re * im;

                re = _re + x;
                im = _im + y;

                if(abs(re + im) > 100) {
                    break;
                }
            }

            float colorMAP = map(k, 0, 100, 0, 255);
            if(k == 100) {
              colorMAP = 0;
            }

            pixels[index] = color(colorMAP, 255);
        }
    }
    updatePixels();
}
