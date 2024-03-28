package com.example.demo.controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.EmpDocs;
import com.example.demo.model.User;
import com.example.demo.repository.EmpDocsRepository;
import com.example.demo.service.UserService;

@Controller
public class EmpDocsController {
	
	 @Autowired
	    private EmpDocsService empDocsService;
	 
	 @Autowired
	 UserService userService;
	 
	 @Autowired
	 EmpDocsRepository empDocsRepository;

	    // Method to show the form for uploading documents
	    @GetMapping("/addEmpDoc/{id}")
	    public String showUploadForm(@PathVariable("id") Long id,Model model) {
	    	User user = userService.findById(id);
	    	
	    	EmpDocs docs = empDocsRepository.findByUserId(id);
			if (docs == null) {
				docs=new EmpDocs();
				docs.setUser(user);
				model.addAttribute("empDoc", docs);
			}else {
				if (docs.getDocument10th() != null) {
		            String pdfBase64 = Base64.getEncoder().encodeToString(docs.getDocument10th());
		            model.addAttribute("pdfBase64", pdfBase64);
		        }
				if (docs.getDocumentGraduation() != null) {
		            String pdfBase641 = Base64.getEncoder().encodeToString(docs.getDocumentGraduation());
		            model.addAttribute("pdfBase641", pdfBase641);
		        }
				if (docs.getDocumentInter() != null) {
		            String pdfBase642 = Base64.getEncoder().encodeToString(docs.getDocument10th());
		            model.addAttribute("pdfBase642", pdfBase642);
		        }
				if (docs.getAadhar() != null) {
		            String pdfBase643 = Base64.getEncoder().encodeToString(docs.getAadhar());
		            model.addAttribute("pdfBase643", pdfBase643);
		        }
		        model.addAttribute("empDoc1", docs);
		        return "Documents";
			}
	        return "Uplode-Doce"; // Assuming you have an upload form template
	    }

	    // Method to handle document upload
	    @PostMapping("/uploadDoc/{id}")
	    public String handleFileUpload(@PathVariable("id") Long id,
	                                   @RequestParam("document10th") MultipartFile document10th,
	                                   @RequestParam("documentInter") MultipartFile documentInter,
	                                   @RequestParam("documentGraduation") MultipartFile documentGraduation,
	                                   @RequestParam("resume") MultipartFile resume,
	                                   @RequestParam("aadhar") MultipartFile aadhar,
	                                   @RequestParam("passportSizePhoto") MultipartFile passportSizePhoto,
	                                   @RequestParam("githubLink") String githubLink,
	                                   RedirectAttributes redirectAttributes) {
	        
	        // Retrieve the user based on the given ID
	        User user = userService.findById(id);
	        if (user == null) {
	            // Handle the case when user is not found
	            redirectAttributes.addFlashAttribute("error", "User not found!");
	            return "redirect:/"; // Redirect to the home page or appropriate error page
	        }

	        // Create a new EmpDocs object
	        EmpDocs empDocs = new EmpDocs();
	        empDocs.setUser(user);
	        empDocs.setGithubLink(githubLink);

	        // Set the document bytes
	        try {
	            empDocs.setDocument10th(document10th.getBytes());
	            empDocs.setDocumentInter(documentInter.getBytes());
	            empDocs.setDocumentGraduation(documentGraduation.getBytes());
	            empDocs.setResume(resume.getBytes());
	            empDocs.setAadhar(aadhar.getBytes());
	            empDocs.setPassportSizePhoto(passportSizePhoto.getBytes());
	        } catch (IOException e) {
	            // Handle IOException
	            redirectAttributes.addFlashAttribute("error", "Failed to read file: " + e.getMessage());
	            return "error-page"; // Redirect to the home page or appropriate error page
	        }

	        // Save EmpDocs
	        try {
	            empDocsService.save(empDocs);
	            redirectAttributes.addFlashAttribute("message", "Documents uploaded successfully!");
	            return "redirect:/employee-page"; // Redirect to a success page
	        } catch (Exception e) {
	            // Handle the case when saving EmpDocs fails
	            redirectAttributes.addFlashAttribute("error", "Failed to upload documents: " + e.getMessage());
	            return "error-page"; // Redirect to the home page or appropriate error page
	        }
	    }
}
