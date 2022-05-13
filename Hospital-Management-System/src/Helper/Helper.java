package Helper;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Helper {

    public static void optionPaneChangeButtonText() {//Optionpanelerin butonlarýný türkçe yapan metod
        UIManager.put("OptionPane.cancelButtonText", "Ýptal");
        UIManager.put("OptionPane.noButtonText", "Hayýr");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.okButtonText", "Tamam");

    }

    public static void showMsg(String str) {
        optionPaneChangeButtonText();
        String msg;
        switch (str) {
            case "fillup":
                msg = "Lütfen tüm alanlarý doldurunuz !";
                break;
            case "success":
                msg = "Ýþlem baþarýlý";
                break;
            case "fail":
                msg = "Ýþlem baþarýsýz";
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
                msg = "Bu iþlemi gerçekleþtirmek istiyor musunuz ?";
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
        String msg = "Randevu Bilgileri:\n" + "Poliklinik: " + clinic + "\nDoktor: Dr." + doctorName + " \n" + "Tarih: " + date + "\nRandevuyu onaylýyor musunuz ?";

        int result = JOptionPane.showConfirmDialog(null, msg, "Dikkat !", JOptionPane.YES_NO_OPTION);

        if (result == 0) {
            return true;
        } else {
            return false;
        }
    }
}
