package Helper;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Helper {

    public static void optionPaneChangeButtonText() {//Optionpanelerin butonlar�n� t�rk�e yapan metod
        UIManager.put("OptionPane.cancelButtonText", "�ptal");
        UIManager.put("OptionPane.noButtonText", "Hay�r");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.okButtonText", "Tamam");

    }

    public static void showMsg(String str) {
        optionPaneChangeButtonText();
        String msg;
        switch (str) {
            case "fillup":
                msg = "L�tfen t�m alanlar� doldurunuz !";
                break;
            case "success":
                msg = "��lem ba�ar�l�";
                break;
            case "fail":
                msg = "��lem ba�ar�s�z";
                break;
            default:
                msg = str;
                break;
        }
        JOptionPane.showMessageDialog(null, msg, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {
        optionPaneChangeButtonText();
        String msg;
        switch (str) {
            case "sure":
                msg = "Bu i�lemi ger�ekle�tirmek istiyor musunuz ?";
                break;

            default:
                msg = str;
                break;
        }
        int result = JOptionPane.showConfirmDialog(null, msg, "Dikkat !", JOptionPane.YES_NO_OPTION);
        if (result == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean confirmAppoint(String clinic, String doctorName, String date) {
        optionPaneChangeButtonText();
        String msg = "Randevu Bilgileri:\n" + "Poliklinik: " + clinic + "\nDoktor: Dr." + doctorName + " \n" + "Tarih: " + date + "\nRandevuyu onayl�yor musunuz ?";

        int result = JOptionPane.showConfirmDialog(null, msg, "Dikkat !", JOptionPane.YES_NO_OPTION);

        if (result == 0) {
            return true;
        } else {
            return false;
        }
    }
}
