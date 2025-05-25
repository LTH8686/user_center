public class USBFlashDrive implements USBDevice {
    @Override
    public void read() {
        System.out.println("正在从 USB 闪存驱动器读取数据。");
    }

    @Override
    public void write() {
        System.out.println("正在向 USB 闪存驱动器写入数据。");
    }
}
