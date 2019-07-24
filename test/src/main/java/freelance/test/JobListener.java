package freelance.test;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.json.JSONObject;


import freelance.test.AntlrTestParser.JobContext;
import freelance.test.AntlrTestParser.JobsContext;

public class JobListener extends AntlrTestBaseListener {
	JSONObject baseJson;
	@Override
	public void enterJobs(JobsContext ctx) {
		baseJson=new JSONObject();
	}
	@Override
	public void enterJob(JobContext ctx) {
		String tag = ctx.tagName.getText();
		String job=ctx.jobName.getText();
		String description=ctx.desc.getText();
		JSONObject jobJson=new JSONObject();
		jobJson.put(job, description);
		baseJson.put(tag, jobJson);
	}
	
	
	
	public JSONObject getBaseJson() {
		return baseJson;
	}
	public static JSONObject calculateJson(String jobDescription) throws TestException {
		AntlrTestLexer lexer = new AntlrTestLexer(new ANTLRInputStream(jobDescription));
		ErrorListener parserErrorListener = new ErrorListener();
		ErrorListener lexerErrorListener = new ErrorListener();
		lexer.addErrorListener(lexerErrorListener);

		// Get a list of matched tokens
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		AntlrTestParser parser = new AntlrTestParser(tokens);
		parser.addErrorListener(parserErrorListener);

		ParseTreeWalker walker = new ParseTreeWalker();
		JobListener listener = new JobListener();
		walker.walk(listener, parser.jobs());

		if ((lexerErrorListener.getErrorsStr().size() > 0) || parserErrorListener.getErrorsStr().size() > 0) {
			throw new TestException(lexerErrorListener.getErrorsStr(), parserErrorListener.getErrorsStr());
		}
		return listener.getBaseJson();
	}
	

}
