package de.gymolching.fsb.client.api;

import java.io.IOException;

import de.gymolching.fsb.api.FSBPosition;

public interface FSBClientInterface
{
	/**
	 * connects to the server host:port
	 * 
	 * @param host
	 *            hostname/ip address
	 * @param port
	 *            port
	 * @throws IOException
	 *             throws exception when no connection can be established or dataoutstream opened
	 */
	public void connect(String host, int port) throws IOException;

	/**
	 * Disconnects from remote Server
	 * 
	 * @throws IOException
	 *             throws exception when disconnection wasn't possible for some reason
	 */
	public void disconnect() throws IOException;

	/**
	 * Sends a position Update to the Raspberry Pi
	 * 
	 * @param position
	 *            the new position
	 * @throws IOException
	 *             throws exception when new position data could not be sent
	 */
	public void sendNewPosition(FSBPosition position) throws IOException;

	/**
	 * enables or disables verbose client console logging
	 * 
	 * @param verbose
	 */
	public void setVerbose(boolean verbose);
}
