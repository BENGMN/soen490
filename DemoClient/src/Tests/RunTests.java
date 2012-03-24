package Tests;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class RunTests {
	public static void main(String[] args) {
		String response;
		try {
			
			//Test Create User Command
			response = CommandTests.testCreateUserCommand("testing@test.com", "capstone", "USER_NORMAL", "bin");
			System.out.println("Create user with valid parameters: " + response);
			String uid = response;
			
			response = CommandTests.testCreateUserCommand("", "capstone", "USER_NORMAL", "bin");
			System.out.println("Create user with invalid email: " + response);
			
			response = CommandTests.testCreateUserCommand("testing2@test.com", "", "USER_NORMAL", "bin");
			System.out.println("Create user with invalid password: " + response);
			
			response = CommandTests.testCreateUserCommand("testing3@test.com", "capstone", "", "bin");
			System.out.println("Create user with invalid user type: " + response);
			
			response = CommandTests.testCreateUserCommand("testing4@test.com", "capstone", "USER_NORMAL", "");
			System.out.println("Create user with invalid reponse type: " + response);
			
			//Test Create Message Command
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "45", "50", "testing@test.com");
			System.out.println("\nCreate message with valid parameters: " + response);
			String mid = response;
			
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "", "45", "50", "testing@test.com");
			System.out.println("Create message with invalid latitude: " + response);
			
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "", "50", "testing@test.com");
			System.out.println("Create message with invalid longitude: " + response);
			
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "45", "", "testing@test.com");
			System.out.println("Create message with invalid speed: " + response);
			
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "45", "50", "");
			System.out.println("Create message with invalid email: " + response);
			
			//Test Get MessageIds Command
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "100000", "bin", "created_type");
			System.out.println("\nGet message ids with valid parameters: " + response);
			
			response = CommandTests.testGetMessageIdsCommand("-73", "abc", "100000", "bin", "created_type");
			System.out.println("Get message ids with invalid longitude: " + response);
			
			response = CommandTests.testGetMessageIdsCommand("abn", "45", "100000", "bin", "created_type");
			System.out.println("Get message ids with invalid latitude: " + response);
			
			response = CommandTests.testGetMessageIdsCommand("abn", "45", "100000", "bin", "");
			System.out.println("Get message ids with invalid sort type: " + response);
			
			//Test Read Message Command		
			response = CommandTests.testReadMessageCommand(mid, "bin"); 
			System.out.println("\nRead messages with a valid message id: " + response);
			
			response = CommandTests.testReadMessageCommand("", "bin");
			System.out.println("Read messages with invalid message id: " + response);
			
			response = CommandTests.testReadMessageCommand(mid, "");
			System.out.println("Read messages with invalid response type: " + response);		
			
			//Test Upvote Message Command
			response = CommandTests.testUpVoteMessageCommand(mid);
			System.out.println("\nUpvote messages with valid message id: " + response);
			
			response = CommandTests.testUpVoteMessageCommand("");
			System.out.println("Upvote messages with invalid message id: " + response);
			
			//Test DownVote Message Command
			response = CommandTests.testDownVoteMessageCommand(mid);
			System.out.println("\nDownvote messages with valid message id: " + response);
			
			response = CommandTests.testDownVoteMessageCommand("");
			System.out.println("Downvote messages with invalid message id: " + response);
			
			//Test Delete Message Command
			response = CommandTests.testDeleteMessage(mid);
			System.out.println("\nDelete message with valid mid: " + response);
			
			response = CommandTests.testDeleteMessage("");
			System.out.println("Delete message with invalid mid: " + response);
			
			response = CommandTests.testDeleteMessage("1");
			System.out.println("Delete message with not existing mid: " + response);
			
			//Test Read User Command
			response = CommandTests.testReadUserCommand("12345", "bin");
			System.out.println("\nRead user with valid parameters: " + response);
			
			response = CommandTests.testReadUserCommand("", "bin");
			System.out.println("Read user with invalid user id: " + response);
			
			response = CommandTests.testReadUserCommand("12345", "");
			System.out.println("Read user with invalid response type: " + response);
			
			//Test Update User Command
			response = CommandTests.testUpdateUserCommand(uid, "newPassword", "USER_NORMAL", "bin", "1");
			System.out.println("\nUpdate User with valid parameters: " + response);
			
			//Test Delete User Command
			response = CommandTests.testDeleteUserCommand(uid, "", "bin");
			System.out.println("\nDelete user with invalid version: " + response);
			
			response = CommandTests.testDeleteUserCommand(uid, "2", "");
			System.out.println("Delete user with invalid response type: " + response);
			
			response = CommandTests.testDeleteUserCommand(uid, "2", "bin");
			System.out.println("Delete user with valid parameters: " + response);

			//Test Get Server Parameters Command
			response = CommandTests.testGetServerParamsCommand();
			System.out.println("\nGet server parameters: " + response);
			
			//Test Ping Command
			response = CommandTests.testPingCommand();
			System.out.println("\nPing: " + response);
			
			//Test Unsupported Command
			response = CommandTests.testUnsupportedCommand();
			System.out.println("\nUnsupported Command: " + response);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
