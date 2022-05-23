package com.jasper.kroes;

import java.util.Random;

/**
 * Created by Jasper on 25-1-2018.
 */
public class LGCA {
    private final Node[][] grid;
    private final int width;
    private final int height;
    private final int beta;
    private int totalNoOfCells;
    private final double prolifA;
    private final double prolifB;
    private final double prolifC;

    public LGCA(int w, int h, int b, double prolA, double prolB, double prolC) {
        this.width = w;
        this.height = h;
        this.beta = b;
        this.totalNoOfCells = 0;
        this.prolifA = prolA;
        this.prolifB = prolB;
        this.prolifC = prolC;
        this.grid = new Node[width][height];

        //initializes the grid with nodes
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Node(beta);
            }
        }

        System.out.println("Grid initialized");
    }

    public void Update() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Node n = grid[i][j];
                //saves all the migrative cells to the new grid location
                if (n.getNorth() != null) {
                    grid[(i - 1 + width) % width][j].addToNewCells(n.getNorth());
                    n.setNorth(null);
                }
                if (n.getSouth() != null) {
                    grid[(i + 1 + width) % width][j].addToNewCells(n.getSouth());
                    n.setSouth(null);
                }
                if (n.getWest() != null) {
                    grid[i][(j - 1 + height) % height].addToNewCells(n.getWest());
                    n.setWest(null);
                }
                if (n.getEast() != null) {
                    grid[i][(j + 1 + height) % height].addToNewCells(n.getEast());
                    n.setEast(null);
                }
            }
        }
        //reassign all cells to a new spot in a node
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j].Update();
            }
        }
        //proliferate
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j].Proliferate();
            }
        }

    }

    public void addToGrid(Cell c) {
        if (totalNoOfCells < width * height * beta) {
            totalNoOfCells++;
            int w = new Random().nextInt(width);
            int h = new Random().nextInt(height);
            while (!grid[w][h].addCellToNode(c)) {
                if (new Random().nextDouble() < 0.5) {
                    w++;
                } else h++;
            }
        }
    }

    public String ShowResults() {
        int noOfProliferation = 0;
        int noOfMigrative = 0;
        int[][] count = new int[3][2];
        for (int i = 0; i < Type.size; i++) {
            count[i][1] = 0;
            count[i][0] = 0;
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                count = grid[i][j].CountStatesPerType(count);
            }
        }
        return ("Type A (Proliferation rate = " + prolifA + "):\n\t" + count[0][0] + " proliferation\n\t" + count[0][1] + " migration\n" +
                "Type B (Proliferation rate = " + prolifB + "):\n\t" + count[1][0] + " proliferation\n\t" + count[1][1] + " migration\n" +
                "Type C (Proliferation rate = " + prolifC + "):\n\t" + count[2][0] + " proliferation\n\t" + count[2][1] + " migration\n");
    }

    public String toString() {
        return grid.toString();
    }
}
