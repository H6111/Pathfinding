
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Path {
	
	private static startGoalNodes buttons[] = new startGoalNodes[225];
	private static AStar a = new AStar(); 
	
	public static void main(String[] args) {
		System.out.println("Click on any of the white spaces to select your start and goal.");
		System.out.println("Red = Blocked squares, Cyan = start, Magenta = Goal");
		createWorld();
	}
	
	//code similar to the one for the GUI in the Tic-Tac-Toe assignment was written to make the graphic
	private static void createWorld() {
		
		JFrame frame = new JFrame(" 15x15 tile-based world");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(); 
		panel.setLayout(new GridLayout(15, 15));
		panel.setBorder(BorderFactory.createLineBorder(Color.gray, 15));
		panel.setBackground(Color.white);
		
		//x and y used to keep track of individual node locations in the tile-based world
		int x = 0;
		int y= 0;
		
		//create all the nodes that are represented as buttons
		for (int i = 0; i <= 224; i++)
		{
			buttons[i] = new startGoalNodes(x,y);
			panel.add(buttons[i]);
			++x;
			if(x == 15) {
				x = 0;
				++y;
			}
		}
		
		//creates the randomly generates nodes that are blocked
		for (int i = 0; i< 23; i++) {
			int nonNavigable = (int)(Math.random()*224);	
			
			while(buttons[nonNavigable].getBackground().equals(Color.red)) {
				nonNavigable = (int)(Math.random()*224);
			}
			
			buttons[nonNavigable].setBackground(Color.red);
		}
		
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setSize(600, 600);	
	}
	
	public static class startGoalNodes extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;
		private int playAgain = JOptionPane.DEFAULT_OPTION;
		private int heuristicNumber = 0;
		private static boolean start = true;
		private static boolean goal = true;
		private int x = 0;
		private int y = 0;
		
		public startGoalNodes(int x, int y){ 
			super();
			setBackground(Color.white);
			addActionListener(this);
			this.x = x;
			this.y = y;
		}
		
		public void actionPerformed(ActionEvent e) {
			//creates start node
			if(start && getBackground().equals(Color.white)){
				setBackground(Color.CYAN);
				start = false;
			}
			
			//create goal node
			if(!start && goal && getBackground().equals(Color.white)) {
				setBackground(Color.MAGENTA);
				goal = false;
				boolean pathExists = a.Algorithm(buttons);
				
				//allow user to continue specifying start and goal nodes after paths have been found without shutting down the program
				if(pathExists) {
					playAgain = JOptionPane.showConfirmDialog(null, "Specify Goal and Start again?",
							"Restart?", JOptionPane.YES_NO_OPTION);
				}
				else {
					playAgain = JOptionPane.showConfirmDialog(null, "No path could be found. Specify Goal and Start again?",
							"Restart?", JOptionPane.YES_NO_OPTION);
				}
				
				//restart the tile-based world and randomly generate the blocked nodes again
				if (playAgain == JOptionPane.YES_OPTION)
				{
					start = true;
					goal = true;
					
					for (int i = 0; i <= 224; i++)
					{
						buttons[i].setBackground(Color.WHITE);
					}
					
					for (int i = 0; i< 23; i++) {
						int nonNavigable = (int)(Math.random()*224);	
						
						while(buttons[nonNavigable].getBackground().equals(Color.red)) {
							nonNavigable = (int)(Math.random()*224);
						}
						
						buttons[nonNavigable].setBackground(Color.red);
					}
				} 
				else if (playAgain == JOptionPane.NO_OPTION)
				{
					System.exit(0);
				}
			}
		}
		
		public void heuristic(int distance, int specificIndex, int goalIndex){
			//Manhattan distance method used for calculating the heuristic
			heuristicNumber = Math.abs(buttons[specificIndex].x - buttons[goalIndex].x) +
					Math.abs(buttons[specificIndex].y - buttons[goalIndex].y);
			
			//the result of the Manhattan distance method is added to the distance the specific node is from the starting node
			heuristicNumber = heuristicNumber + distance;
		}
		
		public int getHeurisitcNumber() {
			return heuristicNumber;
		}
		
	}
	
}