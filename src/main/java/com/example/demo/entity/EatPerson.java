package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <吃饭>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/6/22]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
@Table(name = "TEST_PERSON")
public class EatPerson
{
    @Id
    @Column(name = "PERSONID")
    private Long personId;

    @Column(name = "PERSONNAME")
    private String personName;

    @Column(name = "ISWASHHANDS")
    private Long isWashHands;

    @Column(name = "EATSTATE")
    private Long eatState;

    @Column(name = "ISADMIN")
    private Long isAdmin;

    public Long getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Long isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Long getIsWashHands() {
        return isWashHands;
    }

    public void setIsWashHands(Long isWashHands) {
        this.isWashHands = isWashHands;
    }

    public Long getEatState() {
        return eatState;
    }

    public void setEatState(Long eatState) {
        this.eatState = eatState;
    }
}
