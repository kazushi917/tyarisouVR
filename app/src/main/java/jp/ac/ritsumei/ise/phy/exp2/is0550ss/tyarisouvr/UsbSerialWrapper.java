package jp.ac.ritsumei.ise.phy.exp2.is0550ss.tyarisouvr;

public class UsbSerialWrapper {

    // アプリ起動後、USBデバイスと接続するために呼び出す
    public static boolean OpenDevice(int baudrate)
    {
        baudrate_ = baudrate;
        usb_manager_ = (UsbManager) context_.getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usb_manager_);
        usb_driver_ = availableDrivers.get(0);
        return Connect();
    }

    public static boolean Connect() {
        UsbDeviceConnection connection = usb_manager_.openDevice(usb_driver_.getDevice());

        port_ = usb_driver_.getPorts().get(0);
        port_.open(connection);
        port_.setParameters(baudrate_, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
        port_.setDTR(true);
        connected_ = true;

        return true;
    }

    // 受信したデータを取得する
    public static String Read()
    {
        length_ = 0;
        data_ = "";
        byte[] buffer = new byte[8192];
        length_ = port_.read(buffer, 2000);

        data_ = new String(buffer, StandardCharsets.UTF_8);
        return data_;
    }