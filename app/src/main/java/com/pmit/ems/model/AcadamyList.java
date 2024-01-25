package com.pmit.ems.model;

public class AcadamyList {
    private String id;
    private String imageLink;
    private String educationStander;
    private String examBoard;
    private String examYears;
    private String examFullMarks;
    private String examMarksObtained;
    private String examPersentage;
    public AcadamyList(String id,String imageLink,String educationStander,String examBoard,String examYears,
                       String examFullMarks,String examMarksObtained,String examPersentage){
        this.id=id;
        this.imageLink=imageLink;
        this.educationStander=educationStander;
        this.examBoard=examBoard;
        this.examYears=examYears;
        this.examFullMarks=examFullMarks;
        this.examMarksObtained=examMarksObtained;
        this.examPersentage=examPersentage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getEducationStander() {
        return educationStander;
    }

    public void setEducationStander(String educationStander) {
        this.educationStander = educationStander;
    }

    public String getExamBoard() {
        return examBoard;
    }

    public void setExamBoard(String examBoard) {
        this.examBoard = examBoard;
    }

    public String getExamYears() {
        return examYears;
    }

    public void setExamYears(String examYears) {
        this.examYears = examYears;
    }

    public String getExamFullMarks() {
        return examFullMarks;
    }

    public void setExamFullMarks(String examFullMarks) {
        this.examFullMarks = examFullMarks;
    }

    public String getExamMarksObtained() {
        return examMarksObtained;
    }

    public void setExamMarksObtained(String examMarksObtained) {
        this.examMarksObtained = examMarksObtained;
    }

    public String getExamPersentage() {
        return examPersentage;
    }

    public void setExamPersentage(String examPersentage) {
        this.examPersentage = examPersentage;
    }
}
