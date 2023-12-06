package model;

public class Student extends Person{
    private String age;
    private String college;

    public Student(String personId, String name, String photo,String age, String college) {
        super(personId, name, photo);
        this.age= age;
        this.college= college;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Override
    public String toString() {
        return college;
    }
}
