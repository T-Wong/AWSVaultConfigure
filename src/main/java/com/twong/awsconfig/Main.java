package com.twong.awsconfig;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	private final static String SECRET_PATH = "v1/aws/creds/deploy";		// default path to the AWS backend secret in vault
	
	/**
	 * Main entry point into the AWSVaultConfig program. It will prompt the user for
	 * input and then get the secret from the vault instance.
	 * 
	 * @param args	command line arguments passed into the program. The first argument
	 * 				passed will determine the vault path to the AWS backend secret in vault.
	 * 				If no argument is passed in the path "v1/aws/creds/deploy" will be used.
	 */
	public static void main(String[] args) {
		String secretPath = SECRET_PATH;
		
		if(args.length != 0) {
			secretPath = args[0];
		}
		
		Vault vault = printPrompt();
		
		String[] awsCreds = vault.getAWSSecret(secretPath);
		if(saveCreds(awsCreds)) {
			System.out.println("New AWS credentials sucessfully saved to your AWS configuration.");
		}
		
		return;
	}
	
	/**
	 * Prints the program summary and asks the user for input. 
	 * 
	 * @return	a new Vault object with vault address and vault token set.
	 */
	private static Vault printPrompt() {
		Scanner in = new Scanner(System.in);
		String vaultAddress = null;
		char[] vaultToken = null;
		
		// Print welcome message
		System.out.println("Welcome! This program will get dynamic AWS credentials from HashiCorp Vault and save it to your local AWS config.");
		System.out.println("This program requires that you have awscli installed beforehand.\n");
		
		try {
			// Get vault address
			System.out.print("Vault root address (E.g. http://10.0.44.254:8200): ");
			vaultAddress = in.nextLine().trim();
			
			// Get vault token
			System.out.print("Vault token (hidden): ");
			vaultToken = System.console().readPassword();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			in.close();
		}
		
		return new Vault(vaultAddress, vaultToken);
	}

	/**
	 * Saves the AWS credentials to a file so that AWS knows about them for the current user.
	 * 
	 * @param creds		the AWS credentials to save. creds[0] is the AWS Access Key ID and
	 * 					creds[1] is the AWS Secret Access Key.
	 * @return	if the method was able to successfully save the credentials.
	 */
	private static boolean saveCreds(String[] creds) {
		String command = "echo -ne \"" + creds[0] + "\n " + creds[1] + "\n\n\n\" | /usr/bin/aws configure";

		try {
			Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
