
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

public class AStar {

	public boolean Algorithm(Path.startGoalNodes buttons[]) {
		Integer goalIndex=0;
		Integer startIndex=0;
		
		for(int i = 0; i<buttons.length; i++) {
			if(buttons[i].getBackground().equals(Color.MAGENTA)) {
				goalIndex = i;
			}
			if(buttons[i].getBackground().equals(Color.CYAN)){
				startIndex = i;
			}
		}
		
		//check to see if there is a possible path between the starting node to the goal node
		if(blockedLeft(buttons, goalIndex) && blockedRight(buttons, goalIndex) && blockedDown(buttons, goalIndex) && blockedUp(buttons, goalIndex)) {
			return false;
		}
		
		if(blockedLeft(buttons, startIndex) && blockedRight(buttons, startIndex) && blockedDown(buttons, startIndex) && blockedUp(buttons, startIndex)) {
			return false;
		}
		
		ArrayList<Integer> visited = new ArrayList<Integer>();
		ArrayList<Integer> frontier = new ArrayList<Integer>();
		int distance = 1;
		frontier.add(startIndex);
		Integer traverse = 0;
		
		while(!frontier.isEmpty()) {
			traverse = new Integer(frontier.get(0));
			frontier.remove(0);
			
			visited.add(traverse);
	
			if(traverse.equals(goalIndex)) {	
				Integer current = visited.get(visited.size() - 1);
				for(int i = visited.size() - 2; i>= 0; i--) {
					if(current.equals(visited.get(i) + 1) || current.equals(visited.get(i)-1) ||
							current.equals(visited.get(i)+15) || current.equals(visited.get(i)-15)) {
						buttons[visited.get(i)].setBackground(Color.cyan);
						current = visited.get(i);
					}
				}
				
				break;
			}
			visited.add(traverse);
			
			//check to see if the path can move up, down, left, or right
			if(!blockedDown(buttons, traverse) && !visited.contains(traverse + 15) && !frontier.contains(traverse + 15)) {
				frontier.add(traverse + 15);
				buttons[traverse + 15].heuristic(distance, traverse + 15, goalIndex);
			}
			
			if(!blockedLeft(buttons, traverse) && !visited.contains(traverse - 1) && !frontier.contains(traverse -1)) {
				frontier.add(traverse - 1);
				buttons[traverse - 1].heuristic(distance, traverse - 1, goalIndex);
			}
			
			if(!blockedRight(buttons, traverse) && !visited.contains(traverse + 1) && !frontier.contains(traverse + 1)) {
				frontier.add(traverse + 1);
				buttons[traverse + 1].heuristic(distance, traverse + 1, goalIndex);
			}
			
			if(!blockedUp(buttons, traverse) && !visited.contains(traverse - 15) && !frontier.contains(traverse - 15)) {
				frontier.add(traverse - 15);
				buttons[traverse - 15].heuristic(distance, traverse - 15, goalIndex);
			}
			
			distance++;
			
			sortMethod(frontier, buttons);
		}
		
		if(!traverse.equals(goalIndex)) {
			return false;
		}
		
		return true;
	}
	
	private boolean blockedLeft(JButton buttons[], int index){
		if((buttons[index].getX()-36) < 21 || buttons[index-1].getBackground().equals(Color.RED)){
			return true;
		}
		return false;
	}
	
	private boolean blockedRight(JButton buttons[], int index){
		if((buttons[index].getX()+36) > 525 || buttons[index+1].getBackground().equals(Color.RED)){
			return true;
		}
		return false;
	}
	
	private boolean blockedDown(Path.startGoalNodes buttons[], int index){
		if(((index + 15) > 224 || buttons[index+15].getBackground().equals(Color.RED))){
			return true;
		}
		return false;
	}
	
	private boolean blockedUp(Path.startGoalNodes buttons[], int index){
		if(((index - 15) < 0 || buttons[index-15].getBackground().equals(Color.RED))){
			return true;
		}
		return false;
	}
	
	//method to sort the ArrayList from smallest heuristic number to largest
	private void sortMethod(ArrayList<Integer> sort, Path.startGoalNodes buttons[]) {
		for(int i = 0; i<sort.size(); i++) {
			Integer min = buttons[sort.get(i)].getHeurisitcNumber();
			int minIndex = i;
			
			for(int j= i+1; j<sort.size(); j++) {
				if(buttons[sort.get(j)].getHeurisitcNumber() == min) {
					
				}
				else if(min > buttons[sort.get(j)].getHeurisitcNumber()) {
					min = buttons[sort.get(j)].getHeurisitcNumber();
					minIndex = j;
				}
					
			}
			Integer temp = sort.get(i);
			sort.set(i, sort.get(minIndex));
			sort.set(minIndex, temp);
		}
		
	}
}
