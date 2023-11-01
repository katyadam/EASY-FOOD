package cz.muni.fi.pv168.project.service.crud;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message)
    {
        super(message);
    }
}
