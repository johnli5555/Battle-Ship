package game2;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
/*
 * To run the battleship program, run this class
 * 
 * BattleShipMainMenu runs the main menu of the game. It implements the graphical interface of the menu
 * and determines the mode in which the player wants to play(simple and advanced AI).
 */
public class BattleShipMainMenu extends JFrame implements ActionListener
{
	// Initializing the main menu buttons and panels
    public JLabel battleShip;
    public JButton startGame;
    public JButton about;
    public JButton quit;
    public JButton simple;
    public JButton advanced;
    public JButton cancel;
    String aboutString;
    public JPanel mainPan;
    
    // Initializing the properties of the main menu buttons and panels
    public BattleShipMainMenu() {
        this.battleShip = new JLabel("BattleShip");
        this.startGame = new JButton("Start");
        this.about = new JButton("About");
        this.quit = new JButton("Quit");
        this.simple = new JButton("Simple Ai");
        this.advanced = new JButton("Advanced Ai");
        this.cancel = new JButton("Cancel");
        this.aboutString = "BattleShip, by John\n\nThe rules of game are as follows:\n\n1. You must prepare a 10 by 10 grid(you will see the format as you start the game)\n2. You must place ships on the Grid(Horizontal and/or Vertical only)\n3. The Ships are: Carrier 5 slots, Battleship 4 slots, Submarine 3 slots, Cruiser 3 slots, Destroyer s slots\n4. Player and Ai takes turn choosing coordinates to hit\n5. You must be honest regarding hitting/sinking of ships\n6. The first to sink all of opponent's ship wins\n7. Have Fun!";
        this.mainPan = new JPanel();
        this.setUndecorated(true);
        this.setSize(480, 360);
        this.setTitle("BATTLESHIP");
        this.setBackground(Color.WHITE);
        this.getRootPane().setWindowDecorationStyle(0);
        this.setResizable(false);
        final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        final int dimX = (int)((dimension.getWidth() - this.getWidth()) / 2.0);
        final int dimY = (int)((dimension.getHeight() - this.getHeight()) / 2.0);
        this.setLocation(dimX, dimY);
        final Font title = new Font("Serif", 1, 46);
        final Font buttons = new Font("Serif", 2, 30);
        this.battleShip.setFont(title);
        this.startGame.setFont(buttons);
        this.about.setFont(buttons);
        this.quit.setFont(buttons);
        this.simple.setFont(buttons);
        this.advanced.setFont(buttons);
        this.cancel.setFont(buttons);
        this.startGame.setBackground(Color.white);
        this.about.setBackground(Color.white);
        this.quit.setBackground(Color.white);
        this.simple.setBackground(Color.white);
        this.advanced.setBackground(Color.white);
        this.cancel.setBackground(Color.white);
        this.startGame.addActionListener(this);
        this.about.addActionListener(this);
        this.quit.addActionListener(this);
        this.simple.addActionListener(this);
        this.advanced.addActionListener(this);
        this.cancel.addActionListener(this);
        this.battleShip.setHorizontalAlignment(0);
        this.setLayout(new GridBagLayout());
        final BoxLayout general = new BoxLayout(this.mainPan, 1);
        this.mainPan.setLayout(general);
        this.mainPan.add(this.battleShip, new GridBagConstraints());
        this.mainPan.add(this.startGame, new GridBagConstraints());
        this.mainPan.add(this.about, new GridBagConstraints());
        this.mainPan.add(this.quit, new GridBagConstraints());
        this.mainPan.add(this.simple, new GridBagConstraints());
        this.mainPan.add(this.advanced, new GridBagConstraints());
        this.mainPan.add(this.cancel, new GridBagConstraints());
        this.add(this.mainPan, new GridBagConstraints());
        this.battleShip.setAlignmentX(0.5f);
        this.startGame.setAlignmentX(0.5f);
        this.about.setAlignmentX(0.5f);
        this.quit.setAlignmentX(0.5f);
        this.simple.setAlignmentX(0.5f);
        this.advanced.setAlignmentX(0.5f);
        this.cancel.setAlignmentX(0.5f);
        this.battleShip.setVisible(true);
        this.startGame.setVisible(true);
        this.about.setVisible(true);
        this.quit.setVisible(true);
        this.simple.setVisible(false);
        this.advanced.setVisible(false);
        this.cancel.setVisible(false);
        this.mainPan.setVisible(true);
        this.setVisible(true);
    }
    
    // If the"start game" button os pressed, this code is executed and the available modes are made visible
    public void actionPerformed(final ActionEvent event) {
        final String command = event.getActionCommand();
        if (command.equals("Start")) {
            this.startGame.setVisible(false);
            this.about.setVisible(false);
            this.quit.setVisible(false);
            this.simple.setVisible(true);
            this.advanced.setVisible(true);
            this.cancel.setVisible(true);
        }
        else if (command.equals("Simple Ai")) {
            final BattleShip game = new BattleShip(0);
            this.dispose();
        }
        else if (command.equals("Advanced Ai")) {
            final BattleShip game = new BattleShip(1);
            this.dispose();
        }
        else if (command.equals("Cancel")) {
            this.battleShip.setVisible(true);
            this.startGame.setVisible(true);
            this.about.setVisible(true);
            this.quit.setVisible(true);
            this.simple.setVisible(false);
            this.advanced.setVisible(false);
            this.cancel.setVisible(false);
        }
        else if (command.equals("About")) {
            JOptionPane.showMessageDialog(null, this.aboutString);
        }
        else if (command.equals("Quit")) {
            this.dispose();
        }
    }
    
    // Creating a menu and running the game
    public static void main(final String[] args) {
        final BattleShipMainMenu game = new BattleShipMainMenu();
    }
}
