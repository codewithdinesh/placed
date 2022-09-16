package com.tplacement.placement;

public class placementData {

    String name;
    String desc;
    String date;
    String roles;
    String hr;
    String org;
    String org_location;
    String org_desc;
    String skills;
    String id, education, experience;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public placementData() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public void setOrg_location(String org_location) {
        this.org_location = org_location;
    }

    public void setOrg_desc(String org_desc) {
        this.org_desc = org_desc;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }

    public String getRoles() {
        return roles;
    }

    public String getHr() {
        return hr;
    }

    public String getOrg() {
        return org;
    }

    public String getOrg_location() {
        return org_location;
    }

    public String getOrg_desc() {
        return org_desc;
    }

    public String getSkills() {
        return skills;
    }


    public placementData(String name, String desc, String date, String roles, String hr, String org, String org_location, String org_desc, String skills, String id, String education, String experience) {
        this.name = name;
        this.desc = desc;
        this.date = date;
        this.roles = roles;
        this.hr = hr;
        this.org = org;
        this.org_location = org_location;
        this.org_desc = org_desc;
        this.skills = skills;
        this.education = education;
        this.id = id;
        this.experience = experience;
    }
}
