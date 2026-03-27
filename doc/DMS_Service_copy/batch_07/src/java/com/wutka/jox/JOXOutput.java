package com.wutka.jox;

/**
 * An interface used by the output utility class so it can write XML to either
 * an output stream or a writer without caring.
 * 
 * This interface is implemented by both JOXOutputStream and JOXWriter.
 * 
 * @author Mark Wutka
 * @version 1.0 05/08/2000
 * @version 1.1 05/09/2000
 * @version 2.0 7007-01-19 Àî×ÓÑï check ok!
 */

interface JOXOutput {
	public void writeString(String str) throws java.io.IOException;
}
