public class Computer {
    private USBDevice usbDevice;

    public Computer(USBDevice usbDevice) {
        this.usbDevice = usbDevice;
    }

    public void setUsbDevice(USBDevice usbDevice) {
        this.usbDevice = usbDevice;
    }

    public void performRead() {
        if (usbDevice != null) {
            usbDevice.read();
        } else {
            System.out.println("未连接 USB 设备。");
        }
    }

    public void performWrite() {
        if (usbDevice != null) {
            usbDevice.write();
        } else {
            System.out.println("未连接 USB 设备。");
        }
    }
}
