package passwordmanagerr;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.text.Font; 
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;  


public class PasswordManager extends Application {
	TextField tf,passs;      
	Label response,prompt;
	public static void main (String [] args) {
		
	launch (args) ;
	}
	public void start (Stage myStage)throws Exception {

		  Text text = new Text(); 
	      myStage.show(); 
	      text.setText("Password Manager");
	      text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30)); 
	      text.setFill(Color.BLUE); 
		 File file = new File("password.txt");
		myStage.setTitle ("Password Manager") ;
		FlowPane rootNode = new FlowPane(60, 70);
		rootNode . setAlignment (Pos . CENTER);
		Scene myScene=new Scene(rootNode);
		myStage.setScene(myScene);
		prompt = new Label ("Website: ");
		response = new Label ("Enter website: ") ;
		Button findpass = new Button("Retreive a password");
		Button deletepass=new Button("Delete a password");
		Button generate=new Button("Generate a Random password");
		Button urown= new Button("Create your own password");
		Button Enter= new Button("Set password");
		tf=new TextField() ;
		tf.setPromptText("Enter Website" ) ;
		tf.setPrefColumnCount (20) ;
		passs=new TextField();
		passs.setPromptText("Enter the password");
		passs.setPrefColumnCount(20);
		tf.setOnAction (new EventHandler<ActionEvent> () {
			public void handle (ActionEvent ae) {
				response.setText("Website: "+tf.getText());
			}});
		passs.setOnAction (new EventHandler<ActionEvent> () {
			public void handle (ActionEvent ae) {
				response.setText("Password: "+passs.getText());
			}});
		urown.setOnAction (new EventHandler<ActionEvent> () {
			public void handle (ActionEvent ae) {
					response.setText ("Enter your own password");
					rootNode.getChildren().addAll(passs,Enter);
					Enter.setOnAction (new EventHandler<ActionEvent> () {
						public void handle (ActionEvent ae) {
							String website=tf.getText();
							String pass=passs.getText();
							if(website.isEmpty())
							{
								response.setText("Please enter the website");
							}
							else if(pass.isEmpty())
							{
								response.setText("Please enter the Password");
							}
							else
							{

								 appendStrToFile(file,"Website :"+ website);
							        appendStrToFile(file,"\t Password: "+pass+"\n");

								   System.out.println("Website is: "+ website);
								   System.out.println("password is: "+pass);
									response.setText("Website is : "+website+"  Password is: "+pass);
									rootNode.getChildren().removeAll(passs,Enter);
							}
							}
							
						});
					
			}
				});
		findpass.setOnAction (new EventHandler<ActionEvent> () {
					public void handle (ActionEvent ae) {
						String str=tf.getText();
						if(str.isEmpty())
						{
							response.setText("Please enter the website");
						}
						else
						{
						int count=0;
			        Scanner in = null;
			        try {
			            in = new Scanner(file);
			            while(in.hasNext())
			            {
			                String line=in.nextLine();
			                if(line.contains(str))
			                {
			                    System.out.println(line);

								response.setText (line);
			                    count++;
			                }
			            }
			        } catch (FileNotFoundException e) {
			            e.printStackTrace();
			        }
			        if(count==0)
			        {
			        	System.out.println("Website Password doesnt exist");
			        	response.setText("Website doesn't Exist");
			        }
					}
					}
				});

		deletepass.setOnAction (new EventHandler<ActionEvent> (){
					public void handle (ActionEvent ae) {
						String str=tf.getText();	
						int count;
							try {
								count=del(file,str);
								if(count==0)
								{
									response.setText("Website does not exist");
								}
								else
									response.setText(str+" Sucesssfully Deleted");
							} catch (IOException e) {
								e.printStackTrace();
							}
						
							
					}
				});
		generate.setOnAction (new EventHandler<ActionEvent> () {
					public void handle (ActionEvent ae) {
						String website=tf.getText();
						if(website.isEmpty())
						{
							response.setText("Please enter the website");
						}
						else {
						   char[] password=generatePassword(16);
						   String pass= new String(password);
					        appendStrToFile(file,"Website :"+ website);
					        appendStrToFile(file,"\t Password: "+pass+"\n");

						   System.out.println("Website is: "+ website);
						   System.out.println("password is: "+pass);
							response.setText("Website is : "+website+"  Password is: "+pass);
						}
					}
				});
				rootNode.setStyle("-fx-background-color:linear-gradient(to right, yellow,darkorange);");
				rootNode.getChildren().addAll(text,prompt,tf,findpass,deletepass,generate,urown, response);
				myStage.resizableProperty().setValue(false);
 				myStage.show();
	}
	private static char[] generatePassword(int length) {
	      String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
	      String specialCharacters = "!@#$%^&*?";
	      String numbers = "1234567890";
	      String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
	      Random random = new Random();
	      char[] password = new char[length];
	      password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
	      password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
	      password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
	      password[3] = numbers.charAt(random.nextInt(numbers.length()));
	   
	      for(int i = 4; i< length ; i++) {
	         password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
	      }
	      return password;
	   }

	    public static void appendStrToFile(File file,String str)
	    {	
	    	try {
	    		BufferedWriter out = new BufferedWriter(
	    				new FileWriter(file, true));
	    		out.write(str);
	    		out.close();
	    	}
	    	catch (IOException e) {
	    		System.out.println("exception occoured" + e);
	    	}
	    }
	    private static int del(File file,String str) throws IOException{
	    	int count=0;
	    	if(str.isEmpty())
	    	{
	    		return count;
	    	}
	            File tempFile = new File("C:\\Users\\rdnds\\Desktop\\java\\temp.txt");
	            Scanner in = null;
	            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
	            try {
		            in = new Scanner(file);
		            while(in.hasNext())
		            {
		                String line=in.nextLine();
		                
		                if(line.contains(str))
		                {
		                	count++;
		                   continue;
		                }
		                else
		                {
		                	 writer.write(line + System.getProperty("line.separator"));
		            }
		        }
	            }
	            catch (FileNotFoundException e) {
		            e.printStackTrace();
		        }
		        if(count==0)
		        {
		        	System.out.println("Website Password doesnt exist");
		        }
	            writer.close();
	            Scanner inn=null;
	            BufferedWriter writerr = new BufferedWriter(new FileWriter(file));
	            inn = new Scanner(tempFile);
	            while(inn.hasNext())
	            {
	                String line=inn.nextLine();
	               writerr.write(line + System.getProperty("line.separator"));
		        }
	            writerr.close();
	            inn.close();
	            tempFile.delete();
	            if(count!=0)
	            System.out.println("Successfully deleted "+str);
	            return count;
}
}

