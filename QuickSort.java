float[] data;

void setup() {
    size(1000, 600);

    data = new float[width];
    for (int i = 0; i < data.length; i++) {
        data[i] = random(height);
    }

    stroke(255);
    //sort dividing tha array on subsets
    quickSort(data, 0, data.length - 1);
}

void draw() {
    background(50);

    for (int k = 0; k < data.length; k++) {
        line(k, height, k, height - data[k]);
    }
}

void quickSort(float[] values, int start, int end) {
    if(start >= end) {
        return;
    }

    int index = splitArray(values, start, end);

    //recursion time
    quickSort(values, start, index - 1);
    quickSort(values, index + 1, end);
}

int splitArray(float[] values, int start, int end) {
    int pivotIndex = start;
    int pivotValue = values[end];

    for (int i = start; i < end; i++) {
        if (values[i] < pivotValue) {
            swapPositions(values, i, pivotIndex);
            pivotIndex++;
        }
    }

    swapPositions(values, pivotIndex, end);

    return pivotIndex;
}

void swapPositions(float[] values, int firstIndex, int secondIndex) {
    float temp = values[firstIndex];

    values[firstIndex] = values[secondIndex];
    values[secondIndex] = temp;
}
