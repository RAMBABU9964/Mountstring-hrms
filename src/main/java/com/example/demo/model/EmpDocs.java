package com.example.demo.model;

import java.util.Arrays;

import jakarta.persistence.*;

@Entity
public class EmpDocs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(length = 100000)
    private byte[] document10th;

    @Lob
    @Column(length = 100000)
    private byte[] documentInter;

    @Lob
    @Column(length = 100000)
    private byte[] documentGraduation;

    @Lob
    @Column(length = 100000)
    private byte[] resume;

    @Lob
    @Column(length = 100000)
    private byte[] aadhar;
    
    @Lob
    @Column(length = 100000)
    private byte[] passportSizePhoto;

    private String githubLink;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
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

	@Override
	public String toString() {
		final int maxLen = 10;
		return "EmpDocs [id=" + id + ", document10th="
				+ (document10th != null ? Arrays
						.toString(Arrays.copyOf(document10th, Math.min(document10th.length, maxLen))) : null)
				+ ", documentInter="
				+ (documentInter != null ? Arrays
						.toString(Arrays.copyOf(documentInter, Math.min(documentInter.length, maxLen))) : null)
				+ ", documentGraduation="
				+ (documentGraduation != null ? Arrays.toString(
						Arrays.copyOf(documentGraduation, Math.min(documentGraduation.length, maxLen))) : null)
				+ ", resume="
				+ (resume != null ? Arrays.toString(Arrays.copyOf(resume, Math.min(resume.length, maxLen))) : null)
				+ ", aadhar="
				+ (aadhar != null ? Arrays.toString(Arrays.copyOf(aadhar, Math.min(aadhar.length, maxLen))) : null)
				+ ", passportSizePhoto="
				+ (passportSizePhoto != null
						? Arrays.toString(Arrays.copyOf(passportSizePhoto, Math.min(passportSizePhoto.length, maxLen)))
						: null)
				+ ", githubLink=" + githubLink + ", user=" + user + "]";
	}
    
}
