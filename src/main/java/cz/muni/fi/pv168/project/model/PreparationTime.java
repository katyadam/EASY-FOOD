package cz.muni.fi.pv168.project.model;

public record PreparationTime(
        Integer hours,
        Integer minutes
) {
    @Override
    public String toString() {
        return hours + " hours" + " " + minutes + " minutes";
    }
}
