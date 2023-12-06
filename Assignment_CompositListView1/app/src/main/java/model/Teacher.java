package model;

public class Teacher extends Person{
    private String salary;
    private String commission;
    private String company;

    public Teacher(String personId, String name, String photo,String salary,String commission, String company) {
        super(personId, name, photo);
        this.salary= salary;
        this.commission= commission;
        this.company=company;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return company;
    }
}
