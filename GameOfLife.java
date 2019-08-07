int[][] grid, nextGen;
int size = 20;
int rows, cols;

void setup() {
    size(700, 700);
    cols = width/size;
    rows = height/size;

    grid = new int[cols][rows];

    for (int i = 0; i < grid.length; ++i) {
        for (int j = 0; j < grid[0].length; ++j) {
            grid[i][j] = floor(random(2));
        }
    }

    frameRate(10);
}

void draw() {
    background(50);

    for (int i = 0; i < grid.length; ++i) {
        for (int j = 0; j < grid[0].length; ++j) {
            int x = i * size;
            int y = j * size;

            if (grid[i][j] == 1) {
                fill(0);
            } else{
                fill(255);
            }

            stroke(0);
            rect(x, y, size - 1, size - 1);
        }
    }

    nextGen = grid.clone();

    for (int i = 0; i < grid.length; ++i) {
        for (int j = 0; j < grid[0].length; ++j) {
            int neighbours = lookAround(grid, i, j);

            int actualState = grid[i][j];
            
            if (actualState == 0) {
                //the cell its dead :(
                if(neighbours == 3) {
                    nextGen[i][j] = 1;
                } else{
                    nextGen[i][j] = 0;
                }
            } else{
                //the cell its alive :)
                if(neighbours < 2 || neighbours > 3) {
                    nextGen[i][j] = 0;
                } else{
                    nextGen[i][j] = 1;
                }
            }
        }
    }

    grid = nextGen.clone();
}

int lookAround(int[][] actual, int x, int y) {
    int counter = 0;

    for (int i = -1; i < 2; ++i) {
        for (int j = -1; j < 2; ++j) {
            int horizontal = (x + i + cols) % cols;
            int vertical = (y + j + rows) % rows;

            counter += actual[horizontal][vertical];
        }
    }

    counter -= actual[x][y];

    return counter;
}
