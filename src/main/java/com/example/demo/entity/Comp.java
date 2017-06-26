package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/6/22]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
@Table(name = "TEST_COMP")
public class Comp
{
    @Id
    @Column(name = "COMPID")
    private Long compId;

    @Column(name = "COMPNAME")
    private String compName;

    @Transient
    private List<Person> people;

    public Comp(Long compId,String compName)
    {
        this.compId = compId;
        this.compName = compName;
    }

    public Comp()
    {

    }

    public Long getCompId()
    {
        return compId;
    }

    public void setCompId(Long compId)
    {
        this.compId = compId;
    }

    public String getCompName()
    {
        return compName;
    }

    public void setCompName(String compName)
    {
        this.compName = compName;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
}
