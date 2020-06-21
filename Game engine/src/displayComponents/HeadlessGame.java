package displayComponents;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class HeadlessGame implements Runnable{
	private Thread t;
	private int hp = 100;
	private boolean b = true;
	private String ttyConfig;
	public HeadlessGame() {
		if(System.getProperty("os.name").toLowerCase().indexOf("win") != -1) System.err.println("Please, don't try and run this from windows with no gui. Get that MS server bs outta here.");
		else {
			start();
		}
	}
	
	/**
	 * Code from https://www.darkcoding.net/software/non-blocking-console-io-is-not-possible/
	 */
    private void setTerminalToCBreak() throws IOException, InterruptedException {

       ttyConfig = stty("-g");

        // set the console to be character-buffered instead of line-buffered
        stty("-icanon min 1");

        // disable character echoing
        stty("-echo");
    }
    
	/**
     *  Execute the specified command and return the output
     *  (both stdout and stderr).
     */
	private static String exec(final String[] cmd) throws IOException, InterruptedException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		Process p = Runtime.getRuntime().exec(cmd);
		int c;
		InputStream in = p.getInputStream();

		while ((c = in.read()) != -1) {
			bout.write(c);
		}

		in = p.getErrorStream();

		while ((c = in.read()) != -1) {
			bout.write(c);
		}

		p.waitFor();

		String result = new String(bout.toByteArray());
		return result;
	}
	
	/**
     *  Execute the stty command with the specified arguments
     *  against the current active terminal.
     */
    private static String stty(final String args)
                    throws IOException, InterruptedException {
        String cmd = "stty " + args + " < /dev/tty";

        return exec(new String[] {
                    "sh",
                    "-c",
                    cmd
                });
    }




	@Override
	public void run() {
		try {
		System.out.println("You need a gui to play this game.");
		t.sleep(5000);
		System.out.println("No, seriously.");
		t.sleep(5000);
		System.out.println("What are you still doing here?");
		t.sleep(5000);
		System.out.println("Fine, you want a game? Here. I'll narrate what's happening.");
		System.out.println("Press enter to shoot, and WASD to move.");
		setTerminalToCBreak();
		game();
		stty(ttyConfig.trim());
		}catch (Exception e) {
			System.out.println("Goodbye.");
			try {
				if(ttyConfig != null) stty(ttyConfig.trim());
			} catch (Exception e2) {
				System.err.println("Error restoring config.");
				e2.printStackTrace();
			}
		}
	}
	private void game() throws IOException, InterruptedException {
		while(b) {
			if(System.in.available() != 0) {
				int c = System.in.read();
				switch (c) {
				case 119:
					System.out.println("step.");
					t.sleep(10);
					break;
				case 100:
					System.out.println("step.");
					t.sleep(10);
					break;
				case 115:
					System.out.println("step.");
					t.sleep(10);
					break;
				case 97:
					Runtime.getRuntime().exec("clear");
					System.out.println("step.");
					t.sleep(10);
					break;
				case 10:
					Runtime.getRuntime().exec("clear");
					System.out.println("BANG");
					t.sleep(10);
					break;
				case 27:
					System.out.println("Goodbye.");
					b = false;
					break;
				default:
					break;
				}
				if(new Random().nextInt(50) > 35) {
					System.out.println("Ow, you got shot.");
					hp -= new Random().nextInt(20);
					System.out.println("You now have " + hp + " hp.");
				}
				if(hp <= 0) {
					System.out.println("You died. Goodbye.");
					b = false;
				}
			}
		}
	}
	
	public void start() {
		if(t == null)
			t = new Thread(this,"Headless game");
		t.start();	
	}

}