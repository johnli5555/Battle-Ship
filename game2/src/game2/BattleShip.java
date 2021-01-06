package game2;

import java.io.PrintWriter;
import java.io.File;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
/*
 * BattleShip is the class that executes the game. It is called from the menu class
 */
public class BattleShip extends JFrame implements ActionListener
{
	// Initializing variables, arrays, buttons, panels, buttons.
    public String[] letters;
    public ArrayList<String> shipNames;
    public ArrayList<Double> shipTypes;
    public ArrayList<int[]> huntCoor;
    public int[] computersShip;
    public int[] playersShip;
    public int[] hitting;
    public int[][] huntReport;
    public int maxPriority;
    public int indexPriority;
    public int[] returnVar;
    boolean search;
    public String[] huntDirection;
    public double[] lastHitShip;
    public boolean justSank;
    public ArrayList<ArrayList<JButton>> gameGrid;
    static final int ROW = 10;
    static final int COL = 10;
    public static int[][] placedShip;
    public int[][] probGrid;
    public static double[][] playerShip;
    public int difficulty;
    public int timeSec;
    public int timeMin;
    public boolean started;
    public boolean playerTurn;
    public int direction;
    public int x;
    public int y;
    public int side;
    public boolean detected;
    public int firstTurn;
    public double hitResult;
    public int hitsOf3;
    public int[] playerHitsOf3;
    public JPanel boxPan;
    public JPanel lognButton;
    public JButton exit;
    public JButton start;
    public JScrollPane log;
    public JPanel logRoom;
    public JLabel logLabel;
    public JButton yes;
    public JButton no;
    public JLabel confirmation;
    public JLabel whichShip;
    public Timer time;
    
    static {
        BattleShip.placedShip = new int[10][10];
        BattleShip.playerShip = new double[10][10];
    }
    
    // Setting the properties of the initialized variables
    public BattleShip(final int diff) {
        this.letters = new String[] { "k", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
        this.shipNames = new ArrayList<String>();
        this.shipTypes = new ArrayList<Double>();
        this.huntCoor = new ArrayList<int[]>();
        this.computersShip = new int[] { 5, 4, 3, 3, 2 };
        this.playersShip = new int[] { 5, 4, 3, 3, 2 };
        this.hitting = new int[2];
        this.huntReport = new int[5][5];
        this.maxPriority = 0;
        this.indexPriority = 0;
        this.returnVar = new int[2];
        this.search = true;
        this.huntDirection = new String[5];
        this.lastHitShip = new double[1];
        this.justSank = false;
        this.gameGrid = new ArrayList<ArrayList<JButton>>();
        this.probGrid = new int[10][10];
        this.timeSec = 0;
        this.timeMin = 0;
        this.started = false;
        this.playerTurn = false;
        this.detected = false;
        this.hitResult = -1.0;
        this.hitsOf3 = 0;
        this.playerHitsOf3 = new int[1];
        this.boxPan = new JPanel();
        this.lognButton = new JPanel();
        this.exit = new JButton("Exit");
        this.start = new JButton("Start");
        this.log = new JScrollPane();
        this.logRoom = new JPanel();
        this.logLabel = new JLabel("Hi, here is the log for this game.");
        this.yes = new JButton("YES");
        this.no = new JButton("no");
        this.confirmation = new JLabel("Was it a hit?");
        this.whichShip = new JLabel("Which ship?");
        this.time = null;
        for (int i = 1; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                this.huntReport[j][i] = 0;
            }
            this.huntDirection[i] = "und";
        }
        this.huntReport[0][0] = 2;
        this.huntReport[1][0] = 3;
        this.huntReport[2][0] = 3;
        this.huntReport[3][0] = 4;
        this.huntReport[4][0] = 5;
        for (int i = 0; i < BattleShip.placedShip.length; ++i) {
            for (int j = 0; j < BattleShip.placedShip[i].length; ++j) {
                BattleShip.placedShip[i][j] = 0;
                this.probGrid[i][j] = 0;
                BattleShip.playerShip[i][j] = 0.0;
            }
        }
        this.ShipPlacing(this.difficulty = diff);
        this.logShips();
        this.shipNames.add("Carrier");
        this.shipNames.add("Battleship");
        this.shipNames.add("Submarine");
        this.shipNames.add("Cruiser");
        this.shipNames.add("Destroyer");
        this.shipNames.add("None");
        this.shipTypes.add(5.0);
        this.shipTypes.add(4.0);
        this.shipTypes.add(3.1);
        this.shipTypes.add(3.2);
        this.shipTypes.add(2.0);
        this.setUndecorated(true);
        this.setSize(980, 460);
        this.setTitle("BATTLESHIP");
        this.setBackground(Color.WHITE);
        this.getRootPane().setWindowDecorationStyle(0);
        this.setResizable(false);
        final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        final int dimX = (int)((dimension.getWidth() - this.getWidth()) / 2.0);
        final int dimY = (int)((dimension.getHeight() - this.getHeight()) / 2.0);
        this.setLocation(dimX, dimY);
        final GridLayout gridLay = new GridLayout(11, 11);
        final FlowLayout general = new FlowLayout();
        final BoxLayout logLay = new BoxLayout(this.lognButton, 1);
        final BoxLayout logRoomLay = new BoxLayout(this.logRoom, 1);
        this.boxPan.setLayout(gridLay);
        this.logRoom.setLayout(logRoomLay);
        this.lognButton.setLayout(logLay);
        this.setLayout(general);
        this.log.setVerticalScrollBarPolicy(22);
        this.log.setHorizontalScrollBarPolicy(30);
        this.log.setViewportView(this.logRoom);
        this.log.setPreferredSize(new Dimension(240, 360));
        this.exit.setPreferredSize(new Dimension(40, 40));
        this.exit.setBackground(Color.white);
        this.exit.addActionListener(this);
        this.start.setPreferredSize(new Dimension(40, 40));
        this.start.setBackground(Color.white);
        this.start.addActionListener(this);
        this.logRoom.add(this.logLabel);
        this.lognButton.add(this.log, new GridBagConstraints());
        this.lognButton.add(this.start, new GridBagConstraints());
        this.lognButton.add(this.exit, new GridBagConstraints());
        this.log.setAlignmentX(0.5f);
        this.start.setAlignmentX(0.5f);
        this.exit.setAlignmentX(0.5f);
        final Dimension buttonDim = new Dimension(60, 40);
        for (int k = 0; k < 11; ++k) {
            this.gameGrid.add(new ArrayList<JButton>());
            for (int l = 0; l < 11; ++l) {
                if (k == 0 && l > 0) {
                    this.gameGrid.get(k).add(new JButton(String.valueOf(l)));
                    this.gameGrid.get(k).get(l).addActionListener(this);
                }
                else if (k > 0 && l == 0) {
                    this.gameGrid.get(k).add(new JButton(this.letters[k]));
                    this.gameGrid.get(k).get(l).addActionListener(this);
                }
                else {
                    this.gameGrid.get(k).add(new JButton(""));
                    this.gameGrid.get(k).get(l).addActionListener(this);
                }
                this.boxPan.add(this.gameGrid.get(k).get(l), new GridBagConstraints());
                this.gameGrid.get(k).get(l).setBackground(Color.white);
                this.gameGrid.get(k).get(l).setPreferredSize(buttonDim);
            }
        }
        this.add(this.boxPan);
        this.add(this.lognButton);
        this.boxPan.setVisible(true);
        this.lognButton.setVisible(true);
        this.setVisible(true);
    }
    
    /*-----------------------------------------------------------------------------------------------------------------
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * Processes the interface of the game before it starts (activates immediately after user clicks start
     * In menu. Also contains the code that prompts the user who goes first (or random)
     */
    public void actionPerformed(final ActionEvent event) {
        final String command = event.getActionCommand();
        if (command.equals("Start") && !this.started) {
            final int randOrNot = JOptionPane.showOptionDialog(null, "Determine randomly who goes first?", "Welcome", 0, -1, null, null, null);
            if (randOrNot == 0) {
                this.firstTurn = (int)(Math.random() * 2.0);
            }
            else {
                this.firstTurn = JOptionPane.showOptionDialog(null, "Player goes first?", "Welcome", 0, -1, null, null, null);
            }
            if (this.firstTurn == 0) {
                this.logRoom.add(new JLabel("Player goes first!"));
                JOptionPane.showMessageDialog(null, "Player goes first!");
                this.playerTurn = true;
            }
            else {
                this.logRoom.add(new JLabel("Computer goes first!"));
                JOptionPane.showMessageDialog(null, "Computer goes first!");
                this.playerTurn = false;
                if (this.difficulty == 1) {
                    this.hitResult = this.computerHit(this.hitResult);
                }
                else {
                    this.hitResult = this.computerHitSimple();
                }
                this.playerTurn = true;
            }
            this.started = true;
        }
        else if (command.equals("Exit")) {
            this.dispose();
        }
        if (command.equals("") && this.started && this.playerTurn) {
            for (int i = 1; i < this.gameGrid.size(); ++i) {
                for (int j = 1; j < this.gameGrid.size(); ++j) {
                    if (this.gameGrid.get(i).get(j) == event.getSource()) {
                    	this.logRoom.validate();
                        this.logRoom.add(new JLabel("Player hits " + this.letters[i] + j + "!"));
                        if (BattleShip.placedShip[i - 1][j - 1] != 0) {
                            JOptionPane.showMessageDialog(null, "You hit!");
                            this.determinePlayerHit(BattleShip.placedShip[i - 1][j - 1]);
                            this.gameGrid.get(i).get(j).setBackground(Color.RED);
                        }
                        else {
                        	this.logRoom.validate();
                            this.logRoom.add(new JLabel("You missed"));
                            JOptionPane.showMessageDialog(null, "You missed!");
                            this.gameGrid.get(i).get(j).setBackground(Color.BLUE);
                        }
                    }
                }
            }
            this.playerTurn = false;
            if (this.difficulty == 1) {
                this.hitResult = this.computerHit(this.hitResult);
            }
            else {
                this.hitResult = this.computerHitSimple();
            }
            this.playerTurn = true;
        }
    }
    
    /*-----------------------------------------------------------------------------------------------------------------
     * ShipPlacing places the computer ships randomly on the board
     */
    public void ShipPlacing(final int newDifficulty) {
        int size = 2;
        while (size != 6) {
                if (size == 3) {
                    for (int i = 0; i < 2; ++i) {
                        this.randomize();
                        while (!this.canPlace(this.x, this.y, this.direction, size)) {
                            this.randomize();
                        }
                        this.place(this.x, this.y, this.direction, size);
                    }
                }
                else {
                    this.randomize();
                    while (!this.canPlace(this.x, this.y, this.direction, size)) {
                        this.randomize();
                    }
                    this.place(this.x, this.y, this.direction, size);
                }
                ++size;
         }
     }
    
    /*
     * canPlace is a helper function of shipPlacing that determines whether or not a ship 
     * can be placed with the random staring point and direction. Some randomly generated  
     * ships will go off the board or overlap with other existing ships and that is not allowed
     */
    public boolean canPlace(int r, int c, final int d, final int newSize) {
        boolean check = true;
        if (d == 0) {
            if (r - (newSize - 1) < 0) {
                check = false;
            }
            else {
                for (int i = 0; i < newSize; ++i) {
                    if (BattleShip.placedShip[r][c] != 0) {
                        check = false;
                    }
                    --r;
                }
            }
        }
        else if (d == 1) {
            if (c + (newSize + 1) >= 10) {
                check = false;
            }
            else {
                for (int i = 0; i < newSize; ++i) {
                    if (BattleShip.placedShip[r][c] != 0) {
                        check = false;
                    }
                    ++c;
                }
            }
        }
        else if (d == 2) {
            if (r + (newSize - 1) >= 10) {
                check = false;
            }
            else {
                for (int i = 0; i < newSize; ++i) {
                    if (BattleShip.placedShip[r][c] != 0) {
                        check = false;
                    }
                    ++r;
                }
            }
        }
        else if (d == 3) {
            if (c - (newSize - 1) < 0) {
                check = false;
            }
            else {
                for (int i = 0; i < newSize; ++i) {
                    if (BattleShip.placedShip[r][c] != 0) {
                        check = false;
                    }
                    --c;
                }
            }
        }
        return check;
    }
    
    /*
     * Place is a helper function of shipPlacing places a computer ship on the computer board  
     * given a starting point (r, c) a direction d and a size newsize
     */
    public void place(int r, int c, final int d, final int newSize) {
        if (d == 0) {
            for (int a = 0; a < newSize; ++a) {
                BattleShip.placedShip[r][c] = newSize;
                --r;
            }
        }
        else if (d == 1) {
            for (int a = 0; a < newSize; ++a) {
                BattleShip.placedShip[r][c] = newSize;
                ++c;
            }
        }
        else if (d == 2) {
            for (int a = 0; a < newSize; ++a) {
                BattleShip.placedShip[r][c] = newSize;
                ++r;
            }
        }
        else if (d == 3) {
            for (int a = 0; a < newSize; ++a) {
                BattleShip.placedShip[r][c] = newSize;
                --c;
            }
        }
    }
    
    /*
     * Randomize is a helper function of ShipPlacing generates a random ship with staring point and direction
     */
    public void randomize() {
        final Random rand = new Random();
        this.side = (int)(rand.nextDouble() * 4.0);
        this.direction = (int)(rand.nextDouble() * 4.0);
        this.x = (int)(rand.nextDouble() * 10.0);
        this.y = (int)(rand.nextDouble() * 10.0);
    }
    
    /*-----------------------------------------------------------------------------------------------------------------
     * The simple computer hit hits completely randomly on a square that has not yet been hit
     */
    public double computerHitSimple() {
        int x;
        int y;
        do {
            x = (int)(Math.random() * 10.0);
            y = (int)(Math.random() * 10.0);
        } while (BattleShip.playerShip[x][y] != 0.0);
        this.hitting[0] = x;
        this.hitting[1] = y;
        final double result = this.determineComputerHit();
        return result;
    }
    
    /*-----------------------------------------------------------------------------------------------------------------
     * The advanced computer hit is a 2-step algorithm that find the location of the ship(search) then
     * finds its orientation(hunt). These modes are determined by priority
     */
    public double computerHit(double result) {
        // This is what happens when the computer sinks a ship. Everything is reset so that search
    	// can find another ship
        if (!this.detected) {
            if (this.playersShip[4] == 0) {
                this.huntCoor.clear();
                this.huntReport[0][1] = 0;
                this.maxPriority = 0;
                for (int i = 0; i < 5; ++i) {
                    if (this.huntReport[i][1] > this.maxPriority) {
                        this.maxPriority = this.huntReport[i][1];
                        this.indexPriority = i;
                    }
                }
                if (this.maxPriority == 0) {
                    this.search = true;
                }
            }
            if (this.playersShip[2] == 0) {
                this.huntCoor.clear();
                this.huntReport[2][1] = 0;
                this.maxPriority = 0;
                for (int i = 0; i < 5; ++i) {
                    if (this.huntReport[i][1] > this.maxPriority) {
                        this.maxPriority = this.huntReport[i][1];
                        this.indexPriority = i;
                    }
                }
                if (this.maxPriority == 0) {
                    this.search = true;
                }
            }
            if (this.playersShip[3] == 0) {
                this.huntCoor.clear();
                this.huntReport[1][1] = 0;
                this.maxPriority = 0;
                for (int i = 0; i < 5; ++i) {
                    if (this.huntReport[i][1] > this.maxPriority) {
                        this.maxPriority = this.huntReport[i][1];
                        this.indexPriority = i;
                    }
                }
                if (this.maxPriority == 0) {
                    this.search = true;
                }
            }
            if (this.playersShip[1] == 0) {
                this.huntCoor.clear();
                this.huntReport[3][1] = 0;
                this.maxPriority = 0;
                for (int i = 0; i < 5; ++i) {
                    if (this.huntReport[i][1] > this.maxPriority) {
                        this.maxPriority = this.huntReport[i][1];
                        this.indexPriority = i;
                    }
                }
                if (this.maxPriority == 0) {
                    this.search = true;
                }
            }
            if (this.playersShip[0] == 0) {
                this.huntCoor.clear();
                this.huntReport[4][1] = 0;
                this.maxPriority = 0;
                for (int i = 0; i < 5; ++i) {
                    if (this.huntReport[i][1] > this.maxPriority) {
                        this.maxPriority = this.huntReport[i][1];
                        this.indexPriority = i;
                    }
                }
                if (this.maxPriority == 0) {
                    this.search = true;
                }
            }
        }
        // If there is not ship detected, the computer enters search mode
        if (this.search) {
            this.hitting = this.search();
            this.logRoom.validate();
            this.logRoom.add(new JLabel("The Computer hits " + this.letters[this.hitting[0] + 1] + (this.hitting[1] + 1) + "!"));
            result = this.determineComputerHit();
            BattleShip.playerShip[this.hitting[0]][this.hitting[1]] = result;
            // If the computer hits a ship, it enters hunt mode 
            if (BattleShip.playerShip[this.hitting[0]][this.hitting[1]] >= 2.0) {
                this.detected = true;
                this.search = false;
            }
        }
        // Hunt mode (kill mode is part of hunt mode)
        else {
            this.hunt(this.hitting, result);
            this.logRoom.validate();
            this.logRoom.add(new JLabel("The Computer hits " + this.letters[this.hitting[0] + 1] + (this.hitting[1] + 1) + "!"));
            result = this.determineComputerHit();
            BattleShip.playerShip[this.hitting[0]][this.hitting[1]] = result;
            this.detected = false;
        }
        if (IntStream.of(this.playersShip).sum() != 0 && IntStream.of(this.computersShip).sum() != 0) {
            JOptionPane.showMessageDialog(null, "Your Turn!");
        }
        return result;
    }
    
    /*
     * ----------------------------------------------------------------------------------------------------------------
     * Search is an algorithm used by the advanced AI in order to try and find player ships. In Search mode,
     * the computer either has not found any ships or any ship that has been found has been completely sunk.
     * If there is a partially sunk ship the AI will engage in hunt mode. 
     */
    public int[] search() {
    	// determines the probability of a ship existing on each square
        this.determineProbability();
        int largestX = 0;
        int largestY = 0;
        for (int i = 0; i < this.probGrid.length; ++i) {
            for (int j = 0; j < this.probGrid[i].length; ++j) {
                if (this.probGrid[i][j] > this.probGrid[largestX][largestY]) {
                    largestX = i;
                    largestY = j;
                }
            }
        }
        final int[] result = { largestX, largestY };
        return result;
    }
    
    /*
     * determineProbability is the helper function of search that determines the probability of
     * a ship existing on a specific square. This probability is computed by counting the total
     * number of ways that a ship can exist on that square.
     */
    public void determineProbability() {
        for (int i = 0; i < this.probGrid.length; ++i) {
            for (int j = 0; j < this.probGrid[i].length; ++j) {
                this.probGrid[i][j] = 0;
            }
        }
        for (int i = 0; i < BattleShip.playerShip.length; ++i) {
            for (int j = 0; j < BattleShip.playerShip[i].length; ++j) {
                for (int p = 0; p < this.shipTypes.size(); ++p) {
                    final double shipTypeInt = this.shipTypes.get(p);
                    final int lX = i + (int)shipTypeInt - 1;
                    if (lX <= BattleShip.playerShip.length - 1) {
                        boolean areaOccupied = false;
                        for (int k = i; k <= lX; ++k) {
                            if (BattleShip.playerShip[k][j] != 0.0) {
                                areaOccupied = true;
                                break;
                            }
                        }
                        if (!areaOccupied) {
                            for (int k = i; k <= lX; ++k) {
                                final int[] array = this.probGrid[k];
                                final int n = j;
                                ++array[n];
                            }
                        }
                    }
                }
                for (int p = 0; p < this.shipTypes.size(); ++p) {
                    final double shipTypeInt = this.shipTypes.get(p);
                    final int lY = j + (int)shipTypeInt - 1;
                    if (lY <= BattleShip.playerShip[i].length - 1) {
                        boolean areaOccupied = false;
                        for (int k = j; k <= lY; ++k) {
                            if (BattleShip.playerShip[i][k] != 0.0) {
                                areaOccupied = true;
                                break;
                            }
                        }
                        if (!areaOccupied) {
                            for (int k = j; k <= lY; ++k) {
                                final int[] array2 = this.probGrid[i];
                                final int n2 = k;
                                ++array2[n2];
                            }
                        }
                    }
                }
            }
        }
    }
    
    /*-----------------------------------------------------------------------------------------------------------------
     * Hunt is an algorithm used to find the direction of a ship and then sink it 
     * It is the most complicated algorithm because it has to deal with a lot of 
     * fringe case scenarios.
     */
    public void hunt(final int[] cor, final double result) {
        final int[] returnVar = cor;
        // getting information to update HuntReport
        int newResult = -1;
        if (result == 5.0) {
            newResult = 4;
        }
        if (result == 4.0) {
            newResult = 3;
        }
        if (result == 3.1) {
            newResult = 2;
        }
        if (result == 3.2) {
            newResult = 1;
        }
        if (result == 2.0) {
            newResult = 0;
        }
        this.maxPriority = 0;
        for (int k = 0; k < 5; ++k) {
            if (this.huntReport[k][1] > this.maxPriority) {
                this.maxPriority = this.huntReport[k][1];
                this.indexPriority = k;
            }
        }
        final int corX = this.hitting[0];
        final int corY = this.hitting[1];
        if (this.maxPriority == 0) {
            ++this.maxPriority;
            this.huntReport[newResult][1] = this.maxPriority;
            this.indexPriority = newResult;
            this.huntReport[newResult][2] = corX;
            this.huntReport[newResult][3] = corY;
            String direction = "l";
            int maxD = getLength(corX, corY, "l");
            if (getLength(corX, corY, "r") > maxD) {
                maxD = getLength(corX, corY, "r");
                direction = "r";
            }
            if (getLength(corX, corY, "u") > maxD) {
                maxD = getLength(corX, corY, "u");
                direction = "u";
            }
            if (getLength(corX, corY, "d") > maxD) {
                maxD = getLength(corX, corY, "d");
                direction = "d";
            }
            this.huntDirection[newResult] = direction;
        }
        else if (result > 0.0) {
            this.search = false;
            // If hunt finds a new ship it will update HuntReport
            if (this.indexPriority != newResult) {
                ++this.maxPriority;
                this.huntReport[newResult][1] = this.maxPriority;
                this.indexPriority = newResult;
                this.huntReport[newResult][2] = corX;
                this.huntReport[newResult][3] = corY;
                String direction = "l";
                int maxD = getLength(corX, corY, "l");
                if (getLength(corX, corY, "r") > maxD) {
                    maxD = getLength(corX, corY, "r");
                    direction = "r";
                }
                if (getLength(corX, corY, "u") > maxD) {
                    maxD = getLength(corX, corY, "u");
                    direction = "u";
                }
                if (getLength(corX, corY, "d") > maxD) {
                    maxD = getLength(corX, corY, "d");
                    direction = "d";
                }
                if (getLength(corX, corY, "l") > maxD) {
                	maxD = getLength(corX, corY, "l");
                	direction = "l";
                }
                // generating a direction for hunting
                this.huntDirection[newResult] = direction;
            }
            else {
            	// If the ship's orientation has already been determined (hit twice) then keep hitting in that direction
            	// until it misses. Then it flips and keeps hitting until the ship is sunk
                this.huntReport[this.indexPriority][4] = 1;
                if (this.huntDirection[this.indexPriority].equals("l")) {
                    if (this.hitting[0] != 0) {
                        if (BattleShip.playerShip[this.hitting[0] - 1][this.hitting[1]] != 0.0) {
                            this.huntDirection[this.indexPriority] = this.flip(this.huntDirection[this.indexPriority]);
                        }
                    }
                    else {
                        this.huntDirection[this.indexPriority] = this.flip(this.huntDirection[this.indexPriority]);
                    }
                }
                if (this.huntDirection[this.indexPriority].equals("r")) {
                    if (this.hitting[0] != 9) {
                        if (BattleShip.playerShip[this.hitting[0] + 1][this.hitting[1]] != 0.0) {
                            this.huntDirection[this.indexPriority] = this.flip(this.huntDirection[this.indexPriority]);
                        }
                    }
                    else {
                        this.huntDirection[this.indexPriority] = this.flip(this.huntDirection[this.indexPriority]);
                    }
                }
                if (this.huntDirection[this.indexPriority].equals("u")) {
                    if (this.hitting[1] != 0) {
                        if (BattleShip.playerShip[this.hitting[0]][this.hitting[1] - 1] != 0.0) {
                            this.huntDirection[this.indexPriority] = this.flip(this.huntDirection[this.indexPriority]);
                        }
                    }
                    else {
                        this.huntDirection[this.indexPriority] = this.flip(this.huntDirection[this.indexPriority]);
                    }
                }
                if (this.huntDirection[this.indexPriority].equals("d")) {
                    if (this.hitting[1] != 9) {
                        if (BattleShip.playerShip[this.hitting[0]][this.hitting[1] + 1] != 0.0) {
                            this.huntDirection[this.indexPriority] = this.flip(this.huntDirection[this.indexPriority]);
                        }
                    }
                    else {
                        this.huntDirection[this.indexPriority] = this.flip(this.huntDirection[this.indexPriority]);
                    }
                }
            }
        }
        
        // If the ship has only been hit once, then the computer finds the orientation by hitting
        // and rotating if it misses
        else if (this.huntReport[this.indexPriority][4] == 0) {
            this.huntDirection[this.indexPriority] = this.rotate(this.huntReport[this.indexPriority][2], this.huntReport[this.indexPriority][3], this.huntDirection[this.indexPriority]);
        }
        else {
            this.huntDirection[this.indexPriority] = this.flip(this.huntDirection[this.indexPriority]);
        }
        if (this.huntDirection[this.indexPriority].equals("l")) {
            boolean ok = false;
            int l = 1;
            while (!ok) {
                if (BattleShip.playerShip[this.huntReport[this.indexPriority][2] - l][this.huntReport[this.indexPriority][3]] == 0.0) {
                    ok = true;
                    this.hitting[0] = this.huntReport[this.indexPriority][2] - l;
                    this.hitting[1] = this.huntReport[this.indexPriority][3];
                }
                else {
                    ++l;
                }
            }
        }
        if (this.huntDirection[this.indexPriority].equals("r")) {
            boolean ok = false;
            int l = 1;
            while (!ok) {
                if (BattleShip.playerShip[this.huntReport[this.indexPriority][2] + l][this.huntReport[this.indexPriority][3]] == 0.0) {
                    ok = true;
                    this.hitting[0] = this.huntReport[this.indexPriority][2] + l;
                    this.hitting[1] = this.huntReport[this.indexPriority][3];
                }
                else {
                    ++l;
                }
            }
        }
        if (this.huntDirection[this.indexPriority].equals("u")) {
            boolean ok = false;
            int l = 1;
            while (!ok) {
                if (BattleShip.playerShip[this.huntReport[this.indexPriority][2]][this.huntReport[this.indexPriority][3] - l] == 0.0) {
                    ok = true;
                    this.hitting[0] = this.huntReport[this.indexPriority][2];
                    this.hitting[1] = this.huntReport[this.indexPriority][3] - l;
                }
                else {
                    ++l;
                }
            }
        }
        if (this.huntDirection[this.indexPriority].equals("d")) {
            boolean ok = false;
            int l = 1;
            while (!ok) {
                if (BattleShip.playerShip[this.huntReport[this.indexPriority][2]][this.huntReport[this.indexPriority][3] + l] == 0.0) {
                    ok = true;
                    this.hitting[0] = this.huntReport[this.indexPriority][2];
                    this.hitting[1] = this.huntReport[this.indexPriority][3] + l;
                }
                else {
                    ++l;
                }
            }
        }
    }
    

    /*-----------------------------------------------------------------------------------------------------------------
     * determineComputerHit processes user inputs regarding computer hits and calls the corresponding 
     * functions. It also manages the log room
     */
    public double determineComputerHit() {
        double result = -1.0;
        int outcome = 0;
        String ship = "";
        if (IntStream.of(this.playersShip).sum() != 0 && IntStream.of(this.computersShip).sum() != 0) {
            outcome = JOptionPane.showOptionDialog(null, "The Computer hits " + this.letters[this.hitting[0] + 1] + (this.hitting[1] + 1) + "! HIT or MISS?", "Outcome", 0, -1, null, null, null);
        }
        if (outcome == 0) {
            this.search = false;
            if (IntStream.of(this.playersShip).sum() != 0 && IntStream.of(this.computersShip).sum() != 0) {
                ship = (String)JOptionPane.showInputDialog(null, "Which ship is hit?", "", 0, null, this.shipNames.toArray(), "");
            }
            if (ship == null) {
                ship = "None";
            }
            if (ship.equals("Carrier")) {
                result = 5.0;
                final int[] playersShip = this.playersShip;
                final int n = 0;
                --playersShip[n];
                this.lastHitShip[0] = 5.0;
                if (this.playersShip[0] == 0) {
                    this.shipNames.remove("Carrier");
                    this.shipTypes.remove(new Double(5.0));
                    this.logRoom.validate();
                    this.logRoom.add(new JLabel("Computer sunk Carrier!"));
                    JOptionPane.showMessageDialog(null, "Computer sunk Carrier!");
                }
                else {
                	this.logRoom.validate();
                    this.logRoom.add(new JLabel("Computer hit Carrier!"));
                    JOptionPane.showMessageDialog(null, "Computer hit Carrier!");
                }
            }
            else if (ship.equals("Battleship")) {
                result = 4.0;
                final int[] playersShip2 = this.playersShip;
                final int n2 = 1;
                --playersShip2[n2];
                this.lastHitShip[0] = 4.0;
                if (this.playersShip[1] == 0) {
                    this.shipNames.remove("Battleship");
                    this.shipTypes.remove(new Double(4.0));
                    this.logRoom.validate();
                    this.logRoom.add(new JLabel("Computer sunk battleShip!"));
                    JOptionPane.showMessageDialog(null, "Computer sunk battleship!");
                }
                else {
                	this.logRoom.validate();
                    this.logRoom.add(new JLabel("Computer hit Battleship!"));
                    JOptionPane.showMessageDialog(null, "Computer hit Battleship!");
                }
            }
            else if (ship.equals("Cruiser")) {
                result = 3.1;
                final int[] playersShip3 = this.playersShip;
                final int n3 = 2;
                --playersShip3[n3];
                this.lastHitShip[0] = 3.1;
                if (this.playersShip[2] == 0) {
                    this.playerHitsOf3[0] = 1;
                    this.shipNames.remove("Cruiser");
                    this.shipTypes.remove(new Double(3.1));
                    this.logRoom.validate();
                    this.logRoom.add(new JLabel("Computer sunk Cruiser!"));
                    JOptionPane.showMessageDialog(null, "Computer sunk Cruiser!");
                }
                else {
                	this.logRoom.validate();
                    this.logRoom.add(new JLabel("Computer hit Cruiser!"));
                    JOptionPane.showMessageDialog(null, "Computer hit Cruiser!");
                }
            }
            else if (ship.equals("Submarine")) {
                result = 3.2;
                final int[] playersShip4 = this.playersShip;
                final int n4 = 3;
                --playersShip4[n4];
                this.lastHitShip[0] = 3.2;
                if (this.playersShip[3] == 0) {
                    this.shipNames.remove("Submarine");
                    this.shipTypes.remove(new Double(3.2));
                    this.logRoom.validate();
                    this.logRoom.add(new JLabel("Computer sunk Submarine!"));
                    JOptionPane.showMessageDialog(null, "Computer sunk Submarine!");
                }
                else {
                	this.logRoom.validate();
                    this.logRoom.add(new JLabel("Computer hit Submarine!"));
                    JOptionPane.showMessageDialog(null, "Computer hit Submarine!");
                }
            }
            else if (ship.equals("Destroyer")) {
                result = 2.0;
                final int[] playersShip5 = this.playersShip;
                final int n5 = 4;
                --playersShip5[n5];
                this.lastHitShip[0] = 2.0;
                if (this.playersShip[4] == 0) {
                    this.shipNames.remove("Destroyer");
                    this.shipTypes.remove(new Double(2.0));
                    this.logRoom.validate();
                    this.logRoom.add(new JLabel("Computer sunk Destroyer!"));
                    JOptionPane.showMessageDialog(null, "Computer sunk Destroyer!");
                }
                else {
                	this.logRoom.validate();
                    this.logRoom.add(new JLabel("Computer hit Destroyer!"));
                    JOptionPane.showMessageDialog(null, "Computer hit Destroyer!");
                }
            }
        }
        else {
            result = -1.0;
            this.logRoom.validate();
            this.logRoom.add(new JLabel("Computer Missed!"));
            JOptionPane.showMessageDialog(null, "Computer Missed!");
        }
        if (IntStream.of(this.playersShip).sum() == 0) {
            JOptionPane.showMessageDialog(null, "Computer won!!!");
            this.tally();
            this.dispose();
        }
        return result;
    }
    
    /*-----------------------------------------------------------------------------------------------------------------
     * determinePlayerHit processes user inputs regarding player hits and calls the corresponding 
     * functions. It also manages the log room
     */
    public void determinePlayerHit(final int hitNum) {
        if (hitNum == 5) {
            final int[] computersShip = this.computersShip;
            final int n = 0;
            --computersShip[n];
            if (this.computersShip[0] == 0) {
            	this.logRoom.validate();
                this.logRoom.add(new JLabel("Player sunk the Carrier!"));
                JOptionPane.showMessageDialog(null, "Player sunk the Carrier!");
            }
            else {
            	this.logRoom.validate();
                this.logRoom.add(new JLabel("Player hit the Carrier!"));
                JOptionPane.showMessageDialog(null, "Player hit the Carrier!");
            }
        }
        else if (hitNum == 4) {
            final int[] computersShip2 = this.computersShip;
            final int n2 = 1;
            --computersShip2[n2];
            if (this.computersShip[1] == 0) {
            	this.logRoom.validate();
                this.logRoom.add(new JLabel("Player sunk the Battleship!"));
                JOptionPane.showMessageDialog(null, "Player sunk the Battleship!");
            }
            else {
            	this.logRoom.validate();
                this.logRoom.add(new JLabel("Player hit the Battleship!"));
                JOptionPane.showMessageDialog(null, "Player hit the Battleship!");
            }
        }
        else if (hitNum == 2) {
            final int[] computersShip3 = this.computersShip;
            final int n3 = 4;
            --computersShip3[n3];
            if (this.computersShip[4] == 0) {
            	this.logRoom.validate();
                this.logRoom.add(new JLabel("Player sunk the Destroyer!"));
                JOptionPane.showMessageDialog(null, "Player sunk the destroyer!");
            }
            else {
            	this.logRoom.validate();
                this.logRoom.add(new JLabel("Player hit the Destroyer!"));
                JOptionPane.showMessageDialog(null, "Player hit the destroyer!");
            }
        }
        else if (hitNum == 3) {
            ++this.hitsOf3;
            if (this.hitsOf3 <= 3) {
                final int[] computersShip4 = this.computersShip;
                final int n4 = 2;
                --computersShip4[n4];
                if (this.computersShip[2] == 0) {
                	this.logRoom.validate();
                    this.logRoom.add(new JLabel("Player sunk the Cruiser!"));
                    JOptionPane.showMessageDialog(null, "Player sunk the Cruiser!");
                }
                else {
                	this.logRoom.validate();
                    this.logRoom.add(new JLabel("Player hit the Cruiser!"));
                    JOptionPane.showMessageDialog(null, "Player hit the Cruiser!");
                }
            }
            else {
                final int[] computersShip5 = this.computersShip;
                final int n5 = 3;
                --computersShip5[n5];
                if (this.computersShip[3] == 0) {
                	this.logRoom.validate();
                    this.logRoom.add(new JLabel("Player sunk the Submarine!"));
                    JOptionPane.showMessageDialog(null, "Player sunk the Submarine!");
                }
                else {
                	this.logRoom.validate();
                    this.logRoom.add(new JLabel("Player hit the Submarine!"));
                    JOptionPane.showMessageDialog(null, "Player hit the Submarine!");
                }
            }
        }
        if (IntStream.of(this.computersShip).sum() == 0) {
            JOptionPane.showMessageDialog(null, "Player won!!!");
            this.tally();
            this.dispose();
        }
    }
    
    /*
     * logship stores the game information in a txt file called PregameLog.txt
     */
    public void logShips() {
        try {
            final File saveFile = new File("PregameLog.txt");
            final PrintWriter write = new PrintWriter(saveFile);
            write.println("BattleShip Pregame: ");
            write.println("Computer ship placements: ");
            for (int i = 0; i < BattleShip.placedShip.length; ++i) {
                for (int j = 0; j < BattleShip.placedShip[i].length; ++j) {
                    write.print(String.valueOf(BattleShip.placedShip[i][j]) + " ");
                }
                write.println("");
            }
            write.println("5s = Carrier");
            write.println("4s = Battleship");
            write.println("3s = Cruiser");
            write.println("3s = Submarine");
            write.println("2s = Destroyer");
            write.close();
            JOptionPane.showMessageDialog(null, "Game saved to " + saveFile);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Save Failed");
        }
    }
    
    /*
     * tally stores the game results in a txt file called Results.txt
     */
    public void tally() {
        try {
            final File saveFile = new File("Results.txt");
            final PrintWriter write = new PrintWriter(saveFile);
            write.println("BattleShip Pregame: ");
            write.println("");
            write.println("Computer ship placements: ");
            for (int i = 0; i < BattleShip.placedShip.length; ++i) {
                for (int j = 0; j < BattleShip.placedShip[i].length; ++j) {
                    write.print(String.valueOf(BattleShip.placedShip[i][j]) + " ");
                }
                write.println("");
            }
            write.println("5s = Carrier");
            write.println("4s = Battleship");
            write.println("3s = Cruiser");
            write.println("3s = Submarine");
            write.println("2s = Destroyer");
            write.println("");
            write.println("Player:");
            write.println("Hits on target: " + (17 - IntStream.of(this.computersShip).sum()));
            write.print("Sunk: ");
            if (this.computersShip[0] == 0) {
                write.print("Carrier ");
            }
            if (this.computersShip[1] == 0) {
                write.print("Battleship ");
            }
            if (this.computersShip[2] == 0) {
                write.print("Cruiser ");
            }
            if (this.computersShip[3] == 0) {
                write.print("Submarine ");
            }
            if (this.computersShip[4] == 0) {
                write.print("Destroyer");
            }
            if (17 - IntStream.of(this.computersShip).sum() < 2) {
                write.print("none");
            }
            write.println("");
            write.println("");
            write.println("Computer:");
            write.println("Hits on taget: " + (17 - IntStream.of(this.playersShip).sum()));
            write.print("Sunk: ");
            if (this.playersShip[0] == 0) {
                write.print("Carrier ");
            }
            if (this.playersShip[1] == 0) {
                write.print("Battleship ");
            }
            if (this.playersShip[2] == 0) {
                write.print("Cruiser ");
            }
            if (this.playersShip[3] == 0) {
                write.print("Submarine ");
            }
            if (this.playersShip[4] == 0) {
                write.print("Destroyer ");
            }
            if (17 - IntStream.of(this.playersShip).sum() < 2) {
                write.print("none");
            }
            write.println("");
            write.println("");
            write.print("Total time: ");
            if (this.timeSec < 10) {
                write.println(String.valueOf(String.valueOf(this.timeMin)) + ":0" + String.valueOf(this.timeSec));
            }
            else {
                write.println(String.valueOf(String.valueOf(this.timeMin)) + ":" + String.valueOf(this.timeSec));
            }
            write.close();
            JOptionPane.showMessageDialog(null, "Game saved to " + saveFile);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Save Failed");
        }
    }
    
    public static int getLength(final int i, final int j, final String direction) {
        int inc = 1;
        if (direction.equals("l")) {
            while (i - inc >= 0 && BattleShip.playerShip[i - inc][j] == 0.0) {
                ++inc;
            }
        }
        if (direction.equals("r")) {
            while (i + inc < 10 && BattleShip.playerShip[i + inc][j] == 0.0) {
                ++inc;
            }
        }
        if (direction.equals("u")) {
            while (j - inc >= 0 && BattleShip.playerShip[i][j - inc] == 0.0) {
                ++inc;
            }
        }
        if (direction.equals("d")) {
            while (j + inc < 10 && BattleShip.playerShip[i][j + inc] == 0.0) {
                ++inc;
            }
        }
        return inc - 1;
    }
    
    public String flip(final String direction) {
        if (direction.equals("l")) {
            return "r";
        }
        if (direction.equals("r")) {
            return "l";
        }
        if (direction.equals("u")) {
            return "d";
        }
        return "u";
    }
    
    public String rotate(final int corX, final int corY, final String direction) {
        String p = "und";
        if (direction.equals("l")) {
            if (corY > 0 && BattleShip.playerShip[corX][corY - 1] == 0.0) {
                    p = "u";
                    return p;
            }
            else {
                if (corX < 9 && BattleShip.playerShip[corX][corY + 1] == 0.0) {
                    p = "d";
                    return p;
                }
                if (corX < 9 && BattleShip.playerShip[corX + 1][corY] == 0.0) {
                    p = "r";
                    return "r";
                }
            }
        }
        if (direction.equals("r")) {
            if (corY < 9 && BattleShip.playerShip[corX][corY + 1] == 0.0) {
                    p = "d";
                    return p;
            }
            else {
                if (corX > 0 && BattleShip.playerShip[corX][corY - 1] == 0.0) {
                    p = "u";
                    return p;
                }
                if (corX > 0 && BattleShip.playerShip[corX - 1][corY] == 0.0) {
                    p = "l";
                    return p;
                }
            }
        }
        else if (direction.equals("u")) {
            if (corX < 9 && BattleShip.playerShip[corX + 1][corY] == 0.0) {
                    p = "r";
                    return p;
            }
            else {
                if (corX > 0 && BattleShip.playerShip[corX - 1][corY] == 0.0) {
                    p = "l";
                    return p;
                }
                if (corY < 9 && BattleShip.playerShip[corX][corY + 1] == 0.0) {
                    p = "d";
                    return p;
                }
            }
        }
        else if (direction.equals("d")) {
        	if (corX > 0 && BattleShip.playerShip[corX - 1][corY] == 0.0) {
        			p = "l";
        			return p;
            }
        	else {
        		if (corX < 9 && BattleShip.playerShip[corX + 1][corY] == 0.0) {
        			p = "r";
        			return p;
        		}
        		if (corX > 0 && BattleShip.playerShip[corX][corY - 1] == 0.0)
        		{
        			p = "u";
        			return p;
        		}
        	}
        }
        return "oops";
    }
}

