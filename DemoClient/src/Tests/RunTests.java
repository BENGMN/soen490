package Tests;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class RunTests {
	public static void main(String[] args) {
		String response;
		try {
			
			//Test Create User Command
			System.out.print("Create Normal user with valid parameters: ");
			response = CommandTests.testCreateUserCommand("testing@test.com", "capstone", "USER_NORMAL", "bin");
			System.out.println("Response: " + response);
			String uidNormal = response;
			
			//used to test getMessageIds
			System.out.print("\nCreate Advertiser user with valid parameters: ");
			response = CommandTests.testCreateUserCommand("testingAd@test.com", "capstone", "USER_ADVERTISER", "bin");
			System.out.println("Response: " + response);
			String uidAdvertiser = response;
			
			System.out.print("\nCreate user with an invalid email: ");
			response = CommandTests.testCreateUserCommand("test", "capstone", "USER_NORMAL", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nCreate user with an invalid password: ");
			response = CommandTests.testCreateUserCommand("testing2@test.com", "", "USER_NORMAL", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nCreate user with an invalid user type: ");
			response = CommandTests.testCreateUserCommand("testing3@test.com", "capstone", "test", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nCreate user with an invalid response type: ");
			response = CommandTests.testCreateUserCommand("testing4@test.com", "capstone", "USER_NORMAL", "test");
			System.out.println("Response: " + response);
			
			System.out.println("\n-----");
			
			//Test Create Message Command
			System.out.print("\nCreate message with valid parameters: ");
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "45", "50", "testing@test.com");
			System.out.println("Response: " + response);
			String mid = response;
			
			//used to test getMessageIds
			System.out.print("\nCreate Advertisement message with valid parameters: ");
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "45", "50", "testingAd@test.com");
			System.out.println("Response: " + response);
			
			System.out.print("\nCreate message with an invalid latitude: ");
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "test", "45", "50", "testing@test.com");
			System.out.println("Response: " + response);
			
			System.out.print("\nCreate message with an invalid longitude: ");
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "test", "50", "testing@test.com");
			System.out.println("Response: " + response);
	
			System.out.print("\nCreate message with an invalid speed: ");
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "45", "test", "testing@test.com");
			System.out.println("Response: " + response);

			System.out.print("\nCreate message with an invalid email: ");	
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "45", "50", "test");
			System.out.println("Response: " + response);
			
			System.out.print("\nCreate message with a non-existing email: ");	
			response = CommandTests.testCreateMessage(new File(System.getProperty("user.dir") + "\\src\\Client\\Test.amr"), "-73", "45", "50", "abc@abc.com");
			System.out.println("Response: " + response);
			
			System.out.println("\n-----");

			//Test Read Message Command		
			System.out.print("\nRead messages with a valid message id: ");
			response = CommandTests.testReadMessageCommand(mid, "bin"); 
			System.out.println("Response: " + response);
			
			System.out.print("\nRead messages with an invalid message id: ");
			response = CommandTests.testReadMessageCommand("test", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nRead messages with a non-existing message id: ");
			response = CommandTests.testReadMessageCommand("1", "bin");
			System.out.println("Response: " + response);

			System.out.print("\nRead messages with an invalid response type: ");		
			response = CommandTests.testReadMessageCommand(mid, "test");
			System.out.println("Response: " + response );

			System.out.println("\n-----");
			
			//Test Upvote Message Command
			System.out.print("\nUpvote messages with valid message id: ");
			response = CommandTests.testUpvoteMessageCommand(mid);
			System.out.println("Response: " + response);
			
			//Used for testing getMessageIds
			System.out.print("\nUpvote messages with valid message id: ");
			response = CommandTests.testUpvoteMessageCommand(mid);
			System.out.println("Response: " + response);

			System.out.print("\nUpvote messages with an invalid message id: ");
			response = CommandTests.testUpvoteMessageCommand("test");
			System.out.println("Response: " + response);
			
			System.out.print("\nUpvote messages with a non-existing message id: ");
			response = CommandTests.testUpvoteMessageCommand("1");
			System.out.println("Response: " + response);

			System.out.println("\n-----");

			//Test DownVote Message Command
			System.out.print("\nDownvote messages with a valid message id: ");
			response = CommandTests.testDownvoteMessageCommand(mid);
			System.out.println("Response: " + response);
			
			System.out.print("\nDownvote messages with an invalid message id: ");
			response = CommandTests.testDownvoteMessageCommand("test");
			System.out.println("Response: " + response);
			
			System.out.print("\nDownvote messages with a non-existing message id: ");
			response = CommandTests.testDownvoteMessageCommand("1");
			System.out.println("Response: " + response);

			System.out.println("\n-----");
			
			//Test Get Message ids Command
			System.out.print("\nGet message ids with date sorting: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "50", "bin", "date", "false", "5");
			System.out.println("Response: " + response);
			
			System.out.print("\nGet message ids with random sorting: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "50", "bin", "random", "true", "5");
			System.out.println("Response: " + response);
		
			System.out.print("\nGet message ids with rating sorting: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "50", "bin", "date", "false", "5");
			System.out.println("Response: " + response);
			
			System.out.print("\nGet message ids with an invalid longitude: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "test", "50", "bin", "date", "false", "5");
			System.out.println("Response: " + response);

			System.out.print("\nGet message ids with an invalid latitude: ");
			response = CommandTests.testGetMessageIdsCommand("test", "45", "50", "bin", "date", "false", "5");
			System.out.println("Response: " + response);

			System.out.print("\nGet message ids with an invalid sort type: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "50", "bin", "test", "false", "5");
			System.out.println("Response: " + response);

			System.out.print("\nGet message ids with an invalid speed: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "test", "bin", "date", "false", "5");
			System.out.println("Response: " + response);
			
			System.out.print("\nGet message ids an advertiser: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "50", "bin", "random", "true", "5");
			System.out.println("Response: " + response);
			
			System.out.print("\nGet message ids without an advertiser parameter: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "50", "bin", "date", null, "5");
			System.out.println("Response: " + response);
			
			System.out.print("\nGet message ids with an invalid advertiser: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "50", "bin", "date", "test", "5");
			System.out.println("Response: " + response);
			
			System.out.print("\nGet message ids without a limit parameter: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "50", "bin", "date", "false", null);
			System.out.println("Response: " + response);
			
			System.out.print("\nGet message ids with an invalid limit: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "50", "bin", "date", "false", "test");
			System.out.println("Response: " + response);
			
			System.out.print("\nGet message ids with a limit too large: ");
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "50", "bin", "date", "false", "1000");
			System.out.println("Response: " + response);
			
			System.out.println("\n-----");
				
			//Test Delete Message Command
			System.out.print("\nDelete message with a valid mid: ");
			response = CommandTests.testDeleteMessage(mid);
			System.out.println("Response: " + response);
			
			System.out.print("\nDelete message with an invalid mid: ");
			response = CommandTests.testDeleteMessage("test");
			System.out.println("Response: " + response);
			
			System.out.print("\nDelete message with a non-existing mid: ");
			response = CommandTests.testDeleteMessage("1");
			System.out.println("Response: " + response);

			System.out.println("\n-----");

			//Test Read User Command
			System.out.print("\nRead user with valid parameters: ");
			response = CommandTests.testReadUserCommand(uidNormal, "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nRead user with an invalid user id: " );
			response = CommandTests.testReadUserCommand("test", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nRead user with a non-existing user id: " );
			response = CommandTests.testReadUserCommand("1", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nRead user with an invalid response type: ");
			response = CommandTests.testReadUserCommand(uidNormal, "test");
			System.out.println("Response: " + response);

			System.out.println("\n-----");

			//Test Update User Command
			System.out.print("\nUpdate User with valid parameters: ");
			response = CommandTests.testUpdateUserCommand(uidNormal, "newPassword", "USER_NORMAL", "bin", "1");
			System.out.println("Response: " + response);

			System.out.print("\nUpdate User with an invalid user id: ");
			response = CommandTests.testUpdateUserCommand("test", "newPassword", "USER_NORMAL", "bin", "1");
			System.out.println("Response: " + response);
			
			System.out.print("\nUpdate User with a non-existing user id: ");
			response = CommandTests.testUpdateUserCommand("1", "newPassword", "USER_NORMAL", "bin", "1");
			System.out.println("Response: " + response);
			
			System.out.print("\nUpdate User with an invalid password: ");
			response = CommandTests.testUpdateUserCommand(uidNormal, "", "USER_NORMAL", "bin", "1");
			System.out.println("Response: " + response );
			
			System.out.print("\nUpdate User with an invalid user type: ");
			response = CommandTests.testUpdateUserCommand(uidNormal, "newPassword", "test", "bin", "1");
			System.out.println("Response: " + response);
			
			System.out.print("\nUpdate User with an invalid response type: ");
			response = CommandTests.testUpdateUserCommand(uidNormal, "newPassword", "USER_NORMAL", "test", "1");
			System.out.println("Response: " + response);
			
			System.out.print("\nUpdate User with an invalid version: ");
			response = CommandTests.testUpdateUserCommand(uidNormal, "newPassword", "USER_NORMAL", "bin", "test");
			System.out.println("Response: " + response);

			System.out.println("\n-----");

			//Test Delete User Command
			System.out.print("\nDelete user with a invalid version: ");
			response = CommandTests.testDeleteUserCommand(uidNormal, "test", "bin");
			System.out.println("Response: " + response);
			
			System.out.print("\nDelete user with an invalid response type: ");
			response = CommandTests.testDeleteUserCommand(uidNormal, "2", "test");
			System.out.println("Response: " + response);
			
			System.out.print("\nDelete user with a non-existant user id: ");
			response = CommandTests.testDeleteUserCommand("1", "2", "bin");
			System.out.println("Response: " + response);
			
			
			System.out.print("\nDelete Normal user with valid parameters: ");
			response = CommandTests.testDeleteUserCommand(uidNormal, "2", "bin");
			System.out.println("Response: " + response);
			
			//used for testing getMessageIds
			System.out.print("\nDelete Advertisement user with valid parameters: ");
			response = CommandTests.testDeleteUserCommand(uidAdvertiser, "1", "bin");
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
