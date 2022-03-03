package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.TargetClassify;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

/**
 * 考核指标
 */
@Entity
@Table(name="ylrc_assess_target")
@EntityListeners(AuditingEntityListener.class)
public class AssessTarget extends BaseEntity{

    @ValidateEntity(required=true,requiredLeng=true,minLength=2,maxLength=18,errorRequiredMsg="指标名称不能为空!",errorMinLengthMsg="指标名称长度不能小于2!",errorMaxLengthMsg="指标名称长度不能大于18!")
    @Column(name="name",length=18)
    private String name;//名称

    @ValidateEntity(required = true,requiredLeng = true,minLength = 1,maxLength = 500,errorRequiredMsg = "A标准不能为空!",
            errorMinLengthMsg = "A标准不能为空!",errorMaxLengthMsg = "A标准长度不能超过500!")
    @Column(name = "option_a",length = 500)
    private String optionA;//标准A

    @ValidateEntity(required = true,requiredLeng = true,minLength = 1,maxLength = 500,errorRequiredMsg = "B标准不能为空!",
            errorMinLengthMsg = "B标准不能为空!",errorMaxLengthMsg = "B标准长度不能超过500!")
    @Column(name = "option_b",length = 500)
    private String optionB;//标准B

    @ValidateEntity(required = true,requiredLeng = true,minLength = 1,maxLength = 500,errorRequiredMsg = "C标准不能为空!",
            errorMinLengthMsg = "C标准不能为空!",errorMaxLengthMsg = "C标准长度不能超过500!")
    @Column(name = "option_c",length = 500)
    private String optionC;//标准C

    @ValidateEntity(required = true,requiredLeng = true,minLength = 1,maxLength = 500,errorRequiredMsg = "D标准不能为空!",
            errorMinLengthMsg = "D标准不能为空!",errorMaxLengthMsg = "D标准长度不能超过500!")
    @Column(name = "option_d",length = 500)
    private String optionD;//标准D

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100,
            errorMinValueMsg = "分数最小为0",errorMaxValueMsg = "分数最大为100",errorRequiredMsg = "请填写分数")
    @Column(name = "score")
    private Integer score;//分数


    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "ylrc_target_department",joinColumns ={@JoinColumn(name = "assess_target_id")},inverseJoinColumns = @JoinColumn(name = "department_id"))
    private List<Department> departments;//部门

    @Enumerated
    @Column(name = "target_classify")
    private TargetClassify targetClassify;//指标分类

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public TargetClassify getTargetClassify() {
        return targetClassify;
    }

    public void setTargetClassify(TargetClassify targetClassify) {
        this.targetClassify = targetClassify;
    }

    @Override
    public String toString() {
        return "AssessTarget{" +
                "name='" + name + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", score=" + score +
                ", targetClassify=" + targetClassify +
                '}';
    }
}
