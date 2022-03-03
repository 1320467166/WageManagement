package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.EducationEnum;
import com.yuanlrc.base.bean.SexEnum;
import com.yuanlrc.base.bean.StaffStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工
 */
@Entity
@Table(name = "ylrc_staff")
@EntityListeners(AuditingEntityListener.class)
public class Staff extends BaseEntity{


    @Column(name = "job_number")
    private String jobNumber;//工号

    @ValidateEntity(required = true, requiredLeng = true, minLength = 2, maxLength = 18,
            errorRequiredMsg = "请输入姓名", errorMinLengthMsg = "姓名必须在2~18个字符之间", errorMaxLengthMsg = "姓名必须在2~18个字符之间")
    @Column(name = "name")
    private String name;//姓名

    @Column(name = "sex", nullable = false, length = 10)
    private Integer sex = SexEnum.MALE.getCode();//性别

    @Column(name = "card_no", length = 10)
    private Integer cardNo;//卡号

    @Enumerated
    @Column(name = "education")
    private EducationEnum educationEnum;//学历

    @Column(name = "mobile")
    @ValidateEntity(required=true,errorRequiredMsg="电话不能为空!")
    private String mobile;//电话

    @ManyToOne
    @JoinColumn(name = "job_title_id")
    private JobTitle jobTitle;//职称

    @ValidateEntity(required = true, requiredLeng = true, minLength = 2, maxLength = 18,
            errorRequiredMsg = "请输入紧急联系人", errorMinLengthMsg = "紧急联系人姓名必须在2~18个字符之间", errorMaxLengthMsg = "紧急联系人姓名必须在2~18个字符之间")
    @Column(name = "emergency_contact")
    private String emergencyContact;//紧急联系人

    @Column(name = "emergency_mobile")
    @ValidateEntity(required=true,errorRequiredMsg="紧急联系人手机号不能为空!")
    private String emergencyMobile;//紧急联系人手机号

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;//岗位

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;//部门

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 1000000
            ,errorRequiredMsg="基本工资不能为空!",errorMinValueMsg="基本工资最小为0!",errorMaxValueMsg="基本工资最大为1000000!")
    @Column(name = "salary")
    private BigDecimal salary;//基本工资

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;//角色

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 150,
            errorMinValueMsg = "年龄最小为0",errorMaxValueMsg = "年龄最大为150",errorRequiredMsg = "请填写年龄")
    @Column(name = "age")
    private Integer age;//年龄

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "entry_time")
    private Date entryTime;//入职时间

    @ValidateEntity(required=true,requiredLeng=true,minLength=4,maxLength=32,errorRequiredMsg="密码不能为空！",errorMinLengthMsg="密码长度最少为4!",errorMaxLengthMsg="密码长度最多为32!")
    @Column(name="password",nullable=false,length=32)
    private String password;//密码

    @ValidateEntity(required=false)
    @Column(name="head_pic",length=128)
    private String headPic;//头像

    @Column(name = "is_status")
    private Integer isStatus = StaffStatus.ON_THE_JOB.getCode();//是否在职  默认在职

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public EducationEnum getEducationEnum() {
        return educationEnum;
    }

    public void setEducationEnum(EducationEnum educationEnum) {
        this.educationEnum = educationEnum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyMobile() {
        return emergencyMobile;
    }

    public void setEmergencyMobile(String emergencyMobile) {
        this.emergencyMobile = emergencyMobile;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Integer getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(Integer isStatus) {
        this.isStatus = isStatus;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "jobNumber='" + jobNumber + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", educationEnum=" + educationEnum +
                ", mobile='" + mobile + '\'' +
                ", jobTitle=" + jobTitle +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", emergencyMobile='" + emergencyMobile + '\'' +
                ", position=" + position +
                ", department=" + department +
                ", salary=" + salary +
                ", role=" + role +
                ", age=" + age +
                ", entryTime=" + entryTime +
                ", password='" + password + '\'' +
                ", headPic='" + headPic + '\'' +
                ", isStatus=" + isStatus +
                '}';
    }
}
