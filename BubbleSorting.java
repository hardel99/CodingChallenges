float[] data;
int i = 0;

void setup() {
    size(1000, 600);

    data = new float[width];
    for (int i = 0; i < data.length; i++) {
        data[i] = random(height);
    }

    stroke(255);

    //sorting time
    //the algorithm for buuble sorting(slow and expensive sorting method)
    // for (int i = 0; i < data.length; i++) {
    //     for (int j = 0; j < data.length - i - 1; j++) {
    //         float a = data[j];
    //         float b = data[j + 1];

    //         if(a > b) {
    //             swapPositions(data, j, j + 1);
    //         }
    //     }
    // }
}

void draw() {
    background(50);

    for (int k = 0; k < data.length; k++) {
        line(k, height, k, height - data[k]);
    }

    if(i < data.length) {
        for (int j = 0; j < data.length - i - 1; j++) {
            float a = data[j];
            float b = data[j + 1];

            if(a > b) {
                swapPositions(data, j, j + 1);
            }
        }
        
        i++;
    } else{
        noLoop();
    }
}

void swapPositions(float[] values, int firstIndex, int secondIndex) {
    float temp = values[firstIndex];

    values[firstIndex] = values[secondIndex];
    values[secondIndex] = temp;
}
