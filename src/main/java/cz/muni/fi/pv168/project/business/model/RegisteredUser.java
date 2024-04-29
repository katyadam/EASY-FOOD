package cz.muni.fi.pv168.project.business.model;

public class RegisteredUser extends Entity{
    private String password;
    private String name;
    private final Long ID;

    public RegisteredUser( String guid, String name,  String password, Long ID ) {
        super(guid);
        this.name = name;
        this.password = password;
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getID() {
        return ID;
    }

}
