package com.mongcent.core.commons.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author zl
 */
public class SnowflakeUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(SnowflakeUtil.class);
    private static Snowflake snowflake = IdUtil.createSnowflake(getWorkerId() % 32, getDataCenterId() % 32);
    private static final String WORKER_ID = "WorkerId";
    private static final String DATA_CENTER_ID = "DataCenterId";

    static {
        if (System.getProperty(WORKER_ID) != null) {
            String workerId = System.getProperty(WORKER_ID);
            LOGGER.info("=============WorkerId:{}=============", workerId);
        } else {
            LOGGER.info("=============WorkerId:{}=============", "Mac地址生成");
        }

        if (System.getProperty(DATA_CENTER_ID) != null) {
            String dataCenterId = System.getProperty(DATA_CENTER_ID);
            LOGGER.info("=============DataCenterId:{}=============", dataCenterId);
        } else {
            LOGGER.info("=============DataCenterId:{}=============", "IP地址生成");
        }
    }


    private static long getWorkerId() {
        if (System.getProperty(WORKER_ID) != null) {
            return Long.parseLong(System.getProperty(WORKER_ID));
        }

        try {
            InetAddress inetaddress = InetAddress.getLocalHost();

            //get Network interface by Address
            NetworkInterface network = NetworkInterface.getByInetAddress(inetaddress);
            byte[] macArray = network.getHardwareAddress();
            StringBuilder str = new StringBuilder();

            // Convert Array to String
            for (byte aMacArray : macArray) {
                str.append(String.format("%d", aMacArray));
            }

            return Long.parseLong(str.toString().replace("-", ""));
        } catch (Exception e) {
            LOGGER.error("获取Mac失败", e);
            return RandomUtil.randomInt(0, Integer.MAX_VALUE);
        }
    }

    private static long getDataCenterId() {
        if (System.getProperty(DATA_CENTER_ID) != null) {
            return Long.parseLong(System.getProperty(DATA_CENTER_ID));
        }

        try {
            InetAddress addr = InetAddress.getLocalHost();
            return Long.parseLong(addr.getHostAddress().replace(".", ""));
        } catch (Exception e) {
            LOGGER.error("获取Ip失败", e);
            return RandomUtil.randomInt(0, Integer.MAX_VALUE);
        }
    }

    public static Long getLogId() {
        return snowflake.nextId();
    }
}
