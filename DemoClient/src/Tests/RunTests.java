package Tests;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class RunTests {
	public static void main(String[] args) {
		String response;
		try {
			
			//Test Create User Command
			System.out.print("Create user with valid parameters: ");
			response = CommandTests.testCreateUserCommand("testing@test.com", "capstone", "USER_NORMAL", "bin");
			System.out.println("Response: " + response);
			String uid = response;
			
			System.out.print("\nCreate user with invalid email: ");
			response = CommandTests.testCreateUserCommand("test", "capstone", "USER_NORMAL", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nCreate user with invalid password: ");
			response = CommandTests.testCreateUserCommand("testing2@test.com", "", "USER_NORMAL", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nCreate user with invalid user type: ");
			response = CommandTests.testCreateUserCommand("testing3@test.com", "capstone", "test", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nCreate user with invalid response type: ");
			response = CommandTests.testCreateUserCommand("testing4@test.com", "capstone", "USER_NORMAL", "test");
			System.out.println("Response: " + response);
			
			System.out.println("\n-----");
			
			//Test Create Message Command
			System.out.print("\nCreate message with valid parameters: ");
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "45", "50", "testing@test.com");
			System.out.println("Response: " + response);
			String mid = response;
			
			System.out.print("\nCreate message with invalid latitude: ");
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "test", "45", "50", "testing@test.com");
			System.out.println("Response: " + response);
			
			System.out.print("\nCreate message with invalid longitude: ");
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "test", "50", "testing@test.com");
			System.out.println("Response: " + response);
	
			System.out.print("\nCreate message with invalid speed: ");
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "45", "test", "testing@test.com");
			System.out.println("Response: " + response);

			System.out.print("\nCreate message with invalid email: ");	
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "45", "50", "test");
			System.out.println("Response: " + response);
			
			System.out.print("\nCreate message with non-existing email: ");	
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "45", "50", "abc@abc.com");
			System.out.println("Response: " + response);

			System.out.println("\n-----");
			
			//Test Get MessageIds Command
			System.out.print("\nGet message ids with valid parameters: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "50", "bin", "user_rating");
			System.out.println("Response: " + response);
			
			System.out.print("\nGet message ids with invalid longitude: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "test", "50", "bin", "user_rating");
			System.out.println("Response: " + response);

			System.out.print("\nGet message ids with invalid latitude: ");
			response = CommandTests.testGetMessageIdsCommand("test", "45", "50", "bin", "user_rating");
			System.out.println("Response: " + response);

			System.out.print("\nGet message ids with invalid sort type: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "50", "bin", "test");
			System.out.println("Response: " + response);

			System.out.print("\nGet message ids with invalid speed: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "test", "bin", "user_rating");
			System.out.println("Response: " + response);

			System.out.println("\n-----");

			//Test Read Message Command		
			System.out.print("\nRead messages with a valid message id: ");
			response = CommandTests.testReadMessageCommand(mid, "bin"); 
			System.out.println("Response: " + response);
			
			System.out.print("\nRead messages with invalid message id: ");
			response = CommandTests.testReadMessageCommand("test", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nRead messages with non-existing message id: ");
			response = CommandTests.testReadMessageCommand("1", "bin");
			System.out.println("Response: " + response);

			System.out.print("\nRead messages with invalid response type: ");		
			response = CommandTests.testReadMessageCommand(mid, "test");
			System.out.println("Response: " + response );

			System.out.println("\n-----");
			
			//Test Upvote Message Command
			System.out.print("\nUpvote messages with valid message id: ");
			response = CommandTests.testUpvoteMessageCommand(mid);
			System.out.println("Response: " + response);

			System.out.print("\nUpvote messages with invalid message id: ");
			response = CommandTests.testUpvoteMessageCommand("test");
			System.out.println("Response: " + response);
			
			System.out.print("\nUpvote messages with non-existing message id: ");
			response = CommandTests.testUpvoteMessageCommand("1");
			System.out.println("Response: " + response);

			System.out.println("\n-----");

			//Test DownVote Message Command
			System.out.print("\nDownvote messages with valid message id: ");
			response = CommandTests.testDownvoteMessageCommand(mid);
			System.out.println("Response: " + response);
			
			System.out.print("\nDownvote messages with invalid message id: ");
			response = CommandTests.testDownvoteMessageCommand("test");
			System.out.println("Response: " + response);
			
			System.out.print("\nDownvote messages with non-existing message id: ");
			response = CommandTests.testDownvoteMessageCommand("1");
			System.out.println("Response: " + response);

			System.out.println("\n-----");

			//Test Delete Message Command
			System.out.print("\nDelete message with valid mid: ");
			response = CommandTests.testDeleteMessage(mid);
			System.out.println("Response: " + response);
			
			System.out.print("\nDelete message with invalid mid: ");
			response = CommandTests.testDeleteMessage("test");
			System.out.println("Response: " + response);
			
			System.out.print("\nDelete message with non-existing mid: ");
			response = CommandTests.testDeleteMessage("1");
			System.out.println("Response: " + response);

			System.out.println("\n-----");

			//Test Read User Command
			System.out.print("\nRead user with valid parameters: ");
			response = CommandTests.testReadUserCommand(uid, "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nRead user with invalid user id: " );
			response = CommandTests.testReadUserCommand("test", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nRead user with non-existing user id: " );
			response = CommandTests.testReadUserCommand("1", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nRead user with invalid response type: ");
			response = CommandTests.testReadUserCommand(uid, "test");
			System.out.println("Response: " + response);

			System.out.println("\n-----");

			//Test Update User Command
			System.out.print("\nUpdate User with valid parameters: ");
			response = CommandTests.testUpdateUserCommand(uid, "newPassword", "USER_NORMAL", "bin", "1");
			System.out.println("Response: " + response);

			System.out.print("\nUpdate User with invalid user id: ");
			response = CommandTests.testUpdateUserCommand("test", "newPassword", "USER_NORMAL", "bin", "1");
			System.out.println("Response: " + response);
			
			System.out.print("\nUpdate User with non-existing user id: ");
			response = CommandTests.testUpdateUserCommand("1", "newPassword", "USER_NORMAL", "bin", "1");
			System.out.println("Response: " + response);
			
			System.out.print("\nUpdate User with invalid password: ");
			response = CommandTests.testUpdateUserCommand(uid, "", "USER_NORMAL", "bin", "1");
			System.out.println("Response: " + response );
			
			System.out.print("\nUpdate User with invalid user type: ");
			response = CommandTests.testUpdateUserCommand(uid, "newPassword", "test", "bin", "1");
			System.out.println("Response: " + response);
			
			System.out.print("\nUpdate User with invalid response type: ");
			response = CommandTests.testUpdateUserCommand(uid, "newPassword", "USER_NORMAL", "test", "1");
			System.out.println("Response: " + response);
			
			System.out.print("\nUpdate User with invalid version: ");
			response = CommandTests.testUpdateUserCommand(uid, "newPassword", "USER_NORMAL", "bin", "test");
			System.out.println("Response: " + response);
			
			System.out.println("\n-----");

			//Test Delete User Command
			System.out.print("\nDelete user with invalid version: ");
			response = CommandTests.testDeleteUserCommand(uid, "test", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nDelete user with invalid response type: ");
			response = CommandTests.testDeleteUserCommand(uid, "2", "test");
			System.out.println("Response: " + response);
			
			System.out.print("\nDelete user with non-existant user id: ");
			response = CommandTests.testDeleteUserCommand("1", "2", "bin");
			System.out.println("Response: " + response);
			
			
			System.out.print("\nDelete user with valid parameters: ");
			response = CommandTests.testDeleteUserCommand(uid, "2", "bin");
			System.out.println("Response: " + response);

			System.out.println("\n-----");

			//Test Get Server Parameters Command
			System.out.print("\nGet server parameters: ");
			response = CommandTests.testGetServerParametersCommand();
			System.out.println("Response: " + response);
			
			System.out.println("\n-----");

			//Test Ping Command
			System.out.print("\nPing: ");
			response = CommandTests.testPingCommand();
			System.out.println("Response: " + response);

			System.out.println("\n-----");

			//Test Unsupported Command
			System.out.print("\nUnsupported Command: " + response);
			response = CommandTests.testUnsupportedCommand("test");
			System.out.println("Response: " + response);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
