
package com.flamingo.wuzzufjobs;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author noham
 */
@XmlRootElement(name = "JOB")
public class Job implements Serializable{
    private String title ;
    private String company ;
    private String location ;
    private String type ;
    private String level ;
    private String yearsExp ;
    private String country ;
    private String []skills ;

    public Job(String title, String company, String location, String type, String level, String yearsExp, String country, String[] skills) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.type = type;
        this.level = level;
        this.yearsExp = yearsExp;
        this.country = country;
        this.skills = skills;
    }

    public Job() {
    }

    public String getTitle() {
        return title;
    }
    
    @XmlElement(name="Title")
    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }
    @XmlElement(name="Company")
    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }
    @XmlElement(name="Location")
    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }
    
    @XmlElement(name="Type")
    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }
    @XmlElement(name="Level")
    public void setLevel(String level) {
        this.level = level;
    }

    public String getYearsExp() {
        return yearsExp;
    }
    @XmlElement(name="YearsExp")
    public void setYearsExp(String yearsExp) {
        this.yearsExp = yearsExp;
    }

    public String getCountry() {
        return country;
    }
    @XmlElement(name="Country")
    public void setCountry(String country) {
        this.country = country;
    }

    public String[] getSkills() {
        return skills;
    }
    @XmlElement(name="Skills")
    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        String sk= "{ ";
        for (int i = 0; i < skills.length; i++) {
                sk = sk.concat(skills[i]);
                if (i!= (skills.length-1))
                {
                 sk = sk.concat(" , ");
                }
                    
        }
        sk = sk.concat(" }");
        
        return "Job{" + "title=" + title + ", company=" + company + ", location=" + location 
                + ", type=" + type + ", level=" + level + ", yearsExp=" + yearsExp + ", country=" 
                + country + ", skills=" + sk + '}';
    }
    
    
    
}
