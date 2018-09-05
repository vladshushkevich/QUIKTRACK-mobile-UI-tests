package org.stormnetdev.utils;

import org.stormnetdev.reporter.Reporter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class GmailApi {
	
	private static int current = 1; 

	protected static Properties getServerProperty() {
        String host = "imap.gmail.com";
        String port = "993";
        Properties properties = new Properties();
 
        // server setting
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);
 
        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port",
                String.valueOf(port));
        return properties;
	}
	
    /**
     * Sends message with parameters:
     * @param username
     * @param password
     * @param from
     * @param cc
     * @param bcc
     * @param subject
     * @param msg
     */
	
	public static Boolean sendEmail(final String username, final String password, final String from, final String to, final String cc, final String bcc, final String subject, final String msg, int numberOfRetries) {
  
		Reporter.logStep("Sending message with subjest: " + subject);

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
                	protected PasswordAuthentication getPasswordAuthentication() {
                		return new PasswordAuthentication(username, password);
                	}
            	});
 
		try { 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			//below code only requires if your want cc email address
			message.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(cc));
			//below code only requires if your want bcc email address
			message.setRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(bcc));
			message.setSubject(subject);
			message.setText(msg);
    		Reporter.logSubOperation("Sending" + "\n");
			Transport.send(message);
			Reporter.logPassedStep();
			current = 1;
			return true;
		} catch (MessagingException e) {
        	Reporter.logFailed("Could not connect to the message store.");
            if (current < numberOfRetries){
            	Reporter.logInfo("Retry step.");
            	ProjectUtils.sleep(5);
            	current++;
            	return sendEmail(username, password, from, to, cc, bcc, subject, msg, numberOfRetries);
            	}
            else{
            	current = 1;
    			return false;
            	}
		}
	}

    /**
     * Deletes all e-mail messages whose subject field contain
     * a string specified by 'subjectToDelete'
     * @param userName
     * @param password
     * @param subjectToDelete delete if the message's subject contains this value.
     * @param folder
     */
	
    private static Boolean deleteMessages(String userName, String password, String folder, String subjectToDelete, int numberOfRetries, Boolean DeleteAllMessages) throws NullPointerException{ 
        if(DeleteAllMessages == false){
        	Reporter.logStep("Deletion of messages that contain name '" + subjectToDelete + "' from folder '" + folder + "'");
        } else{
        	Reporter.logStep("Deletion of all messages from folder '" + folder + "'");
        }
    	Session session = Session.getDefaultInstance(getServerProperty());
        try {
            // connects to the message store
            Store store = session.getStore("imap");
            store.connect(userName, password);
 
            // opens the folder
            Folder selectedFolder;
            if(folder.equalsIgnoreCase("Sent Mail") || 
            		folder.equalsIgnoreCase("Drafts") ||
            		folder.equalsIgnoreCase("Trash") ||
            		folder.equalsIgnoreCase("All Mail") ||
            		folder.equalsIgnoreCase("Important") ||
            		folder.equalsIgnoreCase("Starred") ||
            		folder.equalsIgnoreCase("Spam") ||
            		folder.equalsIgnoreCase("Outbox")){
                selectedFolder = store.getFolder("[Gmail]/" + folder);
            }
            else{
                selectedFolder = store.getFolder(folder);
            }
            selectedFolder.open(Folder.READ_WRITE);
 
            // fetches new messages from server
            Message[] arrayMessages = selectedFolder.getMessages();
            if(arrayMessages.length != 0){
                for (int i = 0; i < arrayMessages.length; i++) {
                    Message message = arrayMessages[i];
                    if(message.getSubject() != null){
                    	String subject = message.getSubject();
                        if(DeleteAllMessages == false){
                            if (subject.contains(subjectToDelete)) {
                        		message.setFlag(Flags.Flag.DELETED, true);
                        		Reporter.logSubOperation("Marked DELETE for message: " + subject + "\n");
                            } 
                    	} else{
                    		message.setFlag(Flags.Flag.DELETED, true);
                    		Reporter.logSubOperation("Marked DELETE for message: " + subject + "\n");
                    	}
                    }
                }
        		Reporter.logSubOperation("All messages was deleted." + "\n");
            }
            else{
        		Reporter.logSubOperation("No messages to delete." + "\n");
            }

            // expunges the folder to remove messages which are marked deleted
            boolean expunge = true;
            selectedFolder.close(expunge);
 
            // another way:
            //folderInbox.expunge();
            //folderInbox.close(false);
 
            // disconnect
            store.close();
			Reporter.logPassedStep();
			current = 1;
			return true;
        } catch (NoSuchProviderException ex) {
        	Reporter.logFailed("No provider.");
            if (current < numberOfRetries){
            	Reporter.logInfo("Retry step.");
            	ProjectUtils.sleep(5);
            	current++;
            	return deleteMessages(userName, password, folder, subjectToDelete, numberOfRetries, DeleteAllMessages);
            	}
            else{
            	current = 1;
    			return false;
            	}           
        } catch (MessagingException ex) {
        	Reporter.logFailed("Could not connect to the message store.");
            if (current < numberOfRetries){
            	Reporter.logInfo("Retry step.");
            	ProjectUtils.sleep(5);
            	current++;
            	return deleteMessages(userName, password, folder, subjectToDelete, numberOfRetries, DeleteAllMessages);
            	}
            else{
            	current = 1;
    			return false;
            	}
        }
    }
    
    public static void deleteMessages(String userName, String password, String folder, String subjectToDelete, int numberOfRetries){
    	deleteMessages(userName, password, folder, subjectToDelete, numberOfRetries, false);
    }
    
    public static void deleteMessages(String userName, String password, String folder, int numberOfRetries){
    	String subjectToDelete = "Deletion of all messages";
    	deleteMessages(userName, password, folder, subjectToDelete, numberOfRetries, true);
    }
    
    /**
     * Create folder that name contain a string
     * @param userName
     * @param password
     * @param folderName
     */
    
    public static Boolean createFolder(String userName, String password, String folderName, int numberOfRetries)   
    {   
        Reporter.logStep("Create folder with name: " + folderName);
    	Session session = Session.getDefaultInstance(getServerProperty());
        Reporter.logSubOperation("connecting store..");
        boolean isCreated = true;   

        try  
        {   
            Store store = session.getStore("imap");
            store.connect(userName, password);
            Reporter.logSubOperation("connected !");
            Folder defaultFolder = store.getDefaultFolder();
            Folder newFolder = defaultFolder.getFolder(folderName);
            isCreated = newFolder.create(Folder.HOLDS_MESSAGES);
            
            // disconnect
            store.close();
            Reporter.logSubOperation("Created: " + isCreated + "\n");
            if(isCreated == true){
    			Reporter.logPassedStep();
            } else{
            	Reporter.logFailed("Error during creating the folder with name: " + folderName + ". May the folder has already created.");
            }
            current = 1;
            return true;
        } catch (MessagingException e)
        {   
        	Reporter.logFailed("Error creating folder: " + e.getMessage());
            isCreated = false;  
            if (current < numberOfRetries){
            	Reporter.logInfo("Retry step.");
            	ProjectUtils.sleep(5);
            	current++;
            	return createFolder(userName, password, folderName, numberOfRetries);   
            	}
            else{
            	current = 1;
    			return false;
            	}
        }   
    }
    
    /**
     * Deletes folder that name contain a string
     * @param userName
     * @param password
     * @param folder
     */
    
    public static Boolean deleteFolder(String userName, String password, String folder, int numberOfRetries) { 
        Reporter.logStep("Deletion folder with name: " + folder);
    	Session session = Session.getDefaultInstance(getServerProperty());
        try {
            // connects to the message store
            Reporter.logSubOperation("connecting store...");
            Store store = session.getStore("imap");
            store.connect(userName, password);
            Reporter.logSubOperation("connected !" + "\n");

            // deletes the folder
//            Folder folderToDelete = store.getFolder(folder);
            Folder[] arrayFolders = store.getDefaultFolder().list("*");

            
            if(arrayFolders.length != 0){
                for (int i = 0; i < arrayFolders.length; i++) {
                	Folder currentFolder = arrayFolders[i];
                    if(currentFolder.getName() != null){
                    	String name = currentFolder.getName();
                        if (name.toLowerCase().contains(folder.toLowerCase())) {
                        	currentFolder.delete(true); 
                        	Reporter.logSubOperation("Marked DELETE for folder: " + name + "\n");
                        } 
                    }
                }
                Reporter.logSubOperation("All folders was deleted." + "\n");
            }
            else{
            	Reporter.logSubOperation("No folders to delete." + "\n");
            }           
            // disconnect
            store.close();
			Reporter.logPassedStep();
            current = 1;
            return true;
        } catch (NoSuchProviderException ex) {
            Reporter.logFailed("No provider.");
            if (current < numberOfRetries){
            	Reporter.logInfo("Retry step.");
            	ProjectUtils.sleep(5);
            	current++;
            	return deleteFolder(userName, password, folder, numberOfRetries);   
            	}
            else{
            	current = 1;
    			return false;
            	}
        } 
        catch (FolderNotFoundException e) {
        	Reporter.logSubOperation("Folder '" + folder + "' not found.");
			Reporter.logPassedStep();
            return true;
		}
        catch (MessagingException ex) {
        	Reporter.logFailed("Could not connect to the message store.");
            if (current < numberOfRetries){
            	Reporter.logInfo("Retry step.");
            	ProjectUtils.sleep(5);
            	current++;
            	return deleteFolder(userName, password, folder, numberOfRetries);   
            	}
            else{
            	current = 1;
    			return false;
            	}
        }
    }
}