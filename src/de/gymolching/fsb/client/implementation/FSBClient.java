package de.gymolching.fsb.client.implementation;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import de.gymolching.fsb.client.api.FSBClientInterface;
import de.gymolching.fsb.client.api.FSBPosition;

public class FSBClient implements FSBClientInterface
{
	private Socket clientSocket = null;
	private DataOutputStream connOutStream = null;
	private boolean verbose = false;

	public FSBClient()
	{
		this.verbose = false;
	}

	public FSBClient(boolean verbose)
	{
		this.verbose = verbose;
	}

	public void connect(String host, int port) throws IOException
	{
		if (this.verbose)
			System.out.println("[Client] Establishing connection to Server " + host + ":" + port);

		this.clientSocket = new Socket(host, port);
		this.connOutStream = new DataOutputStream(this.clientSocket.getOutputStream());
		if (this.verbose) System.out.println("[Client] Connection established");
	}

	public void disconnect() throws IOException
	{
		this.connOutStream.close();
		this.clientSocket.close();
	}

	public void sendNewPosition(FSBPosition position) throws IOException
	{
		if (this.clientSocket != null)
		{
			if (this.verbose)
				System.out.println("[Client] Sending new position data: " + position.toString());
			this.connOutStream.writeUTF(position.toString());
		}
		else
		{
			System.err
					.println("[Client] No connection established. Can not send data. Connect to server first by invoking connect()");
		}
	}

	@Override
	public void setVerbose(boolean verbose)
	{
		this.verbose = verbose;
	}
}
