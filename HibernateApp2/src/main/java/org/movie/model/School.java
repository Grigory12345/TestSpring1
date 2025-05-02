package org.movie.model;

import javax.persistence.*;

@Entity
@Table(name = "School")
public class School {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "school_number")
    private int school_number;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "principal_id", referencedColumnName = "id")
    private Principal principal;

    public School()
    {

    }

    public School(int school_number)
    {
        this.school_number = school_number;
    }

    public int getSchool_number() {
        return school_number;
    }

    public void setSchool_number(int school_number) {
        this.school_number = school_number;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
}
