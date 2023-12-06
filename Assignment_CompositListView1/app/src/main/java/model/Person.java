package model;

public class Person {

    private String personId;
    private String name;

    private String photo;


    public Person( String personId, String name, String photo) {

        this.personId = personId;
        this.name = name;
        this.photo = photo;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +

                '}';
    }


}
