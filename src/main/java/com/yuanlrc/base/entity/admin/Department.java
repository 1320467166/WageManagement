package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

/**
 * 部门实体类
 */
@Entity
@Table(name = "ylrc_department")
@EntityListeners(AuditingEntityListener.class)
public class Department extends BaseEntity{

    @ValidateEntity(required=true,requiredLeng=true,minLength=2,maxLength=18,errorRequiredMsg="部门名称不能为空!",errorMinLengthMsg="部门名称长度不能小于2!",errorMaxLengthMsg="部门名称长度不能大于18!")
    @Column(name="name",nullable=false,length=18,unique=true)
    private String name;//部门名称

    @Column(name="note",length=100)
    private String note;//备注

    @ManyToMany(cascade = CascadeType.REFRESH,mappedBy = "departments",fetch = FetchType.LAZY)
    private List<AssessTarget> assessTargets;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<AssessTarget> getAssessTargets() {
        return assessTargets;
    }

    public void setAssessTargets(List<AssessTarget> assessTargets) {
        this.assessTargets = assessTargets;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
