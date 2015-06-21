package de.gymolching.fsb.gui;

import java.io.*;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import de.gymolching.fsb.client.api.*;
import de.gymolching.fsb.client.implementation.*;

public class Controller// implements Runnable
{
	public static void main(String[] args)
	{
		Controller controller;
		try
		{
			controller = new Controller();
			controller.startGUI();
		}
		catch (InterruptedException | IOException e)
		{
			e.printStackTrace();
		}
	}

	private Display display;
	private Shell shell;

	private FSBClient client;
	private Text text;
	private Button btnSendData;
	private Scale scale;

	public void run()
	{
		// Ask for client IP
//		boolean connected = false;
//		do
//		{
//			try
//			{
//				String serverIP = JOptionPane.showInputDialog("Server ip:", "127.0.0.1");
//				if (serverIP == null)
//				{
//					System.err.println("Canceling connection attempt, exiting...");
//					System.exit(1);
//				}
//				String serverPort = JOptionPane.showInputDialog("Server port:", "666");
//				if (serverPort == null)
//				{
//					System.err.println("Canceling connection attempt, exiting...");
//					System.exit(1);
//				}
//
//				this.client.connect(serverIP, new Integer(serverPort));
//				connected = true;
//			}
//			catch (Exception e)
//			{
//				System.err.println("Could not connect, pls retry!");
//			}
//		} while (!connected);
		
	}

	public Controller() throws InterruptedException, IOException
	{
		this.client = new FSBClient();
		this.client.connect("192.168.1.168", 666);

//		Thread thread = new Thread(this);
//		thread.start();
//		thread.join();
		
		this.display = new Display();
		this.shell = new Shell(display);
		shell.setSize(238, 450);
		shell.setLayout(new GridLayout(1, false));

		scale = new Scale(shell, SWT.HORIZONTAL);
		scale.setMinimum(0);
		scale.setMaximum(255);
		scale.setIncrement(1);
		scale.setPageIncrement(5);
		scale.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event event)
			{
				text.setText("" + scale.getSelection());
			}
		});
		scale.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));

		text = new Text(shell, SWT.BORDER | SWT.CENTER);
		text.setText("0");
		GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text.widthHint = 182;
		text.setLayoutData(gd_text);

		btnSendData = new Button(shell, SWT.NONE);
		btnSendData.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnSendData.setText("Send Data");
		btnSendData.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				sendData();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				sendData();
			}

			public void sendData()
			{
				try
				{
					client.sendNewPosition(new FSBPosition(text.getText() + ":" + text.getText() + ":" + text.getText() + ":" + text.getText() + ":" + text.getText() + ":" + text.getText() + ":"));
				}
				catch (IOException e)
				{
					e.printStackTrace();
					System.exit(1);
				}
			}
		});
	}

	public void startGUI()
	{
		this.shell.pack();
		this.shell.open();

		while (!this.shell.isDisposed())
		{
			if (!this.display.readAndDispatch())
			{
				this.display.sleep();
			}
		}

		this.display.dispose();

		try
		{
			this.client.disconnect();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
