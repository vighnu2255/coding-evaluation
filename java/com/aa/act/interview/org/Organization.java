package com.aa.act.interview.org;

import java.util.Optional;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public abstract class Organization {

	private Position root;
	private static int empIdentifier;
	
	
	public Organization() {
		root = createOrganization();
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {
		Position position = getPosition(title);
		if(position != null){
			position.setEmployee(Optional.of(new Employee(++empIdentifier, person)));
		}
		return Optional.ofNullable(position);
	}
	
	private Position getPosition(String title){
		Set<Position> set = new HashSet<>();
		Queue<Position> queue = new LinkedList<>();
		queue.add(root);
		while(!queue.isEmpty()){
			Position pos = queue.poll();
			if(pos.getTitle().equals(title)){
				return pos;
			}
			for(Position position: pos.getDirectReports()){
				if(!set.contains(position)){
					set.add(position);
				}
			}
			set.add(pos);
		}
		return null;
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
