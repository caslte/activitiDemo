package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/6/22]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
@Table(name = "TEST_PERSON")
public class Person
{
    @Id
    @Column(name = "PERSONID")
    private Long personId;

    @Column(name = "PERSONNAME")
    private String personName;

    @Column(name = "COMPID")
    private Long compId;

    public Person()
    {

    }

    public Person(Long personId,String personName) {
        this.personId = personId;
        this.personName = personName;
    }
    @Transient
    private Comp comp;

    public Long getPersonId()
    {
        return personId;
    }

    public void setPersonId(Long personId)
    {
        this.personId = personId;
    }

    public String getPersonName()
    {
        return personName;
    }

    public void setPersonName(String personName)
    {
        this.personName = personName;
    }

    public Comp getComp() {
        return comp;
    }

    public void setComp(Comp comp) {
        this.comp = comp;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }
}
