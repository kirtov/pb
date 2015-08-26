package main.pb;

import java.io.Serializable;

public class Person implements Serializable {
    String name = "", sname = "", pname = "";
    String group = "";
    String phoneNumber= "";
    String email = "";
    String comment = "";
    String date = "";
    int pid;

    public Person(int pid, String name, String sname, String pname, String group, String phoneNumber, String email, String comment, String date) {
        setName(name);
        setSName(sname);
        setPName(pname);
        setGroup(group);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setComment(comment);
        setDate(date);
        setPid(pid);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSName(String sname) {
        this.sname = sname;
    }

    public void setPName(String pname) {
        this.pname = pname;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setComment(String comment) {this.comment = comment;}

    public String getName() {
        return name;
    }

    public int getPid() {
        return pid;
    }

    public String getSName() {
        return sname;
    }

    public String getDate() {
        return date;
    }

    public String getPName() {
        return pname;
    }

    public String getGroup() {
        return group;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getComment() {return comment;}


    public String getFullName() {
        return sname + " " + name + " " + pname;
    }

}
