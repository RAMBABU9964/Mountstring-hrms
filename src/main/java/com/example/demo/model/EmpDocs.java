package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class EmpDocs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] document10th;

    @Lob
    private byte[] documentInter;

    @Lob
    private byte[] documentGraduation;

    @Lob
    private byte[] resume;

    @Lob
    private byte[] aadhar;
    @Lob
    private byte[] passportSizePhoto;

    private String githubLink;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public EmpDocs(Long id, byte[] document10th, byte[] documentInter, byte[] documentGraduation, byte[] resume, byte[] aadhar, byte[] passportSizePhoto, String githubLink, User user) {
        this.id = id;
        this.document10th = document10th;
        this.documentInter = documentInter;
        this.documentGraduation = documentGraduation;
        this.resume = resume;
        this.aadhar = aadhar;
        this.passportSizePhoto = passportSizePhoto;
        this.githubLink = githubLink;
        this.user = user;
    }

    public EmpDocs() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDocument10th() {
        return document10th;
    }

    public void setDocument10th(byte[] document10th) {
        this.document10th = document10th;
    }

    public byte[] getDocumentInter() {
        return documentInter;
    }

    public void setDocumentInter(byte[] documentInter) {
        this.documentInter = documentInter;
    }

    public byte[] getDocumentGraduation() {
        return documentGraduation;
    }

    public void setDocumentGraduation(byte[] documentGraduation) {
        this.documentGraduation = documentGraduation;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public byte[] getAadhar() {
        return aadhar;
    }

    public void setAadhar(byte[] aadhar) {
        this.aadhar = aadhar;
    }

    public byte[] getPassportSizePhoto() {
        return passportSizePhoto;
    }

    public void setPassportSizePhoto(byte[] passportSizePhoto) {
        this.passportSizePhoto = passportSizePhoto;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
