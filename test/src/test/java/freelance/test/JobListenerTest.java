package freelance.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JobListenerTest {
	@Test
	public void testSimpleAntlr() throws TestException {
		assertEquals("{\"tag\":{\"job\":\"description\"}}",
				JobListener.calculateJson("tag job\n" + "description").toString());
	}

	@Test
	public void testTricky() throws TestException {
		assertEquals("{\"tag1\":{\"job1\":\"  des  cri  ption1\"},\"tag\":{\"job\":\"descri pti on\"}}",
				JobListener.calculateJson("    tag  job\n" + "descri pti on\n" + "\n" + "\n" + "\n" + "tag1 job1\n"
						+ "  des  cri  ption1\n" + "\n" + "\n" + "                     tag1 job1\n"
						+ "  des  cri  ption1").toString());
	}

	@Test
	public void testParsingException() {
		try {
			JobListener.calculateJson("    tag  job\n" + "descri pti on\n" + "\n" + "\n" + "\n" + "tag1 job1\n"
					+ "  des  cri  ption1\n" + "\n" + "\n" + "                     tag1 job1\n" + "");
			assertTrue(false);
		} catch (TestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
