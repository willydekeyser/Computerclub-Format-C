package willydekeyser.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptTest {

	@Test
	public void bcryptTest() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String paswoord = encoder.encode("");
		System.out.println("Paswoord: " + paswoord);
	}
}
