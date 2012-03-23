package Tests;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class RunTests {
	public static void main(String[] args) {
		String response;
		try {
			response = CommandTests.testCreateUserCommand("testing9@test.com", "capstone", "USER_NORMAL", "bin");
			System.out.println("Create user with valid parameters: " + response);
			
			response = CommandTests.testCreateUserCommand("", "capstone", "USER_NORMAL", "bin");
			System.out.println("Create user with an invalid email: " + response);
			
			response = CommandTests.testCreateUserCommand("testing2@test.com", "", "USER_NORMAL", "bin");
			System.out.println("Create user with an invalid password: " + response);
			
			response = CommandTests.testCreateUserCommand("testing3@test.com", "capstone", "", "bin");
			System.out.println("Create user with an invalid user type: " + response);
			
			response = CommandTests.testCreateUserCommand("testing4@test.com", "capstone", "USER_NORMAL", "");
			System.out.println("Create user with an invalid reponse type: " + response);
			
			response = CommandTests.testGetMessageIdsCommand("-73", "45", "100000", "bin");
			System.out.println("\nGet message ids with valid parameters: " + response);
			
			response = CommandTests.testGetMessageIdsCommand("-73", "abc", "100000", "bin");
			System.out.println("Get message ids with an invalid longitude: " + response);
			
			response = CommandTests.testGetMessageIdsCommand("abn", "45", "100000", "bin");
			System.out.println("Get message ids with an invalid latitude: " + response);
			
			response = CommandTests.testReadMessageCommand("215395808838843885193843599665064", "bin");
			System.out.println("\nRead messages with a valid message id: " + response);
			
			response = CommandTests.testReadMessageCommand("", "bin");
			System.out.println("Read messages with an invalid message id: " + response);
			
			response = CommandTests.testReadMessageCommand("215395808838843885193843599665064", "");
			System.out.println("Read messages with an invalid response type: " + response);
			
			response = CommandTests.testUpVoteMessageCommand("215395808838843885193843599665064");
			System.out.println("\nUpvote messages with valid message id: " + response);
			
			response = CommandTests.testUpVoteMessageCommand("");
			System.out.println("Upvote messages with an invalid message id: " + response);
			
			response = CommandTests.testDownVoteMessageCommand("215395808838843885193843599665064");
			System.out.println("\nDownvote messages with valid message id: " + response);
			
			response = CommandTests.testDownVoteMessageCommand("");
			System.out.println("Downvote messages with an invalid message id: " + response);
			
			response = CommandTests.testReadUserCommand("12345", "bin");
			System.out.println("\nRead user with valid parameters: " + response);
			
			response = CommandTests.testReadUserCommand("", "bin");
			System.out.println("Read user with an invalid user id: " + response);
			
			response = CommandTests.testReadUserCommand("12345", "");
			System.out.println("Read user with an invalid response type: " + response);
			

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
