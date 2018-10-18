package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {

	private Set<Capitalist> hierarchy = new HashSet<>();

	/**
	 * Adds a given element to the hierarchy.
	 * <p>
	 * If the given element is already present in the hierarchy, do not add it and
	 * return false
	 * <p>
	 * If the given element has a parent and the parent is not part of the
	 * hierarchy, add the parent and then add the given element
	 * <p>
	 * If the given element has no parent but is a Parent itself, add it to the
	 * hierarchy
	 * <p>
	 * If the given element has no parent and is not a Parent itself, do not add it
	 * and return false
	 *
	 * @param capitalist the element to add to the hierarchy
	 * @return true if the element was added successfully, false otherwise
	 */
	@Override
	public boolean add(Capitalist capitalist) {
		// I apologize in advance for the way this code looks
		if (capitalist != null && !hierarchy.contains(capitalist)) {
			if (capitalist.hasParent() || capitalist instanceof FatCat) {
				if (!hierarchy.contains(capitalist.getParent())) {
					this.add(capitalist.getParent());
				}
				this.hierarchy.add(capitalist);
				return true;
			}
		}
		return false;
	}

	/**
	 * @param capitalist the element to search for
	 * @return true if the element has been added to the hierarchy, false otherwise
	 */
	@Override
	public boolean has(Capitalist capitalist) {
		return hierarchy.contains(capitalist);
	}

	/**
	 * @return all elements in the hierarchy, or an empty set if no elements have
	 *         been added to the hierarchy
	 */
	@Override
	public Set<Capitalist> getElements() {
		Set<Capitalist> hierarchy = new HashSet<>(this.hierarchy);
		return hierarchy;
	}

	/**
	 * @return all parent elements in the hierarchy, or an empty set if no parents
	 *         have been added to the hierarchy
	 */
	@Override
	public Set<FatCat> getParents() {
		Set<FatCat> parentSet = new HashSet<>();
		for (Capitalist capitalist : hierarchy) {
			for (FatCat fatcat = capitalist instanceof FatCat ? (FatCat) capitalist
					: capitalist.getParent(); fatcat != null; fatcat = fatcat.getParent()) {
				parentSet.add(fatcat);
			}
		}
		return parentSet;
	}

	/**
	 * @param fatCat the parent whose children need to be returned
	 * @return all elements in the hierarchy that have the given parent as a direct
	 *         parent, or an empty set if the parent is not present in the hierarchy
	 *         or if there are no children for the given parent
	 */
	@Override
	public Set<Capitalist> getChildren(FatCat fatCat) {
		Set<Capitalist> childSet = new HashSet<>();
		for (Capitalist capitalist : hierarchy) {
			if (capitalist.getParent() == fatCat) {
				childSet.add(capitalist);
			}
		}
		return childSet;
	}

	/**
	 * @return a map in which the keys represent the parent elements in the
	 *         hierarchy, and the each value is a set of the direct children of the
	 *         associate parent, or an empty map if the hierarchy is empty.
	 */
	@Override
	public Map<FatCat, Set<Capitalist>> getHierarchy() {
		Map<FatCat, Set<Capitalist>> fullHierarchy = new HashMap<>();
		Set<FatCat> fatcats = getParents();
		for (FatCat fatcat : fatcats) {
			fullHierarchy.put(fatcat, getChildren(fatcat));
		}
		return fullHierarchy;
	}

	/**
	 * @param capitalist
	 * @return the parent chain of the given element, starting with its direct
	 *         parent, then its parent's parent, etc, or an empty list if the given
	 *         element has no parent or if its parent is not in the hierarchy
	 */
	@Override
	public List<FatCat> getParentChain(Capitalist capitalist) {
		List<FatCat> parentChain = new ArrayList<>();
		if (capitalist != null) {
			while (capitalist.hasParent() && this.has(capitalist.getParent())) {
				parentChain.add(capitalist.getParent());
				capitalist = capitalist.getParent();
			}
		}
		return parentChain;
	}
}
