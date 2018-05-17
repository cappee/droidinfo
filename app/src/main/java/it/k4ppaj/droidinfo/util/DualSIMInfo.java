package it.k4ppaj.droidinfo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

public final class DualSIMInfo {

    private static DualSIMInfo dualSIMInfo;
    private String imeiSIM1;
    private String imeiSIM2;
    private boolean isSIM1Ready;
    private boolean isSIM2Ready;

    public String getImeiSIM1() {
        return imeiSIM1;
    }

    public String getImeiSIM2() {
        return imeiSIM2;
    }

    public boolean isSIM1Ready() {
        return isSIM1Ready;
    }

    public boolean isSIM2Ready() {
        return isSIM2Ready;
    }

    public boolean isDualSIM() {
        return isSIM2Ready;
     }

     // access can be private - AS
    public DualSIMInfo() {
    }

    @SuppressLint("MissingPermission")
    public static DualSIMInfo getInstance(Context context) {
        if (dualSIMInfo == null) {
            dualSIMInfo = new DualSIMInfo();
            TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
            dualSIMInfo.isSIM1Ready = telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
            dualSIMInfo.isSIM2Ready = false;

            try {
                dualSIMInfo.isSIM1Ready = getSIMStateBySlot(context, "getSimStateGemini", 0);
                dualSIMInfo.isSIM2Ready = getSIMStateBySlot(context, "getSimStateGemini", 1);
            } catch (GeminiMethodNotFoundException e) {
                e.printStackTrace();

                try {
                    dualSIMInfo.isSIM1Ready = getSIMStateBySlot(context, "getSimState", 0);
                    dualSIMInfo.isSIM2Ready = getSIMStateBySlot(context, "getSimState", 1);
                } catch (GeminiMethodNotFoundException e1) {
                    e1.printStackTrace();
                }
            }

            dualSIMInfo.imeiSIM1 = telephonyManager.getDeviceId();
            dualSIMInfo.imeiSIM2 = null;

            try {
                dualSIMInfo.imeiSIM1 = getDeviceIdBySlot(context, "getDeviceIdGemini", 0);
                dualSIMInfo.imeiSIM2 = getDeviceIdBySlot(context, "getDeviceIdGemini", 1);
            } catch (GeminiMethodNotFoundException e) {
                e.printStackTrace();

                try {
                    dualSIMInfo.imeiSIM1 = getDeviceIdBySlot(context, "getDeviceId", 0);
                    dualSIMInfo.imeiSIM2 = getDeviceIdBySlot(context, "getDeviceId", 1);
                } catch (GeminiMethodNotFoundException e1) {
                    e1.printStackTrace();
                }
            }

            return dualSIMInfo;
        } else {
            return null;
        }
    }

    private static String getDeviceIdBySlot(Context context, String predictedMethodName, int slotID) throws GeminiMethodNotFoundException {
        String imei = null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            Class<?> telephonyClass = Class.forName(telephonyManager.getClass().getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID = telephonyClass.getMethod(predictedMethodName, parameter);
            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimID.invoke(telephonyManager, obParameter);

            if (ob_phone != null) {
                imei = ob_phone.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeminiMethodNotFoundException(predictedMethodName);
        }
        return imei;
    }

    private static boolean getSIMStateBySlot(Context context,  String predictedMethodName, int slotID) throws GeminiMethodNotFoundException {
        boolean isReady = false;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            Class<?> telephonyClass = Class.forName(telephonyManager.getClass().getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimStateGemini = telephonyClass.getMethod(predictedMethodName, parameter);
            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimStateGemini.invoke(telephonyManager, obParameter);

            if (ob_phone != null) {
                int simState = Integer.parseInt(ob_phone.toString());
                if (simState == TelephonyManager.SIM_STATE_READY) {
                    isReady = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeminiMethodNotFoundException(predictedMethodName);
        }
        return isReady;
    }

    private static class GeminiMethodNotFoundException extends Exception {

        private static final long serialVersionUID = -996812356902545308L;

        public GeminiMethodNotFoundException(String message) {
            super(message);
        }
    }
}
