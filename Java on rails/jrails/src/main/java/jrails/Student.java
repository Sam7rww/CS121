package jrails;

public class Student extends Model{
    @Column
    public String name;

    @Column
    public String address;

    @Column
    public int age;
}
