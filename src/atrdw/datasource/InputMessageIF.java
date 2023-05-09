package atrdw.datasource;

import atrdw.model.Message;

public interface InputMessageIF {

	public Message nextMessage() throws Exception;

}
