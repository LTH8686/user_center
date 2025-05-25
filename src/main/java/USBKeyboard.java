public class USBKeyboard implements USBDevice {
    @Override
    public void read() {
        System.out.println("正在读取键盘输入。");
    }

    @Override
    public void write() {
        System.out.println("无法将数据写入键盘。");
    }
}
