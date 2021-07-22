

public class AESCryptor {
	static {
		try {
			System.loadLibrary("你的so库名(lib后 .so前)");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static final int ENCRYPT = 0;
	
	public static final int DECRYPT = 1;

	public native static byte[] crypt(byte[] data, long time, int mode);
	public native static byte[] read(String path, long time);

}
