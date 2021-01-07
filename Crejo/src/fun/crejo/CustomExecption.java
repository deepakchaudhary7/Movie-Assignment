package fun.crejo;

@SuppressWarnings("serial")
public class CustomExecption extends RuntimeException {
	String execption;

	public CustomExecption(String execption) {
		super(execption);
	}
	
	
}
