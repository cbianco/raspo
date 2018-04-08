package it.cbmz.raspo.internal.pipeline.excpetion;

public class PipelineException extends Exception {

	public PipelineException() {
		super();
	}

	public PipelineException(Exception e) {
		super(e);
	}

	public PipelineException(String message) {
		super(message);
	}

	public PipelineException(String message, Exception e) {
		super(message, e);
	}

}
