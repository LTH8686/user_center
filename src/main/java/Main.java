public class Main {
    public static void main(String[] args) {
        // 注入 U盘
        USBDevice flashDrive = new USBFlashDrive();
        Computer computer = new Computer(flashDrive);
        computer.performRead();  // 输出：正在从 USB 闪存驱动器读取数据。
        computer.performWrite(); // 输出：正在向 USB 闪存驱动器写入数据。

        // 更换设备为键盘
        USBDevice keyboard = new USBKeyboard();
        computer.setUsbDevice(keyboard);
        computer.performRead();  // 输出：正在读取键盘输入。
        computer.performWrite(); // 输出：无法将数据写入键盘。

        // 无设备时的情况
        computer.setUsbDevice(null);
        computer.performRead();  // 输出：未连接 USB 设备。
    }
}
