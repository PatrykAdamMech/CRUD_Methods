package com.patryk.hollowknight.entity;

import javax.persistence.*;
import javax.swing.text.DateFormatter;
import java.sql.Date;

@Entity
@Table(name = "charms", schema = "public", catalog = "postgres")
public class CharmsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "notches")
    private Integer notches;
    @Basic
    @Column(name = "aquire_date")
    private Date aquireDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNotches() {
        return notches;
    }

    public void setNotches(Integer notches) {
        this.notches = notches;
    }

    public Date getAquireDate() {
        return aquireDate;
    }

    public void setAquireDate(Date aquireDate) {
        this.aquireDate = aquireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharmsEntity that = (CharmsEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (notches != null ? !notches.equals(that.notches) : that.notches != null) return false;
        if (aquireDate != null ? !aquireDate.equals(that.aquireDate) : that.aquireDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (notches != null ? notches.hashCode() : 0);
        result = 31 * result + (aquireDate != null ? aquireDate.hashCode() : 0);
        return result;
    }
}
