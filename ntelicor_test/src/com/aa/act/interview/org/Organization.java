package com.aa.act.interview.org;

import java.util.Optional;

public abstract class Organization {

    private Position root;

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
        Optional<Position> newPosition = positionsExists(title, root);
        if (newPosition.isPresent()) {
            Employee temp = new Employee(0, person);
            newPosition.get().setEmployee(Optional.of(temp));
        }
        return newPosition;
    }

    private Optional<Position> positionsExists(String title, Position current) {

        Optional<Position> returnMe = Optional.empty();

        if (current.getTitle().equalsIgnoreCase(title)) {
            returnMe = Optional.of(current);
        } else {
            for (Position pos : current.getDirectReports()) {
                returnMe = positionsExists(title, pos);
                if (returnMe.isPresent()) {
                    break;
                }
            }
        }

        return returnMe;
    }

    @Override
    public String toString() {
        return printOrganization(root, "");
    }

    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for (Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "\t"));
        }
        return sb.toString();
    }
}
