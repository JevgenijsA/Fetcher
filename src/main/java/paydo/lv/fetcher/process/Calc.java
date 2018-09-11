package paydo.lv.fetcher.process;

public class Calc {

    public static String conP(String p) {
        StringBuilder sb = new StringBuilder();
        byte[] cArr = p.getBytes();
        for (int i = 0; i < cArr.length; i++) {
            sb.append(cArr[i] + i);
            sb.append("398a711");
        }
        return sb.toString();
    }

    public static String unConP(String cp) {
        StringBuilder sb = new StringBuilder();
        String[] sArr = cp.split("398a711");
        for (int i = 0; i < sArr.length; i++) {
            int in = Integer.parseInt(sArr[i]) - i;
            sb.append((char)in);
        }
        return sb.toString();
    }
}
